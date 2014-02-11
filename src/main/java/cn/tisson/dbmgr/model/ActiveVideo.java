package cn.tisson.dbmgr.model;

import java.util.Date;

public class ActiveVideo {
    private Integer id;

    private String servicewebchatid;

    private String mediaId;

    private String title;

    private String description;

    private Date createdate;

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
        this.servicewebchatid = servicewebchatid == null ? null : servicewebchatid.trim();
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId == null ? null : mediaId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}