package cn.tisson.platform.protocol.resp;


import com.alibaba.fastjson.JSON;

/**
 * 消息基类（公众帐号 -> 普通用户）
 */
public class BaseRespMsg {

    private static final String _ToUserName = "ToUserName";
    private static final String _FromUserName = "FromUserName";
    private static final String _CreateTime = "CreateTime";
    private static final String _MsgType = "MsgType";

    // 接收方帐号（收到的OpenID）
    private String ToUserName;
    // 开发者微信号
    private String FromUserName;
    // 消息创建时间 （整型）
    private long CreateTime;
    // 消息类型（text/music/news）
    private String MsgType;


    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
