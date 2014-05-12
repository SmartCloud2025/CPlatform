package cn.tisson.listener;


import cn.tisson.common.GlobalVariables;
import cn.tisson.common.RefreshHelper;
import cn.tisson.common.SystemInit;
import cn.tisson.framework.config.ConfigHandler;
import cn.tisson.framework.global.AppVariables;
import cn.tisson.main.ActivePushThread;
import cn.tisson.main.GetAccessTokenThread;
import cn.tisson.main.SystemRefresher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;

public class MainListener extends org.springframework.web.context.ContextLoaderListener {

    private static final Logger logger = LoggerFactory.getLogger(MainListener.class);

    public void contextDestroyed(ServletContextEvent event) {
        try {
            super.contextDestroyed(event);

            AppVariables.APP_RUNNING_FLAG = false;

            logger.info("[程序管理]: 程序关闭完成");

        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    public void contextInitialized(ServletContextEvent event) {

        try {
            if (!ConfigHandler.loadConfigWithoutDB(GlobalVariables.class)) {
                System.exit(-1);
            }

            logger.info("[配置管理]: 读取所有配置文件完成");

            if (!SystemInit.init()) {
                System.exit(-1);
                return;
            }

            super.contextInitialized(event);

            ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
            RefreshHelper refreshHelper = applicationContext.getBean(RefreshHelper.class);

            // 先刷新一次基础数据
            refreshHelper.refresh();

            GlobalVariables.REFRESH_HELPER = refreshHelper;

//            testInit();
            //刷新系统缓存
            SystemRefresher systemRefresher = new SystemRefresher();
            systemRefresher.start();


            // 消息推送服务接入
            GetAccessTokenThread getAccessTokenThread = new GetAccessTokenThread();
//            getAccessTokenThread.start();

            ActivePushThread activePushThread = new ActivePushThread();
//            activePushThread.start();


            AppVariables.APP_SQL_LOG = false;
            AppVariables.APP_SOCKET_CLIENT_LOG = false;
            AppVariables.APP_SOCKET_SERVER_LOG = false;

            logger.info("[缓存管理]: 初始化缓存成功");
        } catch (Exception e) {
            logger.info("Exception: ", e);
        }
    }

}
