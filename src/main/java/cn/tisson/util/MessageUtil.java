package cn.tisson.util;


import cn.tisson.platform.protocol.bean.*;
import cn.tisson.platform.protocol.req.TextReqMsg;
import cn.tisson.platform.protocol.req.event.ScanNotSubEventReqMsg;
import cn.tisson.platform.protocol.resp.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jasic.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class MessageUtil {

    /**
     * 返回消息类型：文本
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 返回消息类型：图片
     */
    public static final String RESP_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 返回消息类型：语音
     */
    public static final String RESP_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 返回消息类型：音乐
     */
    public static final String RESP_MESSAGE_TYPE_VIDEO = "video";

    /**
     * 返回消息类型：音乐
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * 返回消息类型：图文
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";


    /**
     * 请求消息类型：文本
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：图片
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 请求消息类型：链接
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * 请求消息类型：地理位置
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * 请求消息类型：音频
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 请求消息类型：视频
     */
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

    /**
     * 请求消息类型：推送
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * 事件类型：subscribe(订阅)
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型：unsubscribe(取消订阅)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 已关注扫描
     */
    public static final String EVENT_TYPE_SCAN = "scan";

    /**
     * 上报地理位置事件
     */
    public static final String EVENT_TYPE_LOCATION = "LOCATION";

    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";

    /**
     * 解析微信发来的请求（XML）
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中  
        Map<String, String> map = new HashMap<String, String>();

        // 从request中取得输入流  
        InputStream inputStream = request.getInputStream();
        // 读取输入流  
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素  
        Element root = document.getRootElement();
        // 得到根元素的所有子节点  
        List<Element> elementList = root.elements();

        // 遍历所有子节点  
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // 释放资源  
        inputStream.close();
        inputStream = null;

        return map;
    }

    /**
     * 解析微信发来的请求（XML）
     *
     * @param requestBody
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(String requestBody) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        SAXReader reader = new SAXReader();
        System.out.println(requestBody);

        Document document = reader.read(new StringReader(requestBody));
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        return map;
    }

    /**
     * 文本消息对象转换成xml
     *
     * @param message 文本消息对象
     * @return xml
     */
    public static String textMessageToXml(TextRespMsg message) {
        xstream.alias("xml", message.getClass());
        return xstream.toXML(message);
    }

    /**
     * 文本消息对象转换成xml
     *
     * @param message 文本消息对象
     * @return xml
     */
    @Deprecated
    public static String textMessageToXml(TextReqMsg message) {
        xstream.alias("xml", message.getClass());
        return xstream.toXML(message);
    }

    /**
     * 回复图片消息
     *
     * @param message
     * @return
     */
    public static String imageMessageToXml(ImageRespMsg message) {
        xstream.alias("xml", message.getClass());
        xstream.alias("Image", new Image().getClass());
        return xstream.toXML(message);
    }

    public static String voiceMessageToXml(VoiceRespMsg message) {
        xstream.alias("xml", message.getClass());
        xstream.alias("Voice", Voice.class);

        return xstream.toXML(message);
    }

    /**
     * 回复视频消息
     *
     * @param message
     * @return
     */
    public static String videoMessageToXml(VideoRespMsg message) {
        xstream.alias("xml", message.getClass());
        xstream.alias("Video", Video.class);
        return xstream.toXML(message);
    }

    /**
     * 音乐消息对象转换成xml
     *
     * @param message 音乐消息对象
     * @return xml
     */
    public static String musicMessageToXml(MusicRespMsg message) {
        xstream.alias("xml", message.getClass());
        xstream.alias("Music", Music.class);
        return xstream.toXML(message);
    }

    /**
     * 图文消息对象转换成xml
     *
     * @param message 图文消息对象
     * @return xml
     */
    public static String newsMessageToXml(NewsRespMsg message) {
        xstream.alias("xml", message.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(message);
    }

    /**
     * 图文消息对象转换成xml
     *
     * @param message 图文消息对象
     * @return xml
     */
    public static String scanEventMessageToXml(ScanNotSubEventReqMsg message) {
        xstream.alias("xml", message.getClass());
        xstream.alias("Event", Event.class);
        return xstream.toXML(message);
    }

    /**
     * 扩展xstream，使其支持CDATA块
     *
     * @date 2013-05-19
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记  
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });


    @SuppressWarnings("unchecked")
    public static String getRequestBody(HttpServletRequest request) throws IOException {
        int len = request.getContentLength();
        System.out.println("数据流长度:" + len);

        //获取HTTP请求的输入流
        InputStream is = request.getInputStream();

        //已HTTP请求输入流建立一个BufferedReader对象
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        //读取HTTP请求内容
        String buffer = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (buffer != null) {
            //在页面中显示读取到的请求参数
            sb.append(buffer + "\n");
            buffer = br.readLine();
        }
        return sb.toString().trim();
    }


    /**
     * asdasdfasdfasdfasdfasdffa<xml>
     * <ToUserName><![CDATA[LemonTree]]></ToUserName>
     * <FromUserName><![CDATA[Fan_Jasic]]></FromUserName>
     * <CreateTime><![CDATA[1388466337]]></CreateTime>
     * <MsgType><![CDATA[text]]></MsgType>
     * <MsgId><![CDATA[1]]></MsgId>
     * <Content><![CDATA[Test Content测试内容]]></Content>
     * </xml>asdfasfsadfsafasdfas
     *
     * @param body 截取正确的xml内容
     * @return
     */
    public static String cutXml(String body) {
        return StringUtil.getMatch(body, "<xml[\\s\\S]*</xml>");
    }
}
