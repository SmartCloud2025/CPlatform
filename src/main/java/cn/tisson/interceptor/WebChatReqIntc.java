package cn.tisson.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jasic
 * Date: 13-12-25
 * 微信请求拦器
 */
public class WebChatReqIntc extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(WebChatReqIntc.class);

    /**
     * 远程ip
     */
    private String remoteIp;

    /**
     * 请求的url
     */
    private String reqUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        String host = request.getRemoteHost();
        logger.info("Config url: " + reqUrl + ",actual Url: " + url);
        logger.info("Config ip: " + remoteIp + ",actual Ip: " + host);
        return true;
    }

    /**
     * 这个方法是在Controller处理之后
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        String reqUrl = request.getRequestURI();
        modelAndView.addObject("Add", reqUrl);
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }
}
