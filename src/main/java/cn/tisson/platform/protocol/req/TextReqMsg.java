package cn.tisson.platform.protocol.req;

import cn.tisson.util.MessageUtil;


/**
 * 文本消息
 */
public class TextReqMsg extends BaseReqMsg {
    public static final String _Content = "Content";

    // 消息内容  
    private String Content;

    public TextReqMsg() {
        super();
        super.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}  
