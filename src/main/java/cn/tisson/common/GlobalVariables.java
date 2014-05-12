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

    // 数据库刷新基础数据的时间间隔(单位秒）
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


    // 获取Access_Token的通用url
    public static String GET_ACCESS_TOOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={AppID}&secret={AppSecret}";

    // access_token有效时间（单位s）
    public static int ACCESS_TOKEN_EXPIRE_TIME = 7200;

    // 发送客户消息的通用URL
    public static String SEND_ACTIVE_BASE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={ACCESS_TOKEN}";

    // 推送消息的时间间隔（单位秒）（需要大于数据库的缓存刷新时间）>=DB_REFRESH_INTERVAL
    public static int ACTIVE_PUSH_MSG_INTERVAL = 10;
    // 刷新access_token的时间间隔 （单位秒）（需要大于数据库的缓存刷新时间）>=DB_REFRESH_INTERVAL
    public static final int ACCESS_TOKEN_INTERVAL_TIME = 10;

    // 微信超时时间
    public static long WEB_CHAT_POST_TIME_OUT = 5000;


    //--------------------------------------- 指令配置设置 --------------------------------------
    // 微信指令配置的默认分隔符号
    public static String CMD_DEFAULT_SEPERATOR = " ";
    // 指令配置服务调用的基本路径
    public static String CMD_SERVICE_BASE_URL = "http://192.168.1.111:8080/webPhone/cmd";
    // WebPhone基本路径
    public static String WEBPHONE_BASE_URL = "http://192.168.1.111:8080/webPhone";

    // 多个空格转成一个空格
    public static String REPLACE_SPACE = "[ ]{2,}";
}

