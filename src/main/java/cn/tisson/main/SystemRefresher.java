package cn.tisson.main;

import cn.tisson.common.GlobalVariables;
import cn.tisson.framework.thread.ConfigThread;

/**
 * Author Jasic
 * Date 13-12-21.
 */
public class SystemRefresher {


    private CacheRefresher cacheRefresher;

    private ConfigThread configThread;

    public void start() {
        cacheRefresher = new CacheRefresher();
        cacheRefresher.start();

        configThread = new ConfigThread(GlobalVariables.class);
        configThread.start();
    }

}
