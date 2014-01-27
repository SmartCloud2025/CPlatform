package cn.tisson.common;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: Jasic
 * Date: 13-12-4
 */
public class SystemInit {

    public static boolean init() {

        try {
            initRespCode();
            GlobalVariables.GLOBAL_ACCESS_MAP = new HashMap<Date, String>();
        } catch (Exception e) {
            System.out.println("初始化缓存出错，程序退出");
            return false;
        }
        return true;
    }

    public static void initRespCode() {

        GlobalVariables.DAEMON_HANDLE_CMD_CODE_MAP = new ConcurrentHashMap<String, String>();
        // 处理后端命令结果码
        GlobalVariables.DAEMON_HANDLE_CMD_CODE_MAP.put(GlobalConstants.HANDLE_CMD_SUCCESS, "命令处理成功");
        GlobalVariables.DAEMON_HANDLE_CMD_CODE_MAP.put(GlobalConstants.HANDLE_CMD_HTTP_REQUEST_ERROR, "命令HTTP请求格式不正确");
        GlobalVariables.DAEMON_HANDLE_CMD_CODE_MAP.put(GlobalConstants.HANDLE_CMD_NOT_COMPLETED, "命令格式不完整");
        GlobalVariables.DAEMON_HANDLE_CMD_CODE_MAP.put(GlobalConstants.HANDLE_CMD_NOT_EXIST, "命令不存在");
        GlobalVariables.DAEMON_HANDLE_CMD_CODE_MAP.put(GlobalConstants.HANDLE_CMD_SHOPID_NOT_EXIST, "商户Id不存在");
        GlobalVariables.DAEMON_HANDLE_CMD_CODE_MAP.put(GlobalConstants.HANDLE_CMD_LOGIN_ERROR, "商户登录失败");
        GlobalVariables.DAEMON_HANDLE_CMD_CODE_MAP.put(GlobalConstants.HANDLE_CMD_ERROR, "请求处理失败");


        // 微信错误码
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP = new HashMap<String, String>();
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("-1", "系统繁忙");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("0", "请求成功");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40001", "获取access_token时AppSecret错误，或者access_token无效");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40002", "不合法的凭证类型");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40003", "不合法的OpenID");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40004", "不合法的媒体文件类型");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40005", "不合法的文件类型");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40006", "不合法的文件大小");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40007", "不合法的媒体文件id");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40008", "不合法的消息类型");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40009", "不合法的图片文件大小");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40010", "不合法的语音文件大小");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40011", "不合法的视频文件大小");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40012", "不合法的缩略图文件大小");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40013", "不合法的APPID");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40014", "不合法的access_token");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40015", "不合法的菜单类型");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40016", "不合法的按钮个数");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40017", "不合法的按钮个数");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40018", "不合法的按钮名字长度");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40019", "不合法的按钮KEY长度");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40020", "不合法的按钮URL长度");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40021", "不合法的菜单版本号");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40022", "不合法的子菜单级数");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40023", "不合法的子菜单按钮个数");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40024", "不合法的子菜单按钮类型");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40025", "不合法的子菜单按钮名字长度");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40026", "不合法的子菜单按钮KEY长度");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40027", "不合法的子菜单按钮URL长度");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40028", "不合法的自定义菜单使用用户");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40029", "不合法的oauth_code");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40030", "不合法的refresh_token");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40031", "不合法的openid列表");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40032", "不合法的openid列表长度");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40033", "不合法的请求字符，不能包含xxx格式的字符");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40035", "不合法的参数");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40038", "不合法的请求格式");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40039", "不合法的URL长度");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40050", "不合法的分组id");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("40051", "分组名字不合法");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("41001", "缺少access_token参数");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("41002", "缺少appid参数");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("41003", "缺少refresh_token参数");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("41004", "缺少secret参数");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("41005", "缺少多媒体文件数据");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("41006", "缺少media_id参数");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("41007", "缺少子菜单数据");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("41008", "缺少oauthcode");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("41009", "缺少openid");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("42001", "access_token超时");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("42002", "refresh_token超时");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("42003", "oauth_code超时");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("43001", "需要GET请求");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("43002", "需要POST请求");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("43003", "需要HTTPS请求");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("43004", "需要接收者关注");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("43005", "需要好友关系");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("44001", "多媒体文件为空");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("44002", "POST的数据包为空");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("44003", "图文消息内容为空");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("44004", "文本消息内容为空");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("45001", "多媒体文件大小超过限制");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("45002", "消息内容超过限制");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("45003", "标题字段超过限制");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("45004", "描述字段超过限制");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("45005", "链接字段超过限制");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("45006", "图片链接字段超过限制");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("45007", "语音播放时间超过限制");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("45008", "图文消息超过限制");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("45009", "接口调用超过限制");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("45010", "创建菜单个数超过限制");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("45015", "回复时间超过限制");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("45016", "系统分组，不允许修改");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("45017", "分组名字过长");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("45018", "分组数量超过上限");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("46001", "不存在媒体数据");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("46002", "不存在的菜单版本");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("46003", "不存在的菜单数据");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("46004", "不存在的用户");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("47001", "解析JSON/XML内容错误");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("48001", "api功能未授权");
        GlobalVariables.WEB_CHAT_RESP_CODE_MAP.put("50001", "用户未授权该api");
    }

}
