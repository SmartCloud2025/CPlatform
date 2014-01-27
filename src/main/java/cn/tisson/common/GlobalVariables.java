package cn.tisson.common;

import cn.tisson.platform.protocol.active.Signature;
import cn.tisson.platform.protocol.active.TokenRespond;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局变量定义类
 * <p/>
 * author Ycz
 */
public class GlobalVariables {

    public static RefreshHelper REFRESH_HELPER;

    //--------------------------------------- 路径设置 --------------------------------------
    public static String PATH_WEB_CHAT_TOKEN = "/platform/token";


    // 微信错误码
    public static Map<String, String> WEB_CHAT_RESP_CODE_MAP;

    // 后端通讯错误码
    public static Map<String, String> DAEMON_HANDLE_CMD_CODE_MAP;

    public static TokenRespond GLOBAL_TOKEN_RESPOND;

    // 数据库刷新基础数据的时间间隔
    public static int DB_REFRESH_INTERVAL = 30;


    // 运行标志
    public static volatile boolean GLOBAL_APP_RUNNING_FLAG = true;
    public static Signature GLOBAL_SIGNATURE;


    public static String GLOBAL_VALIDATED_TOKEN = "bassice";
    public static Map<Date, String> GLOBAL_ACCESS_MAP = new HashMap<Date, String>();

    // 打印从web传过来的数据日志
    public static boolean WEB_CHAT_LOG_FLAG = true;
    // 是否向后端发送错误信息
    public static boolean TERMIAL_EXEPTION_MSG_LOG_FLAG = true;

    // 排重延迟时间
    public static long WEB_CHAT_EXCLUDE_DUPLICATE_DELAY = 1;

}

