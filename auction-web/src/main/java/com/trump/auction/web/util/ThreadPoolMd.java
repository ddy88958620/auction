package com.trump.auction.web.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/12/19.
 */
public class ThreadPoolMd {
    private static ThreadPoolMd instance;
    private ExecutorService executorService;

    private ThreadPoolMd() {
        executorService = Executors.newFixedThreadPool(5);
    }

    public static ThreadPoolMd getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolMd.class) {
                if (instance == null)
                    instance = new ThreadPoolMd();
            }
        }
        return instance;
    }

    public void run(Runnable r) {
        executorService.execute(r);
    }
}
