package cn.tisson.dbmgr.model;

public class CmdConfig {
    private Integer id;

    private String servicewebchatid;

    private Integer fansgroupid;

    private String cmd;

    private String cmdcontend;

    private String type;

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

    public Integer getFansgroupid() {
        return fansgroupid;
    }

    public void setFansgroupid(Integer fansgroupid) {
        this.fansgroupid = fansgroupid;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd == null ? null : cmd.trim();
    }

    public String getCmdcontend() {
        return cmdcontend;
    }

    public void setCmdcontend(String cmdcontend) {
        this.cmdcontend = cmdcontend == null ? null : cmdcontend.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}