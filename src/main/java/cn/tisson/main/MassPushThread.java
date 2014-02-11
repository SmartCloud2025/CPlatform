package cn.tisson.main;

import cn.tisson.common.GlobalCaches;
import cn.tisson.common.LogicHelper;
import cn.tisson.dbmgr.model.*;
import cn.tisson.dbmgr.service.*;
import cn.tisson.framework.global.AppVariables;
import cn.tisson.framework.thread.BaseThread;
import cn.tisson.platform.protocol.active.TokenRespond;
import cn.tisson.platform.protocol.active.servReq.*;
import org.jasic.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author Jasic
 * <p/>
 * 主动消息推送
 */
@Component
public class MassPushThread extends BaseThread {

    private static final Logger logger = LoggerFactory.getLogger(MassPushThread.class);

    @Resource
    private ActiveTextService textService;

    @Resource
    private ActiveImageService imageService;

    @Resource
    private ActiveMusicService musicService;

    @Resource
    private ActiveNewsService newsService;

    @Resource
    private ActiveVideoService videoService;

    @Resource
    private ActiveVoiceService voiceService;


    @Override
    public void run() {
        while (AppVariables.APP_RUNNING_FLAG) {

            Collection<List<MassPushMsg>> collection = GlobalCaches.DB_CACHE_MASS_PUSH_MSG.values();


            try {
                for (List<MassPushMsg> msgList : collection) {

                    for (MassPushMsg msg : msgList) {

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
                            if (groups == null) continue;

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
                            for (String toUserTemp : toUsers) {
                                TextServReq req = new TextServReq(toUserTemp, text.getContent());
                                LogicHelper.sendActiveMsg(logger, tokenRespond.getAccess_token(), req);
                            }
                        }

                        // 图像
                        else if (type.equals(ImageServReq.MSGTYPE)) {

                            ActiveImage image = imageService.selectByPrimaryKey(msgid);

                            for (String toUserTemp : toUsers) {
                                ImageServReq req = new ImageServReq(toUserTemp, image.getMediaId());
                                LogicHelper.sendActiveMsg(logger, tokenRespond.getAccess_token(), req);
                            }
                        }

                        // 音乐
                        else if (type.equals(MusicServReq.MSGTYPE)) {
                            ActiveMusic music = musicService.selectByPrimaryKey(msgid);
                            String titile = music.getTitle();
                            String description = music.getDescription();
                            String musicurl = music.getMusicurl();
                            String hqmusicurl = music.getHqmusicurl();
                            String thumb_media_id = music.getThumbMediaId();


                            for (String toUserTemp : toUsers) {
//                                String touser, String title, String description, String musicurl, String hqmusicurl, String thumb_media_id
                                MusicServReq req = new MusicServReq(toUserTemp,
                                        titile, description, musicurl, hqmusicurl, thumb_media_id);
                                LogicHelper.sendActiveMsg(logger, tokenRespond.getAccess_token(), req);
                            }
                        }

                        // 图文
                        else if (type.equals(NewsServReq.MSGTYPE)) {

//                            newsService.selectByPrimaryKey()
                        }


                        // 声音
                        else if (type.equals(VoiceServReq.MSGTYPE)) {

                            ActiveVoice voice = voiceService.selectByPrimaryKey(msgid);

                            for (String toUserTemp : toUsers) {
                                VoiceServReq req = new VoiceServReq(toUserTemp, voice.getMediaId());
                                LogicHelper.sendActiveMsg(logger, tokenRespond.getAccess_token(), req);
                            }

                        }

                        // 视频
                        else if (type.equals(VideoServReq.MSGTYPE)) {

                            ActiveVideo video = videoService.selectByPrimaryKey(msgid);

                            String media_id = video.getMediaId();
                            String title = video.getTitle();
                            String description = video.getDescription();
                            for (String toUserTemp : toUsers) {
                                //String media_id, String title,String description
                                VideoServReq req = new VideoServReq(toUserTemp, media_id, title, description);
                                LogicHelper.sendActiveMsg(logger, tokenRespond.getAccess_token(), req);
                            }
                        }
                    }
                }
            } catch (Exception e) {

                logger.error(ExceptionUtil.getExceptionStackTrace(e));
            }


        }
    }
}
