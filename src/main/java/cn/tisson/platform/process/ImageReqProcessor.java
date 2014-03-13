package cn.tisson.platform.process;


import cn.tisson.platform.protocol.req.ImageReqMsg;
import cn.tisson.platform.protocol.resp.BaseRespMsg;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: Jasic
 * Date: 13-12-28
 */
public class ImageReqProcessor extends AProcessor<ImageReqMsg> {
    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(ImageReqProcessor.class);

    /**
     * 每个处理器，有且只有全局的一个排重的容器
     */
    private static final Map<String,Object> EXCLUDE_DUPLICATE_MAP = new ConcurrentHashMap<String, Object>();

    @Override
    protected Map<String, Object> getExcludeDuplicate() {
        return EXCLUDE_DUPLICATE_MAP;
    }

    @Override
    protected BaseRespMsg doProcess(ImageReqMsg msg) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
