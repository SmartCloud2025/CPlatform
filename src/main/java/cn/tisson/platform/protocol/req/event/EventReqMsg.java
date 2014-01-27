package cn.tisson.platform.protocol.req.event;

import cn.tisson.platform.protocol.bean.Event;
import cn.tisson.platform.protocol.req.BaseReqMsg;
import cn.tisson.util.MessageUtil;

/**
 * User: Jasic
 * Date: 13-12-28
 * 事件消息
 */
public abstract class EventReqMsg extends BaseReqMsg {

    public static final String _Event = "Event";


    /**
     * 推送的事件类型
     */
    private Event Event;

    protected EventReqMsg() {
        super();
        super.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_EVENT);
    }

    public Event getEvent() {
        return Event;
    }

    public void setEvent(Event event) {
        this.Event = event;
    }

}
