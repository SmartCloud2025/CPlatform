package cn.tisson.util;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 封装了采用HttpClient发送HTTP请求的方法
 */
public class HttpClientUtil {
    private HttpClientUtil() {
    }


    public static final String REQUEST_ERROR = "通信失败";

    /**
     * @param baseReqURL
     * @param dataMap       请求参数,key为parameter的name,value的toString则为parameter的value
     * @param encodeCharset
     * @return
     */
    public static String sendGetRequest(String baseReqURL, Map<String, String> dataMap, String encodeCharset) {

        StringBuffer dataStr = new StringBuffer();

        for (String key : dataMap.keySet()) {
            Object value = dataMap.get(key);
            dataStr.append(key + "=" + value.toString() + "&");
        }

        int lastIndex = dataStr.lastIndexOf("&");
        dataStr.delete(lastIndex, lastIndex + 1);

        baseReqURL = (baseReqURL + "?" + dataStr.toString());
//        try {
//            baseReqURL = URLEncoder.encode(baseReqURL, encodeCharset);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        LogUtil.getLogger(HttpClientUtil.class).info("ReqUrl : " + baseReqURL);
        return sendGetRequest(baseReqURL);
    }

    /**
     * 发送HTTP_GET请求
     *
     * @param reqURL 请求地址(含参数)
     * @return 远程主机响应正文
     */
    public static String sendGetRequest(String reqURL) {
        String respContent = REQUEST_ERROR; //响应内容
        HttpClient httpClient = new DefaultHttpClient(); //创建默认的httpClient实例
        //设置代理服务器
        //httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost("10.0.0.4", 8080));
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); //连接超时10s
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);         //读取超时20s
        HttpGet httpGet = new HttpGet(reqURL); //创建org.apache.http.client.methods.HttpGet
        try {
            HttpResponse response = httpClient.execute(httpGet); //执行GET请求
            HttpEntity entity = response.getEntity();            //获取响应实体
            if (null != entity) {
                //respCharset=EntityUtils.getContentCharSet(entity)也可以获取响应编码,但从4.1.3开始不建议使用这种方式
                Charset respCharset = ContentType.getOrDefault(entity).getCharset();
                respContent = EntityUtils.toString(entity, respCharset);
                //Consume response content
                EntityUtils.consume(entity);
            }
            System.out.println("-------------------------------------------------------------------------------------------");
            StringBuilder respHeaderDatas = new StringBuilder();
            for (Header header : response.getAllHeaders()) {
                respHeaderDatas.append(header.toString()).append("\r\n");
            }
            String respStatusLine = response.getStatusLine().toString(); //HTTP应答状态行信息
            String respHeaderMsg = respHeaderDatas.toString().trim();    //HTTP应答报文头信息
            String respBodyMsg = respContent;                            //HTTP应答报文体信息
            System.out.println("HTTP应答完整报文=[" + respStatusLine + "\r\n" + respHeaderMsg + "\r\n\r\n" + respBodyMsg + "]");
            System.out.println("-------------------------------------------------------------------------------------------");
        } catch (ConnectTimeoutException cte) {
            //Should catch ConnectTimeoutException, and don`t catch org.apache.http.conn.HttpHostConnectException
            LogUtil.getLogger(HttpClientUtil.class).error("请求通信[" + reqURL + "]时连接超时,堆栈轨迹如下", cte);
        } catch (SocketTimeoutException ste) {
            LogUtil.getLogger(HttpClientUtil.class).error("请求通信[" + reqURL + "]时读取超时,堆栈轨迹如下", ste);
        } catch (ClientProtocolException cpe) {
            //该异常通常是协议错误导致:比如构造HttpGet对象时传入协议不对(将'http'写成'htp')or响应内容不符合HTTP协议要求等
            LogUtil.getLogger(HttpClientUtil.class).error("请求通信[" + reqURL + "]时协议异常,堆栈轨迹如下", cpe);
        } catch (ParseException pe) {
            LogUtil.getLogger(HttpClientUtil.class).error("请求通信[" + reqURL + "]时解析异常,堆栈轨迹如下", pe);
        } catch (IOException ioe) {
            //该异常通常是网络原因引起的,如HTTP服务器未启动等
            LogUtil.getLogger(HttpClientUtil.class).error("请求通信[" + reqURL + "]时网络异常,堆栈轨迹如下", ioe);
        } catch (Exception e) {
            LogUtil.getLogger(HttpClientUtil.class).error("请求通信[" + reqURL + "]时偶遇异常,堆栈轨迹如下", e);
        } finally {
            //关闭连接,释放资源
            httpClient.getConnectionManager().shutdown();
        }
        return respContent;
    }

    /**
     * @param reqURL
     * @param dataMap       请求参数,key为parameter的name,value的toString则为parameter的value
     * @param encodeCharset
     * @return
     */
    public static String sendPostRequest(String reqURL, Map<String, String> dataMap, String encodeCharset) {
        StringBuffer dataStr = new StringBuffer();
        for (String key : dataMap.keySet()) {
            Object value = dataMap.get(key);
            dataStr.append(key + "=" + value.toString() + "&");
        }

        int lastIndex = dataStr.lastIndexOf("&");
        dataStr.delete(lastIndex, lastIndex + 1);
        return sendPostRequest(reqURL, dataStr.toString(), encodeCharset);
    }

    /**
     * 发送HTTP_POST请求
     *
     * @param reqURL        请求地址
     * @param reqData       请求参数,若有多个参数则应拼接为param11=value11&22=value22&33=value33的形式
     * @param encodeCharset 编码字符集,编码请求数据时用之,此参数为必填项(不能为""或null)
     * @return 远程主机响应正文
     */
    public static String sendPostRequest(String reqURL, String reqData, String encodeCharset) {
        String reseContent = REQUEST_ERROR;
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
        HttpPost httpPost = new HttpPost(reqURL);
        //由于下面使用的是new StringEntity(....),所以默认发出去的请求报文头中CONTENT_TYPE值为text/plain; charset=ISO-8859-1
        //这就有可能会导致服务端接收不到POST过去的参数,比如运行在Tomcat6.0.36中的Servlet,所以我们手工指定CONTENT_TYPE头消息
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=" + encodeCharset);
        try {
            httpPost.setEntity(new StringEntity(reqData == null ? "" : reqData, encodeCharset));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                reseContent = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
                EntityUtils.consume(entity);
            }
        } catch (ConnectTimeoutException cte) {
            LogUtil.getLogger(HttpClientUtil.class).error("请求通信[" + reqURL + "]时连接超时,堆栈轨迹如下", cte);
        } catch (SocketTimeoutException ste) {
            LogUtil.getLogger(HttpClientUtil.class).error("请求通信[" + reqURL + "]时读取超时,堆栈轨迹如下", ste);
        } catch (Exception e) {
            LogUtil.getLogger(HttpClientUtil.class).error("请求通信[" + reqURL + "]时偶遇异常,堆栈轨迹如下", e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return reseContent;
    }

    /**
     * 发送HTTP_POST_SSL请求
     *
     * @param reqURL        请求地址
     * @param params        请求参数
     * @param encodeCharset 编码字符集,编码请求数据时用之,当其为null时,则取HttpClient内部默认的ISO-8859-1编码请求参数
     * @return 远程主机响应正文
     */
    public static String sendPostSSLRequest(String reqURL, Map<String, String> params, String encodeCharset) {
        String responseContent = REQUEST_ERROR;
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
        //创建TrustManager()
        //用于解决javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        //创建HostnameVerifier
        //用于解决javax.net.ssl.SSLException: hostname in certificate didn't match: <123.125.97.66> != <123.125.97.241>
        X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
            @Override
            public void verify(String host, SSLSocket ssl) throws IOException {
            }

            @Override
            public void verify(String host, X509Certificate cert) throws SSLException {
            }

            @Override
            public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
            }

            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        };
        try {
            //TLS1.0与SSL3.0基本上没有太大的差别,可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
            SSLContext sslContext = SSLContext.getInstance(SSLSocketFactory.TLS);
            //使用TrustManager来初始化该上下文,TrustManager只是被SSL的Socket所使用
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            //创建SSLSocketFactory
            SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext, hostnameVerifier);
            //通过SchemeRegistry将SSLSocketFactory注册到HttpClient上
            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
            //创建HttpPost
            HttpPost httpPost = new HttpPost(reqURL);
            if (null != params) {
                List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset));
            }
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
                EntityUtils.consume(entity);
            }
        } catch (ConnectTimeoutException cte) {
            LogUtil.getLogger(HttpClientUtil.class).error("请求通信[" + reqURL + "]时连接超时,堆栈轨迹如下", cte);
        } catch (SocketTimeoutException ste) {
            LogUtil.getLogger(HttpClientUtil.class).error("请求通信[" + reqURL + "]时读取超时,堆栈轨迹如下", ste);
        } catch (Exception e) {
            LogUtil.getLogger(HttpClientUtil.class).error("请求通信[" + reqURL + "]时偶遇异常,堆栈轨迹如下", e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return responseContent;
    }
}
