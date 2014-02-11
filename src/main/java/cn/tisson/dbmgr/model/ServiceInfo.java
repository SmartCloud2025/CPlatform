package cn.tisson.dbmgr.model;

import java.util.Date;
import java.util.List;

public class ServiceInfo {

    private List<FansGroup> fansGroups;

    public List<FansGroup> getFansGroups() {
        return fansGroups;
    }

    public void setFansGroups(List<FansGroup> fansGroups) {
        this.fansGroups = fansGroups;
    }

    private Integer id;

    private String webchatid;

    private String servicetype;

    private String url;

    private String token;

    private String appid;

    private String appsecret;

    private Date bounddate;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWebchatid() {
        return webchatid;
    }

    public void setWebchatid(String webchatid) {
        this.webchatid = webchatid == null ? null : webchatid.trim();
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype == null ? null : servicetype.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret == null ? null : appsecret.trim();
    }

    public Date getBounddate() {
        return bounddate;
    }

    public void setBounddate(Date bounddate) {
        this.bounddate = bounddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}