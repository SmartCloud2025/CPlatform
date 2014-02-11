package cn.tisson.platform.protocol.active.servReq;

import com.alibaba.fastjson.JSON;

/**
 * Author Jasic
 * Date 14-2-8.
 */
public abstract class BaseServReq {

    private String touser;

    private String msgtype;

    public BaseServReq(String touser, String msgtype) {
        this.touser = touser;
        this.msgtype = msgtype;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }


}



