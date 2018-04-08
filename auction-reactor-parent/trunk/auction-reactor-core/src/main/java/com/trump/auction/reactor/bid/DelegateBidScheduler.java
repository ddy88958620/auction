package com.trump.auction.reactor.bid;

import com.google.common.collect.ImmutableList;
import com.trump.auction.reactor.api.AuctionContextFactory;
import com.trump.auction.reactor.api.AuctionLifeCycle;
import com.trump.auction.reactor.api.exception.BidException;
import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.bid.support.BidEvent;
import com.trump.auction.reactor.bid.support.BidTimer;
import com.trump.auction.reactor.bid.support.DelegateBidEvent;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import static org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.*;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.shaded.com.google.common.collect.Sets;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 委托出价任务调度
 *
 * @author Owen
 * @since 2018/1/22
 */
@Slf4j
@Component
public class DelegateBidScheduler implements AuctionLifeCycle, InitializingBean {

    @Autowired
    private CuratorFramework zkClient;

    @Autowired
    private BidTimer<BidEvent> timer;

    @Autowired
    private AuctionContextFactory contextFactory;

    @Autowired
    private BidConfig bidConfig;

    private LeaderSelector leaderSelector;

    private PathChildrenCache auctionOnBidCache;

    private final Object mutex = new Object();

    /**
     * 已委托出价的竞拍
     */
    private Set<String> delegatedData = Sets.newConcurrentHashSet();

    /**
     * 委托出价任务 leader 选举对应的路径
     */
    public static final String DELEGATE_BID_LEADER_PATH = "/apps/auction/reactor/leader-selector/delegate-bid";

    /**
     * 进行中的竞拍对应的路径
     */
    public static final String AUCTION_ON_BID_PATH = "/apps/auction/reactor/config/auction-on-bid";

    @Override
    public void onStart(@NonNull String auctionNo) {
        try {
            zkClient.create()
                    .creatingParentContainersIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(ZKPaths.makePath(AUCTION_ON_BID_PATH, auctionNo));
        } catch (KeeperException.NodeExistsException e) {
            log.warn("[node] exists, auctionNo = {}", auctionNo);
        } catch (Exception e) {
            throw BidException.defaultError(e);
        }
    }

    @Override
    public void onComplete(@NonNull String auctionNo) {
        try {
            zkClient.delete()
                    .guaranteed()
                    .forPath(ZKPaths.makePath(AUCTION_ON_BID_PATH, auctionNo));
        } catch (KeeperException.NoNodeException e) {
            log.warn("[node] has already been deleted, auctionNo = {}", auctionNo);
        } catch (Throwable e) {
            log.warn("[node] delete error, auctionNo = {}", auctionNo, e);
        }
    }

    private boolean addDelegatedData(String auctionNo) {
        return delegatedData.add(auctionNo);
    }

    private boolean clearDelegatedData(String auctionNo) {
        return delegatedData.remove(auctionNo);
    }

    /**
     * 获取所有进行中的竞拍
     */
    private List<String> allOfOnBid() throws Exception {
        try {
            return zkClient.getChildren().forPath(AUCTION_ON_BID_PATH);
        } catch (KeeperException.NoNodeException e) {
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            log.warn("[node] get children cause an error", e);
            throw e;
        }
    }

    /**
     * 触发委托出价
     */
    private void startDelegateBid(String auctionNo) {
        log.debug("[start delegate bid], auctionNo = {}", auctionNo);

        if (!addDelegatedData(auctionNo)) {
            return;
        }

        try {
            AuctionContext context = contextFactory.create(auctionNo);

            if (invalidAuction(context)) {
                log.warn("[node] auction is invalid, auctionNo = {}", auctionNo);
                clearDelegatedData(auctionNo);
                return;
            }

            timer.add(DelegateBidEvent.create(context), bidConfig.nextDelegateBidDelayTime(context.getBidCountDown()));
        } catch (Throwable e) {
            log.error("[start delegate bid] cause an error", e);
            clearDelegatedData(auctionNo);
        }
    }

    private void removeDelegateBid(String auctionNo) {
        clearDelegatedData(auctionNo);
        timer.remove(DelegateBidEvent.create(auctionNo));
    }

    private boolean invalidAuction(AuctionContext context) {
        return context == null || context.isComplete();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.leaderSelector = new LeaderSelector(zkClient, DELEGATE_BID_LEADER_PATH, leaderSelectorListener());
        this.auctionOnBidCache = new PathChildrenCache(this.zkClient, AUCTION_ON_BID_PATH, false);
        this.auctionOnBidCache.getListenable().addListener(
                new AuctionOnBidListener(ImmutableList.of(Type.CHILD_ADDED, Type.CHILD_REMOVED)));

        auctionOnBidCache.start();
        leaderSelector.autoRequeue();
        leaderSelector.start();
    }

    private LeaderSelectorListenerAdapter leaderSelectorListener() {
        return new LeaderSelectorListenerAdapter() {

            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                try {
                    takeLeaderShip0();
                } catch (Throwable e) {
                    log.error("[leader] selector", e);
                    throw e;
                }
                return;
            }

            private void takeLeaderShip0() throws Exception {
                List<String> auctionNos = allOfOnBid();
                auctionNos.stream().forEach((auctionNo) -> startDelegateBid(auctionNo));

                while (leaderSelector.hasLeadership()) {
                    synchronized (mutex) {
                        log.debug("[leader] wait for notify");

                        try {
                            mutex.wait();
                        } catch (InterruptedException e) {
                            log.warn("[leader] interrupted");
                        }
                    }
                }
            }
        };
    }

    private class AuctionOnBidListener implements PathChildrenCacheListener {

        private List<Type> requiredEventTypes;

        public AuctionOnBidListener(List<Type> requiredEventTypes) {
            this.requiredEventTypes = requiredEventTypes;
        }

        @Override
        public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
            log.debug("[zk event] event = {}", event);

            if (!leaderSelector.hasLeadership()) {
                synchronized (mutex) {
                    mutex.notify();
                }
            }

            if (!isRequiredEvent(event)) {
                return;
            }

            final String auctionNo = ZKPaths.getNodeFromPath(event.getData().getPath());

            if (Type.CHILD_REMOVED.equals(event.getType())) {
                log.info("[zk watch] remove delegate bid, auctionNo = {}", auctionNo);
                removeDelegateBid(auctionNo);
                return;
            }

            if (!leaderSelector.hasLeadership()) {
                return;
            }

            if (Type.CHILD_ADDED.equals(event.getType())) {
                log.info("[zk watch] start delegate bid, event = {}", event);
                startDelegateBid(auctionNo);
            }
        }

        private boolean isRequiredEvent(PathChildrenCacheEvent event) {
            return this.requiredEventTypes.contains(event.getType());
        }
    }
}
