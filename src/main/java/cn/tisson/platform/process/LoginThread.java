package cn.tisson.platform.process;

import cn.tisson.common.LogicHelper;
import cn.tisson.framework.thread.BaseThread;

import org.jasic.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Jasic
 * Date: 13-12-4
 */
public class LoginThread extends BaseThread {

    private static final Logger logger = LoggerFactory.getLogger(LoginThread.class);

    public LoginThread() {
        this(0);
    }

    public LoginThread(int threadId) {
        super(threadId);
        super.setName("接入微信");
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {

            if (LogicHelper.login()) {
                logger.info("登录成功");
                TimeUtil.sleep(7200);
            } else {
                logger.info("登录失败，重试...");
                TimeUtil.sleep(5000l);
            }
        }
    }
}
