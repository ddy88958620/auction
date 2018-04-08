package com.trump.auction.reactor.util.lock;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.ZKPaths;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * zookeeper distributed lock factory
 *
 * @author Owen
 * @since 2018/1/17
 */
@Component
public class ZookeeperLockFactory implements InitializingBean {

    @Autowired
    private CuratorFramework zkClient;

    @Value("${cache.lock.max-idle-time:120}")
    private int maxIdleTime;

    private LoadingCache<String, DistributedLock> cache;

    /**
     * 用于出价的锁目录
     */
    public static final String BID_LOCK_PATH = "/apps/auction/reactor/lock/bid-lock";

    public DistributedLock getLock(String lockPath, String lock) {
        try {
            return cache.get(ZKPaths.makePath(lockPath, lock));
        } catch (ExecutionException e) {
            throw new RuntimeException("get lock cache error", e);
        }
    }

    /**
     * 出价锁
     */
    public DistributedLock getBidLock(String auctionNo) {
        return getLock(BID_LOCK_PATH, auctionNo);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.cache = CacheBuilder.newBuilder()
                .expireAfterAccess(maxIdleTime, TimeUnit.SECONDS)
                .recordStats()
                .build(new CacheLoader<String, DistributedLock>() {
                    @Override
                    public DistributedLock load(String key) throws Exception {
                        return new ZookeeperLock(zkClient, key);
                    }
                });
    }
}
