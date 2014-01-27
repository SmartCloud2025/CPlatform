package cn.tisson.platform.protocol.bean;

/**
 * User: Jasic
 * Date: 13-12-29
 * 回复视频实体
 */
public class Video {

    public static final String _MediaId = "MediaId";
    public static final String _Title = "Title";
    public static final String _Description = "Description";

    private String MediaId;
    private String Title;
    private String Description;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
