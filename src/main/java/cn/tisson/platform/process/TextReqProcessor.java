package cn.tisson.platform.process;

import cn.tisson.common.GlobalConstants;
import cn.tisson.common.LogicHelper;
import cn.tisson.dbmgr.model.CmdConfig;
import cn.tisson.dbmgr.model.FansGroup;
import cn.tisson.platform.protocol.req.TextReqMsg;
import cn.tisson.platform.protocol.resp.BaseRespMsg;
import org.jasic.util.Asserter;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: Jasic
 * Date: 13-12-28
 * 文本消息的处理器
 */
public class TextReqProcessor extends AProcessor<TextReqMsg> {
    /**
     * 每个处理器，有且只有全局的一个排重的容器
     */
    private static final Map<String, Object> EXCLUDE_DUPLICATE_MAP = new ConcurrentHashMap<String, Object>();
    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(AProcessor.class);

    @Override
    protected Map<String, Object> getExcludeDuplicate() {
        return EXCLUDE_DUPLICATE_MAP;
    }

    @Override
    protected BaseRespMsg doProcess(TextReqMsg msg) {

        BaseRespMsg resp = null;

        String serviceId = msg.getToUserName();
        String fanId = msg.getFromUserName();

        // -------------------匹配粉丝与服务号---------------------
        FansGroup fansGroup = matched(msg.getFromUserName(), msg.getToUserName());
        // -------------------匹配粉丝与服务号---------------------

        String cmdStr = msg.getContent();
        CmdConfig cmd = LogicHelper.findCmdConfig(fansGroup.getId(), serviceId, cmdStr);

        if (cmd != null && cmd.getCtype() != null) {
            resp = LogicHelper.getCmdResp(cmd, serviceId, fanId, msg.getContent());
        }

        // TODO 根据关键字回复
        if (resp == null)
            resp = LogicHelper.replyViaKeyWord(serviceId, fanId, cmdStr);

        // 返回默认消息
        if (resp == null) {
            cmd = LogicHelper.findCmdConfig(fansGroup.getId(), serviceId, GlobalConstants.CMD_CONFIG_DEFAULT);
            Asserter.notNull(cmd, "数据库找不到相关命令配置记录");
            resp = LogicHelper.getCmdResp(cmd, serviceId, fanId, msg.getContent());
        }

        return resp;
    }

}
