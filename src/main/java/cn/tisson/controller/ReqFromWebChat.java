package cn.tisson.controller;

import cn.tisson.common.GlobalVariables;
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
    String _handleMessage(@RequestBody String body) {

        String resp = null;
        try {
            resp = service.handle(body);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
        }
        return resp;
    }


    @RequestMapping("/reqBody")
    public String getBody(String body) {

        // 这里的 body 的内容就是 hello
        System.out.println(body);
        return null;
    }

    @RequestMapping("/respBody")
    @ResponseBody
    public User getUser() {
        return new User("Jack", 18);
    }

    /**
     * * jsp 里可以这样访问模型里的数据：
     * age: ${user.age}
     * name: ${user.name}
     * job: ${user.job}
     *
     * @return
     */
    @RequestMapping("/user")
    @ModelAttribute
    public User getUser1() {
        return new User("Jack", 18);
    }

    /**
     * 这里将 @ModelAttribute 标注在方法参数上，表示要从模型数据里取 key 为 "user" 的对象，绑定在方法
     * 参数上。如果这样做的话，其实你是得不到上面的那个请求放入的 User 对象，得到的是另外一个对象。其实
     * 也好理解，这是两个互相独立的请求，作用域不一样。要想达到我们的目的，即能够从模型数据里取数据，需要
     *
     * @param user
     * @return
     */
    @RequestMapping("/user2")
    public String showUser(@ModelAttribute User user) {
        System.out.println(user);
        return null;
    }


    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    public String validateBaseUrl() {
        return "errro404";
    }

    class User {
        private String name;
        private int age;

        User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
