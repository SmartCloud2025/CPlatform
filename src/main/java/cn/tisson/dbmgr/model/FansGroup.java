package cn.tisson.dbmgr.model;

import java.util.Date;
import java.util.List;

public class FansGroup {

    private List<FansInfo> fansInfoList;

    public List<FansInfo> getFansInfoList() {
        return fansInfoList;
    }

    public void setFansInfoList(List<FansInfo> fansInfoList) {
        this.fansInfoList = fansInfoList;
    }

    private Integer id;
    private Integer priority;

    private String servicewebchatid;

    private String groupname;

    private String grouptype;

    private Date createdate;

    private String status;

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

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname == null ? null : groupname.trim();
    }

    public String getGrouptype() {
        return grouptype;
    }

    public void setGrouptype(String grouptype) {
        this.grouptype = grouptype == null ? null : grouptype.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}