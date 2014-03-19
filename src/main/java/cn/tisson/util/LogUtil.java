package cn.tisson.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: Jasic
 * Date: 14-3-3
 */
public class LogUtil {

    private static final Map<Class, Logger> Log_Map;

    static {
        Log_Map = new ConcurrentHashMap<Class, Logger>();
    }

    public static Logger getLogger(Class<?> clazz) {
        Logger logger = Log_Map.get(clazz);
        if (logger == null) {
            logger = LoggerFactory.getLogger(clazz);
            Log_Map.put(clazz, logger);
        }
        return logger;
    }
}
