package cn.tisson.platform.protocol.req.event;

/**
 * User: Jasic
 * Date: 13-12-28
 * subscribe(订阅)
 */
public class SubEventReqMsg extends EventReqMsg {

    public SubEventReqMsg() {
        super();
        super.setEvent(cn.tisson.platform.protocol.bean.Event.subscribe);
    }
}
