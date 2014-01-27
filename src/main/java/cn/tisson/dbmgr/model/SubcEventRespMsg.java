package cn.tisson.dbmgr.model;

public class SubcEventRespMsg {
    private Integer id;

    private String servicewebchatid;

    private String type;

    private Integer msgid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServicewebchatid() {
        return servicewebchatid;
    }

    public void setServicewebchatid(String servicewebchatid) {
        this.servicewebchatid = servicewebchatid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getMsgid() {
        return msgid;
    }

    public void setMsgid(Integer msgid) {
        this.msgid = msgid;
    }
}