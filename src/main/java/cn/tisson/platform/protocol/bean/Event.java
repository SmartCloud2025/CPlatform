package cn.tisson.platform.protocol.bean;

/**
 * User: Jasic
 * Date: 13-12-28
 * Event表示事件类型，包括订阅、取消订阅和自定义菜单点击事件。也就是说，无论用户是关注了公众帐号、取消对公众帐号的关注，
 * 还是在使用公众帐号的菜单，微信服务器都会发送一条MsgType=event的消息给我们，而至于具体这条消息表示关注、取消关注，还是菜单的点击事件，就需要通过Event的值来判断了
 */

public enum Event {
    /*
        之所有有大有小请参照
        http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E4%BA%8B%E4%BB%B6%E6%8E%A8%E9%80%81
     */
    subscribe("订阅"),
    unsubscribe("取消订阅"),
    CLICK("菜单点击"),
    scan("已关注扫描"),
    LOCATION("上报地理位置");

    private String describe;

    Event(String describe) {
        this.describe = describe;
    }
}
