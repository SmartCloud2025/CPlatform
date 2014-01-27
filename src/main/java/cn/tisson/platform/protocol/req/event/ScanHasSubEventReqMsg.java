package cn.tisson.platform.protocol.req.event;

/**
 * User: Jasic
 * Date: 13-12-28
 * 扫描带参数二维码事件（用户已关注）
 */
public class ScanHasSubEventReqMsg extends EventReqMsg {
    public static final String _Ticket = "Ticket";
    public static final String _EventKey = "EventKey";

    /**
     * 二维码的ticket，可用来换取二维码图片
     */
    private String Ticket;

    /**
     * 事件key值
     */
    private String EventKey;

    public ScanHasSubEventReqMsg() {
        super();
        super.setEvent(cn.tisson.platform.protocol.bean.Event.scan);
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }
}
