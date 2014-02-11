package cn.tisson.platform.protocol.active.servReq;

public class VideoServReq extends BaseServReq {

    public final static String MSGTYPE = "video";

    private Video video;

    public VideoServReq(String touser,String media_id, String title,String description) {
        super(touser, MSGTYPE);
        video = new Video(media_id,title,description);
    }

    private class Video{

        private String media_id;
        private String title;
        private String description;

        private Video(String media_id, String title, String description) {
            this.media_id = media_id;
            this.title = title;
            this.description = description;
        }

        public String getMedia_id() {
            return media_id;
        }

        public void setMedia_id(String media_id) {
            this.media_id = media_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
