package cn.tisson.platform.process;

import cn.tisson.platform.protocol.req.LinkReqMsg;
import cn.tisson.platform.protocol.resp.BaseRespMsg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: Jasic
 * Date: 13-12-28
 */
public class LinkReqProcessor extends AProcessor<LinkReqMsg> {
    /**
     * 每个处理器，有且只有全局的一个排重的容器
     */
    private static final Map<String,Object> EXCLUDE_DUPLICATE_MAP = new ConcurrentHashMap<String, Object>();

    @Override
    protected Map<String, Object> getExcludeDuplicate() {
        return EXCLUDE_DUPLICATE_MAP;
    }
    @Override
    protected BaseRespMsg doProcess(LinkReqMsg msg) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
