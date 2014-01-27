package cn.tisson.platform.protocol.resp;

import cn.tisson.platform.protocol.bean.Music;
import cn.tisson.util.MessageUtil;

/**
 * 音乐消息
 */
public class MusicRespMsg extends BaseRespMsg {

    public static final String _Music = "Music";
    // 音乐
    private Music Music;

    public MusicRespMsg() {
        super();
        super.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
    }

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}
