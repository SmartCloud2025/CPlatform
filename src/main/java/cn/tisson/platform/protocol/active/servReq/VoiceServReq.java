package cn.tisson.platform.protocol.active.servReq;


public class VoiceServReq extends BaseServReq {

    public static final String MSGTYPE = "voice";

    private Voice voice;

    public VoiceServReq(String touser, String media_id) {
        super(touser, MSGTYPE);
        voice = new Voice();
        voice.setMedia_id(media_id);
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    private class Voice{
        private String media_id;

        public String getMedia_id() {
            return media_id;
        }

        public void setMedia_id(String media_id) {
            this.media_id = media_id;
        }
    }
}
