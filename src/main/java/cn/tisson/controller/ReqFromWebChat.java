package cn.tisson.controller;

import cn.tisson.common.GlobalVariables;
import cn.tisson.framework.utils.StringUtils;
import cn.tisson.manager.ReqFromWebChatService;
import cn.tisson.platform.protocol.active.Signature;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.jasic.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    @RequestMapping(value = "/token/{webchatId:[\\S]{1,30}}", params = {"signature", "timestamp", "nonce", "echostr"}, method = {RequestMethod.GET})
    public
    @ResponseBody
    String _validateToken(HttpServletRequest request, HttpServletResponse response, @PathVariable("webchatId") final String id) {


        System.out.println("signature" + request.getParameter("signature"));
        System.out.println("timestamp" + request.getParameter("timestamp"));
        System.out.println("nonce" + request.getParameter("nonce"));
        System.out.println("echostr" + request.getParameter("echostr"));
        String path = request.getContextPath();
        String baPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;

        boolean validateSuccess = service.validateToken(baPath, id);

        if (GlobalVariables.WEB_CHAT_LOG_FLAG) {
            logger.info("Url:[" + baPath + "],Token:[" + id + "]的Token在数据库中" + (validateSuccess ? "能" : "不能") + "找到记录");
        }

        Signature signature = service.validatePara(logger, request, response, id);
        if (validateSuccess && signature != null && signature.isSuccess() && !StringUtils.isEmpty(signature.getEchostr())) {
            validateSuccess = true;
        } else {
            validateSuccess = false;
        }

        if (GlobalVariables.WEB_CHAT_LOG_FLAG) {
            logger.info("Url:[" + baPath + "],Token:[" + id + "]的Token验证" + (validateSuccess ? "成功" : "失败"));
        }

        return signature.getEchostr();
//        return validateSuccess ? signature.getEchostr() : "hello";
    }

    /**
     * 处理微信服务器返回xml消息
     *
     * @param body
     * @return
     */
    @RequestMapping(value = "/token/{webchatId:[\\S]{1,30}}", method = {RequestMethod.POST})
    public
    @ResponseBody
    String _handleMessage(@RequestBody String body,HttpRequest request,HttpResponse response,HttpSession session) {

        String resp = null;
        try {
            resp = service.handle(body);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
        }finally {
            if (resp == null){
               response.setEntity(null);
            }
        }

        return resp;
    }
}
