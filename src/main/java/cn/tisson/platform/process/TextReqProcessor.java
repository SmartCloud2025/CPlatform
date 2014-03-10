package cn.tisson.platform.process;

import cn.tisson.common.GlobalConstants;
import cn.tisson.common.LogicHelper;
import cn.tisson.dbmgr.mapper.FansInfoMapper;
import cn.tisson.dbmgr.model.CmdConfig;
import cn.tisson.dbmgr.model.FansGroup;
import cn.tisson.dbmgr.model.FansInfo;
import cn.tisson.dbmgr.model.ServiceInfo;
import cn.tisson.dbmgr.service.FansInfoService;
import cn.tisson.platform.protocol.req.TextReqMsg;
import cn.tisson.platform.protocol.resp.BaseRespMsg;
import cn.tisson.platform.protocol.resp.TextRespMsg;
import cn.tisson.util.SpringContextUtil;
import org.slf4j.Logger;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jasic
 * Date: 13-12-28
 * 文本消息的处理器
 */
public class TextReqProcessor extends AProcessor<TextReqMsg> {
    /**
     * 每个处理器，有且只有全局的一个排重的容器
     */
    private static final List<Object> EXCLUDE_DUPLICATE_LIST = new ArrayList<Object>();
    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(AProcessor.class);


    @Override
    protected List<Object> getExcludeDuplicate() {
        return EXCLUDE_DUPLICATE_LIST;
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
            FansInfo fansInfo = new FansInfo();
            fansInfo.setWebchatid(serviceInfo.getWebchatid());
            fansInfo.setFansgroupid(fansGroup.getId());
            fansInfoService.insertSelective(fansInfo);
        }

        String cmdStr = msg.getContent();

        CmdConfig cmd = LogicHelper.findCmdConfig(fansGroup.getId(), msg.getToUserName(), cmdStr);

        if (cmd == null || cmd.getCtype() == null) {
            TextRespMsg respMsg = new TextRespMsg();
            respMsg.setToUserName(msg.getFromUserName());
            respMsg.setFromUserName(msg.getToUserName());
            String respText = "测试时使用--没找到相关指令";

            respMsg.setContent(respText);
            respMsg.setCreateTime(System.currentTimeMillis() / 1000);

            return respMsg;
        }

        String cType = cmd.getCtype();

        // 需要与后台交互得出结果
        if (cType.equalsIgnoreCase(GlobalConstants.CMD_CONFIG_CTYPE_CT02)
                ) {

            // TODO 与后台交互回复

            TextRespMsg respMsg = new TextRespMsg();
            respMsg.setToUserName(msg.getFromUserName());
            respMsg.setFromUserName(msg.getToUserName());
            String respText = "测试时使用--后台交互结果";

            respMsg.setContent(respText);
            respMsg.setCreateTime(System.currentTimeMillis() / 1000);

            resp = respMsg;
        } else if (cType.equalsIgnoreCase(GlobalConstants.CMD_CONFIG_CTYPE_CT01)
                ) {
            String type = cmd.getType();
            Integer msgId = cmd.getMsgid();
            resp = LogicHelper.getConfigResp(msg.getFromUserName(), msg.getToUserName(), type, msgId);
        }


        // TODO 根据关键字回复
        if (resp == null)
            resp = LogicHelper.replyViaKeyWord(msg.getFromUserName(), msg.getToUserName(), cmdStr);

        if (resp == null) {
            TextRespMsg respMsg = new TextRespMsg();
            respMsg.setFromUserName(msg.getToUserName());
            respMsg.setToUserName(msg.getFromUserName());
            respMsg.setCreateTime(System.currentTimeMillis() / 1000);

            respMsg.setContent("测试提示：没找到相关指令");
            resp = respMsg;
        }

        return resp;

    }


}
