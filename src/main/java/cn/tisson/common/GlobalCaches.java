package cn.tisson.common;

import cn.tisson.dbmgr.model.*;
import cn.tisson.platform.protocol.active.TokenRespond;

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

    // 关注事件推送消息表(SubcEventPushMsg)(ServiceWebChatID/Bean)
    public static Map<String, List<SubcEventRespMsg>> DB_CACHE_SUBC_EVENT_PUSH_MSG = new HashMap<String, List<SubcEventRespMsg>>();
    // 图文消息表(NewsMsg)(PID/BEAN)
    public static Map<Integer, NewsMsg> DB_CACHE_NEWS_MSG = new HashMap<Integer, NewsMsg>();

    // 图文消息表(NewsMsg)(PID/BEAN)
    public static Map<Integer, Text> DB_CACHE_TEXT_MSG = new HashMap<Integer, Text>();

    // 命令(CmdConfig)(PID/BEAN)
    public static Map<Integer, CmdConfig> DB_CACHE_CMD_CONFIG = new HashMap<Integer, CmdConfig>();

   // 命令(CmdConfig)(WebChatID/BEAN)
    public static Map<String, List<MassPushMsg>> DB_CACHE_MASS_PUSH_MSG = new HashMap<String, List<MassPushMsg>>();

    // WE
    public static Map<String,TokenRespond> ACCESS_TOKEN_CACHE  = new HashMap<String, TokenRespond>();
}
