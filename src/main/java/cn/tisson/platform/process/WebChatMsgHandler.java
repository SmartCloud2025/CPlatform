package cn.tisson.platform.process;

import cn.tisson.framework.utils.ReflectionUtils;
import cn.tisson.platform.protocol.req.BaseReqMsg;
import cn.tisson.platform.protocol.resp.BaseRespMsg;
import cn.tisson.util.ClassUtil;
import com.sun.xml.internal.ws.handler.HandlerException;
import org.jasic.common.DefualtThreadFactory;
import org.jasic.util.Asserter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: Jasic
 * Date: 13-12-28
 * <p/>
 * 消息处理入口分发
 */
@Component("WebChatMsgHandler")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.DEFAULT)
public class WebChatMsgHandler<E extends BaseReqMsg> {

    private static final Logger logger = LoggerFactory.getLogger(WebChatMsgHandler.class);
    private final Map<String, AProcessor> processors;

    public WebChatMsgHandler() {
        this.processors = new ConcurrentHashMap<String, AProcessor>();
        try {
            this.init();
        } catch (Exception e) {
            throw new RuntimeException("初始化平台消息接口处理器失败");
        }
    }

    /**
     * 初始化处理器
     *
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void init() throws IllegalAccessException, InstantiationException {
        List<Class<?>> clzList = ClassUtil.getClasses(WebChatMsgHandler.class);
        for (Class<?> clz : clzList) {
            List<Class<?>> fathers = org.jasic.util.ClassUtil.getAllSuperclasses(clz);
            if (fathers.contains(AProcessor.class)) {
                Class<BaseReqMsg> cl = ReflectionUtils.getSuperClassGenricType(clz);
                processors.put(cl.getName(), (AProcessor) clz.newInstance());
            }
        }
        logger.info("All web chat processors[" + processors.size() + "]");
        for (String key : processors.keySet()) {
            logger.info(key + " : " + processors.get(key));
        }
    }


    /**
     * 上溯查找处理器
     *
     * @param clazz
     * @return
     */
    private AProcessor findUp(Class<?> clazz) {

        String clzName = clazz.getName();
        AProcessor processor = processors.get(clzName);

        while (processor == null) {
            clazz = clazz.getSuperclass();
            if (clazz == null) break;
            clzName = clazz.getName();
            processor = processors.get(clzName);
        }

        return processor;
    }

    /**
     * 消息处理，并根据处理的需要返回响应
     *
     * @param msg
     * @return
     */
    public BaseRespMsg handle(E msg) {

        Asserter.notNull(msg);
        String clzName = msg.getClass().getName();
        AProcessor processor = findUp(msg.getClass());

        if (processor == null) {
            throw new HandlerException("找不到与[" + clzName + "]相对应的消息处理器");
        }

        BaseRespMsg respMsg = processor.process(msg);
        return respMsg;
    }
}
