package cn.tisson.platform.protocol.active.servReq;


public class TextServReq extends BaseServReq {

    public static final String MSGTYPE = "text";

    private Text text;

    public TextServReq(String touser, String content) {
        super(touser, MSGTYPE);
        text = new Text();
        text.setContent(content);

    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    private class Text {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
