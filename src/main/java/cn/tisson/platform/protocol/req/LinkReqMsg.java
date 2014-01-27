package cn.tisson.platform.protocol.req;

import cn.tisson.util.MessageUtil;

/**
 * 链接消息
 */
public class LinkReqMsg extends BaseReqMsg {
    public static final String _Title = "Title";
    public static final String _Description = "Description";
    public static final String _Url = "Url";


    // 消息标题
    private String Title;
    // 消息描述
    private String Description;
    // 消息链接
    private String Url;

    public LinkReqMsg() {
        super();
        super.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_LINK);
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
