package cn.tisson.main;

import cn.tisson.common.GlobalVariables;
import cn.tisson.framework.thread.BaseThread;

import cn.tisson.util.ExceptionUtil;
import org.jasic.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author Jasic
 * Date 13-12-21.
 * <p/>
 * 缓存刷新
 */
public class CacheRefresher extends BaseThread {

    private static Logger logger = LoggerFactory.getLogger(CacheRefresher.class);

    public CacheRefresher() {
        super.logHeader = "数据刷新";
        super.setName("数据刷新");
    }


    public void run() {

        while (true) {
            try {
                TimeUtil.sleep(GlobalVariables.DB_REFRESH_INTERVAL);

                // 刷新服务号
                GlobalVariables.REFRESH_HELPER.refresh();


            } catch (Exception e) {
                logger.error(logHeader + ExceptionUtil.getExceptionStackTrace(e));
            }
        }
    }
}
