package cn.tisson.platform.protocol.resp;


import cn.tisson.util.MessageUtil;

/**
 * 文本消息
 */
public class TextRespMsg extends BaseRespMsg {

    private static final String _Content = "Content";

    // 回复的消息内容
    private String Content;

    public TextRespMsg() {
        super();
        super.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
