package cn.tisson.platform.protocol.req;

import cn.tisson.util.MessageUtil;

/**
 * User: Jasic
 * Date: 13-12-28
 * 视频消息
 */
public class VideoReqMsg extends BaseReqMsg {

    public static final String _MediaId = "MediaId";
    public static final String _ThumbMediaId = "ThumbMediaId";

    /**
     * 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    private String MediaId;

    /**
     * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    private String ThumbMediaId;

    public VideoReqMsg() {
        super();
        super.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_VIDEO);
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }
}
