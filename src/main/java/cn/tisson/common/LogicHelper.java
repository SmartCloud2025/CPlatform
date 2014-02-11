package cn.tisson.common;

import cn.tisson.dbmgr.model.*;
import cn.tisson.dbmgr.service.FansGroupService;
import cn.tisson.platform.protocol.active.servReq.BaseServReq;
import cn.tisson.platform.protocol.resp.BaseRespMsg;
import cn.tisson.platform.protocol.resp.NewsRespMsg;
import cn.tisson.platform.protocol.resp.TextRespMsg;
import cn.tisson.util.MessageUtil;
import cn.tisson.util.SpringContextUtil;
import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jasic.util.ExceptionUtil;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: Jasic
 * Date: 13-12-4
 */
public class LogicHelper {

//    @Test
//    public static boolean login() {
//        try {
//            String respStr = login(WEI_XIN_INTERFACE_APPID, WEI_XIN_INTERFACE_SECRET);
//            BaseRespond resp = analyseRespCode(respStr);
//
//            if (resp instanceof TokenRespond) {
//                GlobalVariables.GLOBAL_TOKEN_RESPOND = (TokenRespond) resp;
//                return true;
//            } else if (resp instanceof ErrorRespond) {
//                GlobalVariables.GLOBAL_TOKEN_RESPOND = null;
//                return false;
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//
//    public static String WEI_XIN_INTERFACE_APPID = "wx39b31e79637b8e77";
//    public static String WEI_XIN_INTERFACE_SECRET = "f4b1fab0c5653b2bd89b299f0446f79e";
//
//
//    /**
//     * 登录
//     *
//     * @param appid
//     * @param secet
//     * @return
//     * @throws java.io.IOException
//     */
//    public static String login(String appid, String secet) throws IOException {
//        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
//        url = String.format(url, new Object[]{appid, secet});
//        HttpClient client = new DefaultHttpClient();
//        HttpGet get = new HttpGet(url);
//        HttpResponse response = client.execute(get);
//        return EntityUtils.toString(response.getEntity());
//    }
//
//    /**
//     * 将响应的json转化为实体
//     *
//     * @param entityStr
//     * @return
//     */
//    public static BaseRespond analyseRespCode(String entityStr) {
//        TokenRespond token = JsonUtils.parseToObject(entityStr, TokenRespond.class);
//        System.out.println(LogicHelper.class + "----:" + entityStr);
//        if (token.getAccess_token() != null && token.getExpires_in() != null) {
//            return token;
//        } else {
//            ErrorRespond error = JsonUtils.parseToObject(entityStr, ErrorRespond.class);
//            if (error.getErrcode() == null || error.getErrmsg() == null) {
//                throw new RuntimeException("Can't analyse the respond's code[" + entityStr + "] to a token or a ErrorRespond");
//            }
//            return error;
//        }
//    }


    /**
     * 根据tooken拼接微信接入url
     *
     * @param token
     * @return
     */
    public static String getWebchatPath(String token) {

        return GlobalVariables.PATH_WEB_CHAT_TOKEN + "/" + token;
    }


    /**
     * @param detail
     * @param e
     * @return
     */
    public static String getTerminalProcessDetail(String detail, Exception e) {
        return detail + e == null ? "" : (!GlobalVariables.TERMIAL_EXEPTION_MSG_LOG_FLAG ? "" : ExceptionUtil.getStackTrace(e));
    }

    public static FansGroup addIfNotExistFansGroup(ServiceInfo serviceInfo) {
        List<FansGroup> groupList = serviceInfo.getFansGroups();


        // 存在默认组则直接返回
        if (groupList != null && groupList.size() != 0) {
            for (FansGroup g : groupList) {

                String name = g.getGroupname();
                String type = g.getGrouptype();
                Integer priority = g.getPriority();

                if (name != null && type != null && priority != null
                        && name.equalsIgnoreCase(GlobalConstants.FANS_GROUP_DEFAULT_NAME)
                        && type.equalsIgnoreCase(GlobalConstants.FANS_GROUP_DEFAULT_TYPE)
                        && priority.equals(GlobalConstants.FANS_GROUP_DEFAULT_PRIORITY)
                        ) return g;
            }
        }

        // 不存则新增默认分组
        FansGroup g = new FansGroup();
        g.setServicewebchatid(serviceInfo.getWebchatid());
        g.setGrouptype(GlobalConstants.FANS_GROUP_DEFAULT_TYPE);
        g.setGroupname(GlobalConstants.FANS_GROUP_DEFAULT_NAME);
        g.setPriority(GlobalConstants.FANS_GROUP_DEFAULT_PRIORITY);
        g.setStatus(GlobalConstants.FANS_GROUP_DEFAULT_STATUS);
        g.setCreatedate(GlobalConstants.FANS_GROUP_DEFAULT_CREATE_DATE);

        FansGroupService tempService =
                SpringContextUtil.getBean(FansGroupService.class);
        tempService.insertSelective(g);
        groupList.add(g = tempService.selectByWebchatNamePriority(g));

        return g;
    }

    /**
     * @param serviceInfo
     * @param fromUserName
     * @return
     */
    public static FansGroup findFansGroup(ServiceInfo serviceInfo, String fromUserName) {

        List<FansGroup> fansGroupList = serviceInfo.getFansGroups();
        for (FansGroup group : fansGroupList) {
            List<FansInfo> fansInfos = group.getFansInfoList();
            for (FansInfo fan : fansInfos) {
                if (fan.getWebchatid().equalsIgnoreCase(fromUserName)) {
                    return group;
                }
            }
        }

        return null;
    }

    /**
     * 获取相对应的命令
     *
     * @param id
     * @param toUserName
     * @return
     */
    public static CmdConfig findCmdConfig(Integer id, String toUserName, String cmdStr) {
        Collection<CmdConfig> c = GlobalCaches.DB_CACHE_CMD_CONFIG.values();


        for (CmdConfig cmd : c) {

            String cmdStrTemp = cmd.getCmd();
            // 全局默认的配置(对所有的服务号、订阅号有效）
            if (cmdStr.equals(cmdStrTemp) && cmd.getServicewebchatid() == null) {
                return cmd;
            }

            // 对一个服务号的所有粉丝组配置
            else if (cmdStr.equals(cmdStrTemp) && cmd.getServicewebchatid().equalsIgnoreCase(toUserName) && cmd.getFansgroupid() == null) {
                return cmd;
            }

            // 对一个服务指定的粉丝组
            else if (cmdStr.equals(cmdStrTemp) && cmd.getServicewebchatid().equalsIgnoreCase(toUserName) &&
                    cmd.getFansgroupid().equals(id)) {
                return cmd;
            }
        }

        return null;
    }

    /**
     * 获取配置的回复消息
     *
     * @param toUserName
     * @param fromUserName
     * @param type
     * @param msgId
     * @return
     */
    public static BaseRespMsg getConfigResp(String toUserName, String fromUserName, String type, Integer msgId) {
        /**
         * 图文
         */
        if (type.equals(MessageUtil.RESP_MESSAGE_TYPE_NEWS.toLowerCase().trim())) {

            NewsRespMsg newsRespMsg = new NewsRespMsg();
            newsRespMsg.setFromUserName(fromUserName);
            newsRespMsg.setToUserName(toUserName);
            newsRespMsg.setCreateTime(System.currentTimeMillis() / 1000);


            NewsMsg newsMsg = GlobalCaches.DB_CACHE_NEWS_MSG.get(msgId);
            List<cn.tisson.dbmgr.model.Article> articles = newsMsg.getArticles();

            List<cn.tisson.platform.protocol.bean.Article> arts = new ArrayList<cn.tisson.platform.protocol.bean.Article>();
            for (cn.tisson.dbmgr.model.Article article : articles) {
                cn.tisson.platform.protocol.bean.Article a = new cn.tisson.platform.protocol.bean.Article();
                a.setUrl(article.getUrl());
                a.setDescription(article.getDescription());
                a.setPicUrl(article.getPicurl());
                a.setTitle(article.getTitle());
                arts.add(a);
            }

            newsRespMsg.setArticles(arts);
            newsRespMsg.setArticleCount(arts.size());

            return newsRespMsg;
        }

        /**
         * 文字（在数据库配置默认的规则）
         */
        else if (type.equals(MessageUtil.RESP_MESSAGE_TYPE_TEXT.toLowerCase().trim())) {

            TextRespMsg respMsg = new TextRespMsg();
            respMsg.setFromUserName(fromUserName);
            respMsg.setToUserName(toUserName);
            respMsg.setCreateTime(System.currentTimeMillis() / 1000);

            Text text = GlobalCaches.DB_CACHE_TEXT_MSG.get(msgId);

            respMsg.setContent(text.getContent());
            return respMsg;
        }

        return null;
    }

    /**
     * 通过关键字回复
     *
     * @param fromUserName
     * @param toUserName
     * @param cmdStr
     * @return
     */
    public static BaseRespMsg replyViaKeyWord(String fromUserName, String toUserName, String cmdStr) {
        return null;
    }

    /**
     * 根据url获取token
     *
     * @param appID
     * @param appSecret
     * @return
     * @throws IOException
     */
    public static String getAccessToken(String appID, String appSecret) throws IOException {
        HttpClient client = new DefaultHttpClient();

        String url = new String(GlobalVariables.GET_ACCESS_TOOKEN_URL);
        url = url.replace("{AppID}", appID).replace("{AppSecret}", appSecret);

        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);

        int statusCode = response.getStatusLine().getStatusCode();

        /*4 判断访问的状态码*/
        if (statusCode != HttpStatus.SC_OK) {
            System.err.println("Method failed: ");
            return "Method failed: ";
        }

        String contend = EntityUtils.toString(response.getEntity());
        return contend;
    }

    /**
     * 发送客服消息
     *
     * @param req
     * @return
     */
    public static String sendActiveMsg(Logger logger, String access_token, BaseServReq req) throws IOException {

        String url = new String(GlobalVariables.SEND_ACTIVE_BASE_URL).replace("{ACCESS_TOKEN}", access_token);

        // 客户端
        HttpClient client = new DefaultHttpClient();

        HttpPost post = new HttpPost(url);

        // 随便模拟浏览器--部分信息可要可不要。
        Header Agent = new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36");
        Header connection = new BasicHeader("Connection", "keep-alive");

        Header accept;
        accept = new BasicHeader("Accept", "application/json");

        Header contentType;
        contentType = new BasicHeader("Content-Type", "application/json");

        Header cacheControl = new BasicHeader("Cache-Control", "max-age=0");
        Header acceptEncoding = new BasicHeader("Accept-Encoding", "gzip,deflate,sdch");
        Header acceptLanguage = new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8");

        post.setHeaders(new Header[]{Agent, connection, accept, cacheControl, contentType, acceptEncoding, acceptLanguage});
        String msg = null;
        //================================================================设置内容============================================
        msg = JSON.toJSONString(req);

        logger.info(msg);
        //================================================================设置内容============================================
        post.setEntity(new StringEntity(msg, "UTF-8"));

        //返回
        HttpResponse response = client.execute(post);
        int statusCode = response.getStatusLine().getStatusCode();

        logger.info("statusCode:" + statusCode);

        /* 4、判断访问的状态码*/
        if (statusCode != HttpStatus.SC_OK) {
            logger.info("Method failed: ");
            return "Method failed: ";
        }

        String contend = EntityUtils.toString(response.getEntity());
        return contend;
    }
}