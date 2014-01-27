package cn.tisson.platform.protocol.req;

import cn.tisson.util.MessageUtil;

/**
 * 音频消息
 */
public class VoiceReqMsg extends BaseReqMsg {

    public static final String _MediaId = "MediaId";
    public static final String _Format = "Format";

    // 媒体ID
    private String MediaId;
    // 语音格式
    private String Format;

    public VoiceReqMsg() {
        super();
        super.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_VOICE);
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }
}
