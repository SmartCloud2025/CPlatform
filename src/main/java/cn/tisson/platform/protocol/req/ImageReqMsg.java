package cn.tisson.platform.protocol.req;


import cn.tisson.util.MessageUtil;

/**
 * 图片消息
 */
public class ImageReqMsg extends BaseReqMsg {
    public static final String _PicUrl = "PicUrl";
    public static final String _MediaId = "MediaId";

    // 图片链接
    private String PicUrl;

    public ImageReqMsg() {
        super();
        super.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_IMAGE);
    }

    // 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
    private String MediaId;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
