package cn.tisson.common;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 微信消息接口认证token摘要类
 * <p/>
 * 这个摘要类实现为单例，校验一个签名是否合法的例子如下
 * <pre>
 * WeixinMessageDigest wxDigest = WeixinMessageDigest.getInstance();
 * boolean bValid = wxDigest.validate(signature, timestamp, nonce);
 * </pre>
 *
 * @author Jasic
 */
public final class WebChatDigest {

    /**
     * 单例持有类
     *
     * @author Jasic
     */
    private static class SingletonHolder {
        static final WebChatDigest INSTANCE = new WebChatDigest();
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static WebChatDigest getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private MessageDigest digest;

    private WebChatDigest() {
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (Exception e) {
            throw new InternalError("init MessageDigest error:" + e.getMessage());
        }
    }


    /**
     * 将字节数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        StringBuilder sbDes = new StringBuilder();
        String tmp;
        for (int i = 0; i < b.length; i++) {
            tmp = (Integer.toHexString(b[i] & 0xFF));
            if (tmp.length() == 1) {
                sbDes.append("0");
            }
            sbDes.append(tmp);
        }
        return sbDes.toString();
    }

    private String encrypt(String strSrc) {
        String strDes;
        byte[] bt = strSrc.getBytes();
        strDes = byte2hex(digest.digest(bt));
        return strDes;
    }

    /**
     * 校验请求的签名是否合法
     * <p/>
     * 加密/校验流程：
     * 1. 将token、timestamp、nonce三个参数进行字典序排序
     * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
     * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public boolean validate(String signature, String timestamp, String nonce, String token) {
        //1. 将token、timestamp、nonce三个参数进行字典序排序
        String[] arrTmp = {token, timestamp, nonce};
        Arrays.sort(arrTmp);
        StringBuffer sb = new StringBuffer();
        //2.将三个参数字符串拼接成一个字符串进行sha1加密
        for (int i = 0; i < arrTmp.length; i++) {
            sb.append(arrTmp[i]);
        }
        String expectedSignature = encrypt(sb.toString());
//        String expectedSignature = org.apache.catalina.realm.RealmBase.Digest(sb.toString(), "SHA-1", "UTF-8");
        System.out.println(WebChatDigest.class + "------" + expectedSignature);

        //3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if (expectedSignature.equals(signature)) {
            return true;
        }
        return false;
    }


}
