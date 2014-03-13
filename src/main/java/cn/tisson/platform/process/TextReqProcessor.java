package cn.tisson.platform.process;

import cn.tisson.common.GlobalConstants;
import cn.tisson.common.LogicHelper;
import cn.tisson.dbmgr.model.CmdConfig;
import cn.tisson.dbmgr.model.FansGroup;
import cn.tisson.dbmgr.model.FansInfo;
import cn.tisson.dbmgr.model.ServiceInfo;
import cn.tisson.dbmgr.service.FansInfoService;
import cn.tisson.platform.protocol.req.TextReqMsg;
import cn.tisson.platform.protocol.resp.BaseRespMsg;
import cn.tisson.util.SpringContextUtil;
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

        ServiceInfo serviceInfo = super.SERVICE_INFO_MAP.get();

        if (serviceInfo == null) {
            logger.error("数据库不存在服务号[" + msg.getToUserName() + "],请先添加服务号码信息!");
            return null;
        }

        FansGroup fansGroup = LogicHelper.findFansGroup(serviceInfo, msg.getFromUserName());

        // 如果粉丝找不到分组则将其加入到默认分组
        if (fansGroup == null) {
            FansInfoService fansInfoService = SpringContextUtil.getBean(FansInfoService.class);
            fansGroup = LogicHelper.addIfNotExistFansGroup(serviceInfo);

            // 找到默认插入粉丝信息
            FansInfo fansInfo = fansInfoService.selectByWebChatID(msg.getFromUserName());
            if (fansInfo == null) {
                fansInfo = new FansInfo();
                fansInfo.setWebchatid(msg.getFromUserName());
            }
            fansInfo.setFansgroupid(fansGroup.getId());

            // 没有则插数,有则更新
            if (fansInfo.getId() == null) {
                fansInfoService.insertSelective(fansInfo);
            } else
                fansInfoService.updateByPrimaryKeySelective(fansInfo);
        }

        String cmdStr = msg.getContent();
        CmdConfig cmd = LogicHelper.findCmdConfig(fansGroup.getId(), msg.getToUserName(), cmdStr);

        if (cmd != null && cmd.getCtype() != null) {
            resp = LogicHelper.getCmdResp(cmd, msg);
        }

        // TODO 根据关键字回复
        if (resp == null)
            resp = LogicHelper.replyViaKeyWord(msg.getFromUserName(), msg.getToUserName(), cmdStr);

        // 返回默认消息
        if (resp == null) {
            cmd = LogicHelper.findCmdConfig(fansGroup.getId(), msg.getToUserName(), GlobalConstants.CMD_CONFIG_DEFAULT);
            Asserter.notNull(cmd,"数据库找不到相关命令配置记录");
            resp= LogicHelper.getCmdResp(cmd, msg);
        }

        return resp;

    }


}
