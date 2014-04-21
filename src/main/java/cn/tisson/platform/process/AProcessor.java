package cn.tisson.platform.process;


import cn.tisson.common.GlobalCaches;
import cn.tisson.common.GlobalVariables;
import cn.tisson.common.LogicHelper;
import cn.tisson.dbmgr.model.FansGroup;
import cn.tisson.dbmgr.model.FansInfo;
import cn.tisson.dbmgr.model.ServiceInfo;
import cn.tisson.dbmgr.service.FansInfoService;
import cn.tisson.platform.protocol.req.BaseReqMsg;
import cn.tisson.platform.protocol.resp.BaseRespMsg;
import cn.tisson.util.SpringContextUtil;
import org.jasic.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * User: Jasic
 * Date: 13-12-28
 * <p/>
 * 事件处理器
 * 由于微信的请求与对应的响应并不一定要一致的，所以只返回基础的响应消息
 * （例如：TextReqMsg请求的响应并不确定，有可能是MusicRespMsg也有可能是NewsRespMsg）
 */
public abstract class AProcessor<E extends BaseReqMsg> {

    private final static Logger logger = LoggerFactory.getLogger(AProcessor.class);

    private static final Object LOCK = new Object();

    protected abstract BaseRespMsg doProcess(E msg);

    protected abstract Map<String, Object> getExcludeDuplicate();

    protected ThreadLocal<ServiceInfo> SERVICE_INFO_MAP;

    private Timer timer;

    public AProcessor() {

        timer = new Timer("微信消息排重");
        SERVICE_INFO_MAP = new ThreadLocal<ServiceInfo>();
    }

    /**
     * 做过滤工作
     *
     * @return
     */
    protected boolean beforeProcess(E msg) {

        /**
         * 第一步
         */
        boolean step1;
        String key = LogicHelper.getKey(msg);

        step1 = !getExcludeDuplicate().containsKey(key);
        if (step1 && key != null) getExcludeDuplicate().put(key, LOCK);

        /**
         * 第二步
         */
        boolean step2 = false;

        ServiceInfo serviceInfo = GlobalCaches.DB_CACHE_SERVICE_INFO.get(msg.getToUserName());
        if (serviceInfo != null) {
            SERVICE_INFO_MAP.set(serviceInfo);
            step2 = true;
        }

        return step1 && step2;
    }


    /**
     * 做收尾工作
     *
     * @return
     */
    protected void afterProcess(E msg) {
        String key = LogicHelper.getKey(msg);
        Map<String, Object> keys = getExcludeDuplicate();
        if (key != null) {
            TimerTask task = new RemoveTask(keys, key);
            //微信排重(5秒内不收到响应则再发2次,总共发3次)
            timer.schedule(task, 3 * GlobalVariables.WEB_CHAT_POST_TIME_OUT);
        }

    }

    /**
     * 供外部调用
     *
     * @param msg
     */
    public BaseRespMsg process(E msg) {
        BaseRespMsg respMsg = null;

        /**
         * 1. 正常处理的情况
         */
        if (beforeProcess(msg)) {
            respMsg = doProcess(msg);

            if (respMsg != null) {

                getExcludeDuplicate().put(LogicHelper.getKey(msg), respMsg);
            }

            afterProcess(msg);
        }

        /**
         * 2.被过滤处理的情况
         */
        else {
            long start = System.currentTimeMillis();
            long end = start;
            while (end - start < GlobalVariables.WEB_CHAT_POST_TIME_OUT) {
                String key = LogicHelper.getKey(msg);
                Object value = getExcludeDuplicate().get(key);
                if (value != LOCK && value instanceof BaseRespMsg) {
                    return (BaseRespMsg) value;
                }
                end = System.currentTimeMillis();
                TimeUtil.sleep(10l);
            }
        }

        return respMsg;
    }

    /**
     * 删除排重键
     */
    private class RemoveTask extends TimerTask {
        Object key;
        Map<String, Object> keys;

        private RemoveTask(Map<String, Object> keys, Object key) {
            this.key = key;
            this.keys = keys;
        }

        @Override
        public void run() {
            keys.remove(key);
        }
    }

    // ----------------------------------- 业务处理逻辑 ---------------------------------
    /**
     * 根据toUser与fromUser进行匹配
     *
     * @param toUser
     * @param fromUser
     * @return
     */
    public FansGroup matched(String fromUser, String toUser) {
        // -------------------匹配粉丝与服务号---------------------
        ServiceInfo serviceInfo = SERVICE_INFO_MAP.get();

        if (serviceInfo == null) {
            logger.error("数据库不存在服务号[" + toUser + "],请先添加服务号码信息!");
            return null;
        }

        FansGroup fansGroup = LogicHelper.findFansGroup(serviceInfo, fromUser);

        // 如果粉丝找不到分组则将其加入到默认分组
        if (fansGroup == null) {
            FansInfoService fansInfoService = SpringContextUtil.getBean(FansInfoService.class);
            fansGroup = LogicHelper.addIfNotExistFansGroup(serviceInfo);

            // 找到默认插入粉丝信息
            FansInfo fansInfo = fansInfoService.selectByWebChatID(fromUser);
            if (fansInfo == null) {
                fansInfo = new FansInfo();
                fansInfo.setWebchatid(fromUser);
            }
            fansInfo.setFansgroupid(fansGroup.getId());

            // 没有则插数,有则更新
            if (fansInfo.getId() == null) {
                fansInfoService.insertSelective(fansInfo);
            } else
                fansInfoService.updateByPrimaryKeySelective(fansInfo);
        }
        // -------------------匹配粉丝与服务号---------------------
        return fansGroup;
    }
}
