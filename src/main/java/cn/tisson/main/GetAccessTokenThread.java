package cn.tisson.main;

import cn.tisson.common.GlobalCaches;
import cn.tisson.common.GlobalVariables;
import cn.tisson.common.LogicHelper;
import cn.tisson.dbmgr.model.ServiceInfo;
import cn.tisson.framework.global.AppVariables;
import cn.tisson.framework.thread.BaseThread;
import cn.tisson.framework.utils.StringUtils;
import cn.tisson.platform.protocol.active.TokenRespond;
import cn.tisson.util.JsonUtils;
import org.jasic.util.ExceptionUtil;
import org.jasic.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 获取AccessToken
 */
public class GetAccessTokenThread extends BaseThread {

    private static final Logger logger = LoggerFactory.getLogger(GetAccessTokenThread.class);

    @Override
    public void run() {

        while (AppVariables.APP_RUNNING_FLAG) {
            try {
                Map<String, TokenRespond> map = new HashMap<String, TokenRespond>(GlobalCaches.ACCESS_TOKEN_CACHE);

                List<ServiceInfo> serviceInfos = new ArrayList<ServiceInfo>(GlobalCaches.DB_CACHE_SERVICE_INFO.values());

                for (ServiceInfo info : serviceInfos) {
                    String appid = info.getAppid();
                    String appsecret = info.getAppsecret();

                    if (StringUtils.hasEmpty(new String[]{appid, appsecret})) {
                        continue;
                    }

                    if (map.containsKey(info.getWebchatid())) {
                        TokenRespond respond = map.get(info.getWebchatid());
                        if (respond != null && (respond.getDate().getTime() - System.currentTimeMillis()) <= GlobalVariables.ACCESS_TOKEN_EXPIRE_TIME) {
                            continue;
                        }
                    }

                    String respStr = LogicHelper.getAccessToken(appid, appsecret);
                    TokenRespond respond = JsonUtils.parseToObject(respStr, TokenRespond.class);

                    if (respond == null || StringUtils.hasEmpty(new String[]{respond.getExpires_in(), respond.getAccess_token()})) {
                        continue;
                    }

                    respond.setDate(new Date());
                    map.put(info.getWebchatid(), respond);
                }

                GlobalCaches.ACCESS_TOKEN_CACHE = map;
                logger.info("具有主动推送能力的服务号数量:" + map.size());

            } catch (Exception e) {
                logger.error(ExceptionUtil.getExceptionStackTrace(e));
            }
            TimeUtil.sleep(GlobalVariables.ACCESS_TOKEN_INTERVAL_TIME);
        }

    }
}
