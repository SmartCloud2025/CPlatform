package cn.tisson.platform.protocol.resp;

import cn.tisson.platform.protocol.bean.Image;
import cn.tisson.util.MessageUtil;

/**
 * User: Jasic
 * Date: 13-12-29
 * 回复图片消息
 */
public class ImageRespMsg extends BaseRespMsg {

    public static final String _Image = "Image";

    private Image Image;

    public ImageRespMsg() {
        super();
        super.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_IMAGE);
    }

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        this.Image = image;
    }
}
