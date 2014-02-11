package cn.tisson.platform.protocol.active;

import java.util.Date;

/**
 * User: Jasic
 * Date: 13-12-4
 * 组成AccessToken的实体
 */
public class TokenRespond extends BaseRespond {
    private String access_token;
    private String expires_in;


    private Date date;


    public TokenRespond() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
}
