package cn.tisson.platform.protocol.req;


import cn.tisson.util.MessageUtil;

/**
 * 地理位置消息
 */
public class LocationReqMsg extends BaseReqMsg {

    public static final String _Location_X = "Location_X";
    public static final String _Location_Y = "Location_Y";
    public static final String _Scale = "Scale";
    public static final String _Label = "Label";


    // 地理位置维度
    private String Location_X;
    // 地理位置经度
    private String Location_Y;
    // 地图缩放大小
    private String Scale;
    // 地理位置信息
    private String Label;

    public LocationReqMsg() {
        super();
        super.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_LOCATION);
    }

    public String getLocation_X() {
        return Location_X;
    }

    public void setLocation_X(String location_X) {
        Location_X = location_X;
    }

    public String getLocation_Y() {
        return Location_Y;
    }

    public void setLocation_Y(String location_Y) {
        Location_Y = location_Y;
    }

    public String getScale() {
        return Scale;
    }

    public void setScale(String scale) {
        Scale = scale;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }
}
