package cn.tisson.common;

import cn.tisson.dbmgr.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jasic
 * Date: 13-12-27
 */
public class GlobalCaches {

    // 微信服务号（订阅号）信息（ServiceInfo）(微信号|实体）
    public static Map<String, ServiceInfo> DB_CACHE_SERVICE_INFO = new HashMap<String, ServiceInfo>();

    // 粉丝信息(FansInfo)
    public static Map<Integer, FansInfo> DB_CACHE_FANS_INFO = new HashMap<Integer, FansInfo>();

    // 关注事件推送消息表(SubcEventPushMsg)(ServiceWebChatID/Bean)
    public static Map<String, List<SubcEventRespMsg>> DB_CACHE_SUBC_EVENT_PUSH_MSG = new HashMap<String, List<SubcEventRespMsg>>();
    // 图文消息表(NewsMsg)(PID/BEAN)
    public static Map<Integer, NewsMsg> DB_CACHE_NEWS_MSG = new HashMap<Integer, NewsMsg>();

    // 图文消息表(NewsMsg)(PID/BEAN)
    public static Map<Integer, Text> DB_CACHE_TEXT_MSG = new HashMap<Integer, Text>();


}
