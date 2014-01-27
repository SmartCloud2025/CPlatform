package cn.tisson.platform.process;

import cn.tisson.platform.protocol.req.VoiceReqMsg;
import cn.tisson.platform.protocol.resp.BaseRespMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jasic
 * Date: 13-12-28
 */
public class VoiceReqProcessor extends AProcessor<VoiceReqMsg> {
    /**
     * 每个处理器，有且只有全局的一个排重的容器
     */
    private static final List<Object> EXCLUDE_DUPLICATE_LIST = new ArrayList<Object>();

    @Override
    protected List<Object> getExcludeDuplicate() {
        return EXCLUDE_DUPLICATE_LIST;
    }

    @Override
    protected BaseRespMsg doProcess(VoiceReqMsg msg) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
