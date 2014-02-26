package cn.tisson.platform.process;


import cn.tisson.common.GlobalCaches;
import cn.tisson.common.LogicHelper;
import cn.tisson.dbmgr.model.*;
import cn.tisson.dbmgr.service.FansInfoService;
import cn.tisson.platform.protocol.bean.Article;
import cn.tisson.platform.protocol.bean.Event;
import cn.tisson.platform.protocol.req.event.EventReqMsg;
import cn.tisson.platform.protocol.req.event.ScanNotSubEventReqMsg;
import cn.tisson.platform.protocol.req.event.SubEventReqMsg;
import cn.tisson.platform.protocol.req.event.UnsubEventReqMsg;
import cn.tisson.platform.protocol.resp.BaseRespMsg;
import cn.tisson.platform.protocol.resp.NewsRespMsg;
import cn.tisson.platform.protocol.resp.TextRespMsg;
import cn.tisson.util.MessageUtil;
import cn.tisson.util.SpringContextUtil;
import org.jasic.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * User: Jasic
 * Date: 13-12-28
 * 事件处理
 */
public class EventReqProcessor extends AProcessor<EventReqMsg> {

    private static final Logger logger = LoggerFactory.getLogger(EventReqProcessor.class);

    /**
     * 每个处理器，有且只有全局的一个排重的容器
     */
    private static final List<Object> EXCLUDE_DUPLICATE_LIST = new ArrayList<Object>();


    @Override
    protected List<Object> getExcludeDuplicate() {
        return EXCLUDE_DUPLICATE_LIST;
    }

    @Override
    protected BaseRespMsg doProcess(EventReqMsg msg) {

        BaseRespMsg respMsg = null;

        String content = "这个响应不存在";
        Event event = msg.getEvent();


        switch (event) {
            // 订阅
            case subscribe: {

                SubEventReqMsg subMsg = (SubEventReqMsg) msg;
                if (msg instanceof ScanNotSubEventReqMsg) {
                    // TODO 可获取二维码的ticket，可用来换取二维码图片
                }

                //保存关注用户记录
                ServiceInfo serviceInfo = saveSubscribeUser(subMsg);

                // 处理关注事件
                if (serviceInfo != null) respMsg = handleSubEvent(subMsg, serviceInfo);
                break;
            }
            //取消订阅
            case unsubscribe: {

                // 取消关注不需返回响应
                doUnsubscribe((UnsubEventReqMsg) msg);
                break;
            }

            // 已关注扫描
            case scan: {

                break;
            }

            // 上报地理位置
            case LOCATION: {

                break;
            }

            // 点击菜单
            case CLICK: {

                break;
            }
        }

        return respMsg;
    }


    /**
     * 处理用户关注的行为
     *
     * @param subMsg
     */
    private ServiceInfo saveSubscribeUser(SubEventReqMsg subMsg) {
        FansInfoService fansInfoService = SpringContextUtil.getBean(FansInfoService.class);
        Map<String, ServiceInfo> serviceInfoMap = GlobalCaches.DB_CACHE_SERVICE_INFO;
        String fromUserName = subMsg.getFromUserName().trim();
        String toUserName = subMsg.getToUserName().trim();
        Long createTime = subMsg.getCreateTime();
        String ev = subMsg.getEvent().name().trim();

        ServiceInfo serviceInfo = serviceInfoMap.get(toUserName);

        if (serviceInfo == null) {
            return null;
        }

        FansGroup group = LogicHelper.addIfNotExistFansGroup(serviceInfo);
        if (group == null) {
            return null;
        }

        // 存在服务号码则保存关注用户
        if (serviceInfo != null) {
            FansInfo fan = new FansInfo();
            fan.setWebchatid(fromUserName);
            fan.setFansgroupid(group.getId());

            try {
                FansInfo temp = fansInfoService.selectByWebChatID(fan.getWebchatid());
                if (temp == null) {
                    fansInfoService.insertSelective(fan);
                    logger.info("保存新的关注用户[" + fan.getWebchatid() + "]成功");
                }
                return serviceInfo;
            } catch (Exception e) {
                logger.error("Save a focus fan fail! " + ExceptionUtil.getStackTrace(e));

                return null;
                // TODO 记录在失败文件中
            }
        } else {
            // TODO 服务号已不存在或者分店用户服务到期则不提供服务
            return null;
        }
    }


    /**
     * 处理关注事件
     *
     * @param msg
     * @return
     */
    private BaseRespMsg handleSubEvent(SubEventReqMsg msg, ServiceInfo info) {

        BaseRespMsg resp = null;
        List<SubcEventRespMsg> list = GlobalCaches.DB_CACHE_SUBC_EVENT_PUSH_MSG.get(info.getWebchatid());

        if (list != null && list.size() != 0) {
            /**
             * 随机一个
             */
            // TODO 更好的方式处理
            SubcEventRespMsg subcEventPushMsg = list.get(new Random().nextInt(list.size()));
            String type = subcEventPushMsg.getType().toLowerCase().trim();
            Integer msgId = subcEventPushMsg.getMsgid();

           resp = LogicHelper.getConfigResp(msg.getFromUserName(),msg.getToUserName(),type,msgId);
        }
        if (resp == null) {
            TextRespMsg respMsg = new TextRespMsg();
            respMsg.setFromUserName(msg.getToUserName());
            respMsg.setToUserName(msg.getFromUserName());
            respMsg.setCreateTime(System.currentTimeMillis() / 1000);

            respMsg.setContent("测试提示：服务号没有配置关注回复消息");
            resp = respMsg;
        }
        return resp;

    }


    /**
     * 处理用户取消关注的行为
     *
     * @param unsubMsg
     */
    private void doUnsubscribe(UnsubEventReqMsg unsubMsg) {

        String fromUser = unsubMsg.getFromUserName();
        FansInfoService fansInfoService = SpringContextUtil.getBean(FansInfoService.class);

        fansInfoService.deleteByWebChatID(fromUser);
        logger.info("删除取消关注用户[" + fromUser + "]成功～");
    }
}
