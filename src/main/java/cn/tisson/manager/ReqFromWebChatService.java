package cn.tisson.manager;

import cn.tisson.common.GlobalCaches;
import cn.tisson.common.WebChatDigest;
import cn.tisson.dbmgr.model.ServiceInfo;
import cn.tisson.dbmgr.service.ServiceInfoService;
import cn.tisson.exception.DuplicateMessageException;
import cn.tisson.framework.utils.StringUtils;
import cn.tisson.platform.process.WebChatMsgHandler;
import cn.tisson.platform.protocol.active.Signature;
import cn.tisson.platform.protocol.codec.WebChatMsgDecoder;
import cn.tisson.platform.protocol.codec.WebChatMsgEncoder;
import cn.tisson.platform.protocol.req.BaseReqMsg;
import cn.tisson.platform.protocol.resp.BaseRespMsg;
import cn.tisson.util.MessageUtil;
import com.alibaba.fastjson.JSON;
import org.jasic.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Jasic
 * Date 13-12-21.
 */
@Service("ReqFromWebChatService")
@Lazy
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqFromWebChatService {
// TODO 需要使用多线程去处理微信响应

    private static Logger logger = LoggerFactory.getLogger(ReqFromWebChatService.class);

    @Resource
    private ServiceInfoService serviceInfoServI;

    /**
     * 消息解码器
     */
    @Autowired
    private WebChatMsgDecoder decoder;

    /**
     * 消息处理器
     */
    @Autowired
    private WebChatMsgHandler<BaseReqMsg> handler;

    /**
     * 消息编码器
     */
    @Autowired
    private WebChatMsgEncoder<BaseRespMsg> encoder;

    public ReqFromWebChatService() {

//        /**
//         * 测试
//         */
//        decoder = new WebChatMsgDecoder();
//        this.handler = new WebChatMsgHandler<BaseReqMsg>();
//        this.encoder = new WebChatMsgEncoder<BaseRespMsg>();
    }

    /**
     * 验证token是否存在
     *
     * @param url
     * @param token
     * @return
     */
    public boolean validateToken(String url, String token) {
        if (StringUtils.isNotEmpty(token)) {
            List<ServiceInfo> infos = new ArrayList<ServiceInfo>(GlobalCaches.DB_CACHE_SERVICE_INFO.values());
            for (ServiceInfo info : infos) {
                String tmpToken = info.getToken();
                String tmpUrl = info.getUrl();

                if (!StringUtils.isEmpty(tmpToken) && !StringUtils.isEmpty(tmpUrl)) {
                    // 查找到符合的token及url
                    if (token.toLowerCase().equals(tmpToken.toLowerCase())) {
                        // TODO 暂把URL匹配去掉
//                        if (url.toLowerCase().equals(tmpUrl.toLowerCase()))
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 微信验证消息真实性
     *
     * @param logger
     * @param req
     * @param resp
     * @param token
     * @return
     * @throws java.io.IOException
     */
    public Signature validatePara(Logger logger, HttpServletRequest req, HttpServletResponse resp, String token) {
/*
 *        http://http://jasic.vicp.net//platform/valiateToken/Jasic_Token?signature=9d7f3bf333e0bbab896521b0012100f195ce17c2&timestamp=1389358841&nonce=1389061545&echostr=abcd
 */
        try {
//            String body = MessageUtil.getRequestBody(req);
//
//            if (GlobalVariables.WEB_CHAT_LOG_FLAG) {
//                logger.info(req + "-->" + body);
//            }

            String signature = req.getParameter("signature");
            String timestamp = req.getParameter("timestamp");
            String nonce = req.getParameter("nonce");
            String echostr = req.getParameter("echostr");

            Signature sig = new Signature();
            sig.setSignature(signature);
            sig.setTimestamp(timestamp);
            sig.setNonce(nonce);
            sig.setEchostr(echostr);

            if (!StringUtils.hasEmpty(new String[]{signature, timestamp, nonce, echostr})) {
                sig.setSuccess(WebChatDigest.getInstance().validate(signature, timestamp, nonce, token));
            }
            return sig;
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
        }
        return null;
    }

    /**
     * 处理消息
     *
     * @param body
     * @return
     */
    public String handle(String body) throws UnsupportedEncodingException {

        body = MessageUtil.cutXml(URLDecoder.decode(body, decoder.getCharset().displayName()));

        // reqMessage不可能为null
        BaseReqMsg reqMessage = decoder.decode(body);
        String resp = null;

        try {
            logger.info("请求body转换的实体:" + JSON.toJSONString(reqMessage));
            BaseRespMsg respMsg = handler.handle(reqMessage);
            logger.info("处理请求后响应的实体为:" + JSON.toJSONString(respMsg));
            /**
             * 如果忽略用户的请求信息则返回null
             */
            if (respMsg == null || respMsg.getMsgType() == null) {
                return "";
            }
            resp = encoder.encode(respMsg);
        } catch (DuplicateMessageException e) {
            logger.info("此信息被过滤:" + e.getMessage());
        }
        logger.info("处理请求后响应的XML为:" + (resp));
        return resp;
    }

//    /**
//     * 处理消息
//     *
//     * @param request
//     * @return
//     */
//    public String handle(HttpServletRequest request) throws HandleException {
//
//        try {
//            String body = MessageUtil.getRequestBody(request);
//
//            logger.info("接收到来自[" + request.getRemoteHost() + "]的post请求内容为[" + body + "]");
//            if (StringUtils.isEmpty(body)) {
//                return null;
//            }
//
//
//            return handle(body);
//        } catch (Exception e) {
//            throw new HandleException("接收到来自[" + request.getRemoteHost() + "]的post请求内容不能被正确解析\n" + ExceptionUtil.getStackTrace(e));
//        }
//    }


}
