package cn.tisson.platform.protocol.req.event;

/**
 * User: Jasic
 * Date: 13-12-28
 * 上报地理位置事件
 */
public class PositionEventReqMsg extends EventReqMsg {

    public static final String _Latitude = "Latitude";
    public static final String _Longitude = "Longitude";
    public static final String _Precision = "Precision";

    //    地理位置纬度
    private String Latitude;
    //    地理位置经度
    private String Longitude;
    //    地理位置精度
    private String Precision;

    public PositionEventReqMsg() {
        super();
        super.setEvent(cn.tisson.platform.protocol.bean.Event.LOCATION);
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getPrecision() {
        return Precision;
    }

    public void setPrecision(String precision) {
        Precision = precision;
    }
}
