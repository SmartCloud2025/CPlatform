package cn.tisson.util;

import org.springframework.http.HttpHeaders;

/**
 * User: Jasic
 * Date: 14-1-3
 */
public class HttpValidateUtil {

    /**
     * 请求格式 json
     */
    public static final String HTTP_HEADER_CONTENT_TYPE_JSON = "application/json";
    public static final String HTTP_HEADER_ACCEPT_JSON = "application/json";

    /**
     * 请求格式xml
     */
    public static final String HTTP_HEADER_CONTENT_TYPE_XML = "text/xml";
    public static final String HTTP_HEADER_ACCEPT_XML = "text/xml";


    /**
     * 验证是否为json请求
     *
     * @param headers
     * @return
     */
    public static boolean valiateJsonHeader(HttpHeaders headers) {

        // TODO 判断头

//        String contentType = headers.getFirst("Content-Type");
//        String accept = headers.getFirst("Accept"); // n响应的信息的格式为 json，也可以设定xml格式的
//
//        return HTTP_HEADER_ACCEPT_JSON.equalsIgnoreCase(accept) && HTTP_HEADER_CONTENT_TYPE_JSON.equalsIgnoreCase(contentType);

        return true;
    }

    /**
     * 验证是否为xml请求
     *
     * @param headers
     * @return
     */
    public static boolean valiateXmlHeader(HttpHeaders headers) {
        // TODO 判断头

//        String contentType = headers.getFirst("Content -Type");
//        String accept = headers.getFirst("Accept");
//
//        return HTTP_HEADER_ACCEPT_XML.equalsIgnoreCase(accept) && HTTP_HEADER_CONTENT_TYPE_XML.equalsIgnoreCase(contentType);
        return true;
    }

}
