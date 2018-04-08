package com.trump.auction.reactor.util.lock;

/**
 * Distributed lock support
 *
 * @author Owen
 * @since 2018/1/18
 */
public interface DistributedLock {

    /**
     * 占用锁
     */
    void acquire();

    /**
     * 释放锁
     */
    void release();
}
