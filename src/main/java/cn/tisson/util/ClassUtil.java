package cn.tisson.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * User: Jasic
 * Date: 13-12-28
 */
public class ClassUtil {

    /**
     * 获取类其下所的类
     *
     * @param serverClass
     * @return
     */
    public static List<Class<?>> getClasses(Class<?> serverClass) {
        /**
         * 判断是否已经打成jar包
         */
        String protocol = Thread.currentThread().getContextClassLoader().getResource(serverClass.getName().replace('.', '/') + ".class").getProtocol();

        /**
         * 获取所有class
         */
        List<Class<?>> allClassList = null;
        if ("file".equals(protocol)) {
            allClassList = org.jasic.util.ClassUtil.getClasses(serverClass.getPackage().getName(), null, true);
        } else if ("jar".equals(protocol)) {
            try {
                String jarPackagePath = URLDecoder.decode(serverClass.getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8");

                allClassList = org.jasic.util.ClassUtil.getClasses(serverClass.getPackage().getName(), jarPackagePath, true);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        return allClassList;
    }
}
