package cn.tisson.platform.protocol.codec;

import cn.tisson.common.GlobalVariables;
import cn.tisson.platform.protocol.resp.*;
import cn.tisson.util.MessageUtil;
import org.jasic.util.Asserter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * User: Jasic
 * Date: 13-12-28
 */
@Component("WebChatMsgEncoder")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.DEFAULT)
public class WebChatMsgEncoder<M extends BaseRespMsg> {

    private Charset charset;

    public WebChatMsgEncoder() {

        this(Charset.defaultCharset());
    }

    public WebChatMsgEncoder(Charset charset) {
        Asserter.notNull(charset);
        this.charset = charset;
    }

    /**
     * 将实体转换xml
     *
     * @param msg
     * @return
     */
    public String encode(M msg) {
        String msgType = msg.getMsgType();

        String fanId = msg.getToUserName();
        String serviceId = msg.getFromUserName();

        String resp = "";

        /**
         * 回复文本消息
         */
        if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_TEXT)) {
            resp = MessageUtil.textMessageToXml((TextRespMsg) msg);
        }

        /**
         * 回复图片消息
         */
        else if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_IMAGE)) {
            resp = MessageUtil.imageMessageToXml((ImageRespMsg) msg);
        }

        /**
         * 回复语音消息
         */
        else if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_VOICE)) {
            resp = MessageUtil.voiceMessageToXml((VoiceRespMsg) msg);
        }

        /**
         * 回复视频消息
         */
        else if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_VIDEO)) {
            resp = MessageUtil.videoMessageToXml((VideoRespMsg) msg);
        }
        /**
         * 回复音乐消息
         */
        else if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_MUSIC)) {
            resp = MessageUtil.musicMessageToXml((MusicRespMsg) msg);
        }

        /**
         * 回复图文消息
         */
        else if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_NEWS)) {
            resp = MessageUtil.newsMessageToXml((NewsRespMsg) msg);
        }
        return resp.replaceAll("\\{fanId\\}", fanId).replaceAll("\\{serviceId\\}", serviceId).replaceAll("\\{WebPhoneURL\\}", GlobalVariables.WEBPHONE_BASE_URL);
    }
}
