package cn.tisson.platform.protocol.active;

/**
 * User: Jasic
 * Date: 13-12-4
 */
public class ErrorRespond extends BaseRespond {

    private String errcode;
    private String errmsg;

    public ErrorRespond() {
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
