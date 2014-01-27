package cn.tisson.platform.protocol.req.event;

/**
 * User: Jasic
 * Date: 13-12-28
 */
public class ScanNotSubEventReqMsg extends SubEventReqMsg {
    public static final String _Ticket = "Ticket";

    public final static String _EventKey_PRE = "qrscene_";

    /**
     * 二维码的ticket，可用来换取二维码图片
     */
    private String Ticket;

    /**
     * 事件key值
     */
    private String EventKey;

    public ScanNotSubEventReqMsg() {
        super();
        super.setEvent(cn.tisson.platform.protocol.bean.Event.subscribe);

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
