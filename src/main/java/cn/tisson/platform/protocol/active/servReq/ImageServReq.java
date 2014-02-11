package cn.tisson.platform.protocol.active.servReq;

import com.alibaba.fastjson.JSON;


public class ImageServReq extends BaseServReq {

    public static final String MSGTYPE = "image";

    private Image image;

    public ImageServReq(String touser, String media_id) {
        super(touser, MSGTYPE);
        image = new Image();
        image.setMedia_id(media_id);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    private class Image {
        private String media_id;

        public String getMedia_id() {
            return media_id;
        }

        public void setMedia_id(String media_id) {
            this.media_id = media_id;
        }
    }


    public static void main(String[] args) {
        ImageServReq imageServReq = new ImageServReq("Jasic","media_id");


        System.out.println(JSON.toJSONString(imageServReq));
    }
}
