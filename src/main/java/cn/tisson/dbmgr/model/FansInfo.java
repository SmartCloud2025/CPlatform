package cn.tisson.dbmgr.model;

import java.util.Date;

public class FansInfo {
    private Integer id;

    private String webchatid;

    private Integer fansgroupid;

    private String fanname;

    private String address;

    private String sex;

    private Date focustime;

    private String focustype;

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

    public Integer getFansgroupid() {
        return fansgroupid;
    }

    public void setFansgroupid(Integer fansgroupid) {
        this.fansgroupid = fansgroupid;
    }

    public String getFanname() {
        return fanname;
    }

    public void setFanname(String fanname) {
        this.fanname = fanname == null ? null : fanname.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getFocustime() {
        return focustime;
    }

    public void setFocustime(Date focustime) {
        this.focustime = focustime;
    }

    public String getFocustype() {
        return focustype;
    }

    public void setFocustype(String focustype) {
        this.focustype = focustype == null ? null : focustype.trim();
    }
}