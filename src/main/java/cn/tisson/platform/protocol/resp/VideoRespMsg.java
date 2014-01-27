package cn.tisson.platform.protocol.resp;

import cn.tisson.platform.protocol.bean.Video;
import cn.tisson.util.MessageUtil;

/**
 * User: Jasic
 * Date: 13-12-29
 * 回复视频消息
 */
public class VideoRespMsg extends BaseRespMsg {

    public static final String _Video = "Video";
    private Video Video;

    public VideoRespMsg() {
        super();
        super.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_VIDEO);
    }

    public Video getVideo() {
        return Video;
    }

    public void setVideo(Video video) {
        Video = video;
    }
}
