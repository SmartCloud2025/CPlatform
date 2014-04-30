package cn.tisson.controller;

import cn.tisson.common.GlobalVariables;
import cn.tisson.dbmgr.model.ServiceInfo;
import cn.tisson.framework.utils.StringUtils;
import cn.tisson.manager.ReqFromWebChatService;
import cn.tisson.platform.protocol.active.Signature;
import org.jasic.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jasic
 * Date: 13-12-25
 */
@Controller("reqFromWebChat")
@RequestMapping("/webchat")
public class ReqFromWebChat {

    private static Logger logger = LoggerFactory.getLogger(ReqFromWebChat.class);

    @Autowired
    private ReqFromWebChatService service;

    /**
     * 处理微信验证
     *
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping(value = "/token/{webchatId:[\\S]{1,256}}", params = {"signature", "timestamp", "nonce", "echostr"}, method = {RequestMethod.GET})
    public
    @ResponseBody
    String _validateToken(HttpServletRequest request, HttpServletResponse response, @PathVariable("webchatId") final String id) {

//        System.out.println("signature" + request.getParameter("signature"));
//        System.out.println("timestamp" + request.getParameter("timestamp"));
//        System.out.println("nonce" + request.getParameter("nonce"));
//        System.out.println("echostr" + request.getParameter("echostr"));

        String baPath = request.getRequestURL().toString();
        ServiceInfo serviceInfo = service.getServiceInfoByUrl(baPath);
        boolean validateSuccess = (serviceInfo != null);

        if (GlobalVariables.WEB_CHAT_LOG_FLAG) {
            logger.info("Url:[" + baPath + "]的服务号在数据库中" + (validateSuccess ? "能" : "不能") + "找到记录");
        }

        Signature signature = service.validatePara(logger, request, response, serviceInfo.getToken());
        if (validateSuccess && signature != null && signature.isSuccess() && !StringUtils.isEmpty(signature.getEchostr())) {
            validateSuccess = true;
        } else {
            validateSuccess = false;
        }

        if (GlobalVariables.WEB_CHAT_LOG_FLAG) {
            logger.info("Url:[" + baPath + "],Token:[" + serviceInfo.getToken() + "]的Token验证" + (validateSuccess ? "成功" : "失败"));
        }

        if (validateSuccess)
            return signature.getEchostr();
        else return "";
    }

    /**
     * 处理微信服务器返回xml消息
     *
     * @param body
     * @return
     */
    @RequestMapping(value = "/token/{webchatId:[\\S]{1,256}}", produces = "application/xml;charset=UTF-8", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    String _handleMessage(@RequestBody String body, HttpServletResponse response, HttpServletRequest request) {

        String resp = null;

        String url = request.getRequestURL().toString();
        boolean exist = service.validateUrl(url);

        if (!exist) {
            logger.error("配置中找不到url[" + url + "]");
            return null;
        }
        try {
            resp = service.handle(body, url);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
        }

        return resp;
    }

    /**
     * 获取请求的地址
     *
     * @param request
     * @return
     */
    public String getReqUrl(HttpServletRequest request) {
        String path = request.getContextPath();
        int port = request.getServerPort();
        String baPath = request.getScheme() + "://" + request.getServerName() + (port == 80 ? "" : ":" + port) + path;

        return baPath;
    }
}
