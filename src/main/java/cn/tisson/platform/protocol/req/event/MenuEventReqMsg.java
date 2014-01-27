package cn.tisson.platform.protocol.req.event;


/**
 * User: Jasic
 * Date: 13-12-28
 * 自定义菜单事件
 */
public class MenuEventReqMsg extends EventReqMsg {

    public static final String _EventKey = "EventKey";


    /**
     * 事件key值
     */
    private String EventKey;

    public MenuEventReqMsg() {
        super();
        super.setEvent(cn.tisson.platform.protocol.bean.Event.CLICK);
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
}
