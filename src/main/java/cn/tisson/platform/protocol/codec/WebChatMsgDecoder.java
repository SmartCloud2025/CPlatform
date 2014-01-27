package cn.tisson.platform.protocol.codec;

import cn.tisson.exception.ParseMessageException;
import cn.tisson.framework.utils.StringUtils;
import cn.tisson.platform.protocol.bean.Event;
import cn.tisson.platform.protocol.req.*;
import cn.tisson.platform.protocol.req.event.*;
import cn.tisson.util.MessageUtil;
import org.jasic.util.Asserter;
import org.jasic.util.ExceptionUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * User: Jasic
 * Date: 13-12-28
 * <p/>
 * 微信消息解码器
 */
@Component("WebChatMsgDecoder")
@Scope
public class WebChatMsgDecoder {

    private final static Charset defaultCharset = Charset.forName("UTF-8");

    private Charset charset;

    public WebChatMsgDecoder() {

        this(defaultCharset);
    }

    public WebChatMsgDecoder(Charset charset) {
        Asserter.notNull(charset);
        this.charset = charset;
    }


    /**
     * 解码过程
     *
     * @param body
     * @return
     * @throws Exception
     */
    public BaseReqMsg decode(String body) throws ParseMessageException {

        // 解析当前请求实体
        BaseReqMsg message = null;
        try {

            String decodeStr = URLDecoder.decode(body, charset.displayName());
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(decodeStr);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get(BaseReqMsg._FromUserName);
            // 公众帐号
            String toUserName = requestMap.get(BaseReqMsg._ToUserName);
            // 创建时间
            String createTime = requestMap.get(BaseReqMsg._CreateTime);
            // 消息类型
            String msgType = requestMap.get(BaseReqMsg._MsgType);
            // 消息标识
            String msgId = requestMap.get(BaseReqMsg._MsgId);

            Asserter.notNull(fromUserName, "[Assertion failed] - (fromUserName) is required; it must not be null");
            Asserter.notNull(toUserName, "[Assertion failed] - (toUserName) is required; it must not be null");
            Asserter.notNull(createTime, "[Assertion failed] - (createTime) is required; it must not be null");
            Asserter.notNull(msgType, "[Assertion failed] - (msgType) is required; it must not be null");

            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                TextReqMsg msg = new TextReqMsg();
                msg.setContent(requestMap.get(TextReqMsg._Content));
                if (msg.getContent() != null) {
                    message = msg;
                }
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                ImageReqMsg msg = new ImageReqMsg();
                msg.setPicUrl(requestMap.get(ImageReqMsg._PicUrl));
                if (msg.getPicUrl() != null) message = msg;

            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                LocationReqMsg msg = new LocationReqMsg();
                msg.setLocation_X(requestMap.get(LocationReqMsg._Location_X));
                msg.setLocation_Y(requestMap.get(LocationReqMsg._Location_Y));
                msg.setScale(requestMap.get(LocationReqMsg._Scale));
                msg.setLabel(requestMap.get(LocationReqMsg._Label));
                if (!StringUtils.hasNull(new String[]{msg.getLocation_X(), msg.getLocation_Y(), msg.getScale(), msg.getLabel()}))
                    message = msg;
            }

            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                LinkReqMsg msg = new LinkReqMsg();
                msg.setTitle(requestMap.get(LinkReqMsg._Title));
                msg.setDescription(requestMap.get(LinkReqMsg._Description));
                msg.setUrl(requestMap.get(LinkReqMsg._Url));
                if (!StringUtils.hasNull(new String[]{msg.getTitle(), msg.getDescription(), msg.getUrl()}))
                    message = msg;
            }

            // 音频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                VoiceReqMsg msg = new VoiceReqMsg();
                msg.setMediaId(requestMap.get(VoiceReqMsg._MediaId));
                msg.setFormat(requestMap.get(VoiceReqMsg._Format));
                if (!StringUtils.hasNull(new String[]{msg.getMediaId(), msg.getFormat()})) message = msg;
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
                VideoReqMsg msg = new VideoReqMsg();
                msg.setMediaId(requestMap.get(VideoReqMsg._MediaId));
                msg.setThumbMediaId(requestMap.get(VideoReqMsg._ThumbMediaId));
                if (!StringUtils.hasNull(new String[]{msg.getMediaId(), msg.getThumbMediaId()})) message = msg;
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {

                // 事件类型
                String eventType = requestMap.get(EventReqMsg._Event);

                // 订阅事类型
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {

                    /**
                     * 通过eventkey、Ticket来区分扫描关注还是普通关注
                     */
                    String EventKey = requestMap.get(ScanHasSubEventReqMsg._EventKey);
                    String Ticket = requestMap.get(ScanNotSubEventReqMsg._Ticket);

                    // 空则为普通订阅相反则为扫描订阅
                    if (!StringUtils.hasEmpty(new String[]{EventKey, Ticket}) && EventKey.startsWith(ScanNotSubEventReqMsg._EventKey_PRE)) {
                        ScanNotSubEventReqMsg msg = new ScanNotSubEventReqMsg();
                        msg.setEvent(Event.subscribe);
                        msg.setEventKey(EventKey);
                        msg.setTicket(Ticket);
                        message = msg;
                    } else {
                        SubEventReqMsg msg = new SubEventReqMsg();
                        msg.setEvent(Event.subscribe);
                        message = msg;
                    }
                }
                //取消订阅类型
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    UnsubEventReqMsg msg = new UnsubEventReqMsg();
                    msg.setEvent(Event.unsubscribe);
                    message = msg;
                }

                // 已关注扫描
                else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
                    ScanHasSubEventReqMsg msg = new ScanHasSubEventReqMsg();
                    msg.setEvent(Event.scan);
                    msg.setEventKey(requestMap.get(ScanHasSubEventReqMsg._EventKey));
                    msg.setTicket(requestMap.get(ScanHasSubEventReqMsg._Ticket));
                    message = msg;
                }
                // 上报地理位置
                else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
                    PositionEventReqMsg msg = new PositionEventReqMsg();
                    msg.setEvent(Event.LOCATION);
                    msg.setLatitude(requestMap.get(PositionEventReqMsg._Latitude));
                    msg.setLongitude(requestMap.get(PositionEventReqMsg._Longitude));
                    msg.setPrecision(requestMap.get(PositionEventReqMsg._Precision));
                    message = msg;
                }
                // 菜单点击类型
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    MenuEventReqMsg msg = new MenuEventReqMsg();
                    msg.setEvent(Event.CLICK);
                    msg.setEventKey(requestMap.get(MenuEventReqMsg._EventKey));
                    message = msg;
                }
            }

            if (message == null) {
                throw new RuntimeException("Can't find the exact message type!--");
            }
            message.setToUserName(toUserName);
            message.setFromUserName(fromUserName);
            message.setCreateTime(Long.parseLong(createTime));
            message.setMsgType(msgType);
            if (msgId != null) message.setMsgId(Long.parseLong(msgId));

        } catch (Exception e) {
            // 转换捕捉异常
            throw new ParseMessageException(ExceptionUtil.getStackTrace(e));
        }
        return message;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
