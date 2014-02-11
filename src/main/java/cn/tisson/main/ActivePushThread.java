package cn.tisson.main;

import cn.tisson.common.GlobalCaches;
import cn.tisson.common.GlobalConstants;
import cn.tisson.common.GlobalVariables;
import cn.tisson.common.LogicHelper;
import cn.tisson.dbmgr.model.*;
import cn.tisson.dbmgr.service.*;
import cn.tisson.framework.global.AppVariables;
import cn.tisson.framework.thread.BaseThread;
import cn.tisson.platform.protocol.active.ErrorRespond;
import cn.tisson.platform.protocol.active.TokenRespond;
import cn.tisson.platform.protocol.active.servReq.*;
import cn.tisson.util.SpringContextUtil;
import org.jasic.util.ExceptionUtil;
import org.jasic.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author Jasic
 * <p/>
 * 主动消息推送
 */
public class ActivePushThread extends BaseThread {

    private static final Logger logger = LoggerFactory.getLogger(ActivePushThread.class);

    /**
     * 用于记载发送失败的主动发送消息
     */
    private static final Map<Integer, Integer> RE_ACTIVE_PUSH_MAP = new ConcurrentHashMap<Integer, Integer>();

    private ActiveTextService textService;

    private ActiveImageService imageService;

    private ActiveMusicService musicService;

    private ActiveNewsService newsService;

    private ActiveVideoService videoService;

    private ActiveVoiceService voiceService;

    private ActiveArticleService articleService;

    private MassPushMsgService massPushMsgService;

    public ActivePushThread() {
        this.textService = SpringContextUtil.getBean(ActiveTextService.class);
        this.imageService = SpringContextUtil.getBean(ActiveImageService.class);
        this.musicService = SpringContextUtil.getBean(ActiveMusicService.class);
        this.newsService = SpringContextUtil.getBean(ActiveNewsService.class);
        this.videoService = SpringContextUtil.getBean(ActiveVideoService.class);
        this.voiceService = SpringContextUtil.getBean(ActiveVoiceService.class);
        this.articleService = SpringContextUtil.getBean(ActiveArticleService.class);
        this.massPushMsgService = SpringContextUtil.getBean(MassPushMsgService.class);
    }

    @Override
    public void run() {
        while (AppVariables.APP_RUNNING_FLAG) {

            Collection<List<MassPushMsg>> collection = GlobalCaches.DB_CACHE_MASS_PUSH_MSG.values();
            ErrorRespond respond = null;


            try {
                for (List<MassPushMsg> msgList : collection) {

                    Iterator<MassPushMsg> it = msgList.iterator();

                    while (it.hasNext()) {
                        MassPushMsg msg = it.next();
                        String type = msg.getType();
                        String fromUser = msg.getServicewebchatid();
                        String toUser = msg.getTowebchatid();
                        Integer msgid = msg.getMsgid();

                        TokenRespond tokenRespond = GlobalCaches.ACCESS_TOKEN_CACHE.get(fromUser);
                        if (tokenRespond == null) continue;
                        List<String> toUsers = new ArrayList<String>();

                        /**
                         * 对所有粉丝发送
                         */
                        if (toUser == null) {

                            ServiceInfo serviceInfo = GlobalCaches.DB_CACHE_SERVICE_INFO.get(fromUser);
                            List<FansGroup> groups = serviceInfo.getFansGroups();
                            if (groups == null || groups.size() == 0) continue;

                            for (FansGroup group : groups) {
                                List<FansInfo> fansInfos = group.getFansInfoList();
                                if (fansInfos == null || fansInfos.size() == 0) {
                                    continue;
                                }
                                for (FansInfo fansInfo : fansInfos) {
                                    toUsers.add(fansInfo.getWebchatid());
                                }
                            }
                        }

                        /**
                         * 对指定用户发送
                         */
                        else {
                            toUsers.add(toUser);
                        }

                        // 文本
                        if (type.equals(TextServReq.MSGTYPE)) {
                            ActiveText text = textService.selectByPrimaryKey(msgid);
                            if (text == null || text.getContent() == null) continue;
                            for (String toUserTemp : toUsers) {
                                TextServReq req = new TextServReq(toUserTemp, text.getContent());
                                respond = LogicHelper.sendActiveMsg(logger, tokenRespond.getAccess_token(), req);
                            }
                        }

                        // 图像
                        else if (type.equals(ImageServReq.MSGTYPE)) {

                            ActiveImage image = imageService.selectByPrimaryKey(msgid);
                            if (image == null || image.getMediaId() == null) continue;

                            for (String toUserTemp : toUsers) {
                                ImageServReq req = new ImageServReq(toUserTemp, image.getMediaId());
                                respond = LogicHelper.sendActiveMsg(logger, tokenRespond.getAccess_token(), req);
                            }
                        }

                        // 音乐
                        else if (type.equals(MusicServReq.MSGTYPE)) {
                            ActiveMusic music = musicService.selectByPrimaryKey(msgid);
                            if (music == null) continue;

                            String titile = music.getTitle();
                            String description = music.getDescription();
                            String musicurl = music.getMusicurl();
                            String hqmusicurl = music.getHqmusicurl();
                            String thumb_media_id = music.getThumbMediaId();


                            for (String toUserTemp : toUsers) {
                                MusicServReq req = new MusicServReq(toUserTemp,
                                        titile, description, musicurl, hqmusicurl, thumb_media_id);
                                respond = LogicHelper.sendActiveMsg(logger, tokenRespond.getAccess_token(), req);
                            }
                        }

                        // 图文
                        else if (type.equals(NewsServReq.MSGTYPE)) {

                            ActiveNewsMsg newsMsg = newsService.selectByPrimaryKey(msgid);
                            if (newsMsg == null) continue;
                            List<ActiveArticle> articles = articleService.getListByNewsMsgID(msgid);
                            if (articles == null || articles.size() == 0) continue;
                            newsMsg.setArticles(articles);

                            for (String toUserTemp : toUsers) {
                                NewsServReq req = new NewsServReq(toUserTemp);
                                List<cn.tisson.platform.protocol.bean.Article> arts = new ArrayList<cn.tisson.platform.protocol.bean.Article>();
                                for (ActiveArticle aart : articles) {
                                    cn.tisson.platform.protocol.bean.Article art = new cn.tisson.platform.protocol.bean.Article();
                                    art.setTitle(aart.getTitle());
                                    art.setPicUrl(aart.getPicurl());
                                    art.setDescription(aart.getDescription());
                                    art.setUrl(aart.getUrl());
                                    arts.add(art);
                                }
                                req.getNews().setArticles(arts);
                                respond = LogicHelper.sendActiveMsg(logger, tokenRespond.getAccess_token(), req);
                            }
                        }

                        // 声音
                        else if (type.equals(VoiceServReq.MSGTYPE)) {

                            ActiveVoice voice = voiceService.selectByPrimaryKey(msgid);
                            if (voice == null || voice.getMediaId() == null) continue;

                            for (String toUserTemp : toUsers) {
                                VoiceServReq req = new VoiceServReq(toUserTemp, voice.getMediaId());
                                respond = LogicHelper.sendActiveMsg(logger, tokenRespond.getAccess_token(), req);
                            }

                        }

                        // 视频
                        else if (type.equals(VideoServReq.MSGTYPE)) {

                            ActiveVideo video = videoService.selectByPrimaryKey(msgid);
                            if (video == null || video.getMediaId() == null) continue;

                            String media_id = video.getMediaId();
                            String title = video.getTitle();
                            String description = video.getDescription();
                            for (String toUserTemp : toUsers) {
                                VideoServReq req = new VideoServReq(toUserTemp, media_id, title, description);
                                respond = LogicHelper.sendActiveMsg(logger, tokenRespond.getAccess_token(), req);
                            }
                        }

                        if (respond == null || !respond.getErrcode().equals("0")) {
                            Integer count = RE_ACTIVE_PUSH_MAP.get(msgid);
                            count = (count == null ? 0 : count);

                            if (count >= 3) {
                                updatePushMsg(msg);
                                it.remove();

                            } else {
                                RE_ACTIVE_PUSH_MAP.put(msgid, ++count);
                            }
                        } else {
                            updatePushMsg(msg);
                            it.remove();
                        }
                    }

                }
            } catch (Exception e) {
                logger.error(ExceptionUtil.getStackTrace(e));
            }

            TimeUtil.sleep(GlobalVariables.ACTIVE_PUSH_MSG_INTERVAL);
        }
    }


    public void updatePushMsg(MassPushMsg msg) {
        msg.setStatus(GlobalConstants.STATUS_NO_EFFETIVE);
        massPushMsgService.updateByPrimaryKeySelective(msg);
        RE_ACTIVE_PUSH_MAP.remove(msg.getId());
    }
}
