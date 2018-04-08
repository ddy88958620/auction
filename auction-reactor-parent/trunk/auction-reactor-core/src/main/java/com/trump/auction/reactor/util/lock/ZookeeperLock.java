package com.trump.auction.reactor.util.lock;

import com.trump.auction.reactor.api.exception.BidException;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;


/**
 * {@link DistributedLock} zookeeper 实现
 *
 * @author Owen
 * @since 2018/1/16
 */
@Slf4j
public class ZookeeperLock implements DistributedLock {

    private CuratorFramework client;

    private InterProcessMutex mutex;

    private String lockPath;

    public ZookeeperLock(CuratorFramework client, String lockPath) {
        this.client = client;
        this.lockPath = lockPath;
        this.mutex = new InterProcessMutex(this.client, this.lockPath);
    }

    @Override
    public void acquire() {
        try {
            mutex.acquire();
        } catch (Throwable e) {
            log.error("[lock] get lock cause an error.", e);
            throw BidException.defaultError(e);
        }
    }

    @Override
    public void release() {
        try {
            mutex.release();
        } catch (Throwable e) {
            log.error("[release lock:{}]", lockPath, e);
        }
    }
}
