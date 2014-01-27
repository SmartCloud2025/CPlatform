package cn.tisson.platform.protocol.bean;

/**
 * 回复音乐实体
 */
public class Music {

    public static final String _Title = "Title";
    public static final String _Description = "Description";
    public static final String _MusicUrl = "MusicUrl";
    public static final String _HQMusicUrl = "HQMusicUrl";
    public static final String _ThumbMediaId = "ThumbMediaId";

    // 音乐名称
    private String Title;
    // 音乐描述
    private String Description;
    // 音乐链接
    private String MusicUrl;
    // 高质量音乐链接，WIFI环境优先使用该链接播放音乐
    private String HQMusicUrl;
    // 缩略图的媒体id，通过上传多媒体文件，得到的id
    private String ThumbMediaId;


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

    public String getMusicUrl() {
        return MusicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        MusicUrl = musicUrl;
    }

    public String getHQMusicUrl() {
        return HQMusicUrl;
    }

    public void setHQMusicUrl(String musicUrl) {
        HQMusicUrl = musicUrl;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }
}
