package cn.tisson.common;


import cn.tisson.dbmgr.model.NewsMsg;
import cn.tisson.dbmgr.model.ServiceInfo;
import cn.tisson.dbmgr.model.SubcEventRespMsg;
import cn.tisson.dbmgr.model.Text;
import cn.tisson.dbmgr.service.NewsMsgService;
import cn.tisson.dbmgr.service.ServiceInfoService;
import cn.tisson.dbmgr.service.SubcEventRespMsgService;
import cn.tisson.dbmgr.service.TextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: Jasic
 * Date: 13-12-27
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RefreshHelper {
    private static Logger logger = LoggerFactory.getLogger(RefreshHelper.class);


    @Resource
    ServiceInfoService serviceInfoServI;

    @Resource
    NewsMsgService newsMsgServI;

    @Resource
    SubcEventRespMsgService subcEventRespMsgService;

    @Resource
    TextService textService;

    public void refresh() throws Exception {

        refreshServiceInfo();
        refreshSubcEventPushMsg();
        refreshNewsMsg();
        refreshTextMsg();

    }


    /**
     * 刷新服务号、订阅号
     *
     * @throws Exception
     */
    public void refreshServiceInfo() throws Exception {
        List<ServiceInfo> list = serviceInfoServI.getAll();
        synchronized (GlobalCaches.DB_CACHE_SERVICE_INFO) {
            GlobalCaches.DB_CACHE_SERVICE_INFO = new HashMap<String, ServiceInfo>();
            for (ServiceInfo info : list) {
                GlobalCaches.DB_CACHE_SERVICE_INFO.put(info.getWebchatid(), info);
            }
        }
        logger.info("--刷新服务号数量:" + list.size());
    }


    /**
     * 关注事件推送消息表(SubcEventPushMsg)
     */
    public void refreshSubcEventPushMsg() throws Exception {

        List<SubcEventRespMsg> list = subcEventRespMsgService.getAll();
        synchronized (GlobalCaches.DB_CACHE_SUBC_EVENT_PUSH_MSG) {
            GlobalCaches.DB_CACHE_SUBC_EVENT_PUSH_MSG = new HashMap<String, List<SubcEventRespMsg>>();

            for (SubcEventRespMsg info : list) {
                String webChatID = info.getServicewebchatid();
                List<SubcEventRespMsg> l = GlobalCaches.DB_CACHE_SUBC_EVENT_PUSH_MSG.get(webChatID);

                if (l == null) {
                    l = new ArrayList<SubcEventRespMsg>();
                    GlobalCaches.DB_CACHE_SUBC_EVENT_PUSH_MSG.put(webChatID, l);
                }
                l.add(info);
            }
        }
        logger.info("--关注事件推送消息数量:" + list.size());
    }

    /**
     * 图文推送消息表(NewsMsg)
     */
    public void refreshNewsMsg() throws Exception {

        List<NewsMsg> list = newsMsgServI.getAll();
        synchronized (GlobalCaches.DB_CACHE_NEWS_MSG) {
            GlobalCaches.DB_CACHE_NEWS_MSG = new HashMap<Integer, NewsMsg>();

            for (NewsMsg info : list) {
                GlobalCaches.DB_CACHE_NEWS_MSG.put(info.getId(), info);
            }
        }
        logger.info("--图文推送消息表数量:" + list.size());
    }


    /**
     * 图文推送消息表(NewsMsg)
     */
    public void refreshTextMsg() throws Exception {

        List<Text> list = textService.getAll();
        synchronized (GlobalCaches.DB_CACHE_TEXT_MSG) {
            GlobalCaches.DB_CACHE_TEXT_MSG = new HashMap<Integer, Text>();

            for (Text info : list) {
                GlobalCaches.DB_CACHE_TEXT_MSG.put(info.getId(), info);
            }
        }
        logger.info("--文字推送消息表数量:" + list.size());
    }
}
