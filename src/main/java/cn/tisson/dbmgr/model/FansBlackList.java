package cn.tisson.dbmgr.model;

import java.util.Date;

public class FansBlackList {
    private Integer id;

    private String fanswebchatid;

    private String servicewebchatid;

    private Date createdate;

    private String desription;

    private byte[] frozentimestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFanswebchatid() {
        return fanswebchatid;
    }

    public void setFanswebchatid(String fanswebchatid) {
        this.fanswebchatid = fanswebchatid == null ? null : fanswebchatid.trim();
    }

    public String getServicewebchatid() {
        return servicewebchatid;
    }

    public void setServicewebchatid(String servicewebchatid) {
        this.servicewebchatid = servicewebchatid == null ? null : servicewebchatid.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription == null ? null : desription.trim();
    }

    public byte[] getFrozentimestamp() {
        return frozentimestamp;
    }

    public void setFrozentimestamp(byte[] frozentimestamp) {
        this.frozentimestamp = frozentimestamp;
    }
}