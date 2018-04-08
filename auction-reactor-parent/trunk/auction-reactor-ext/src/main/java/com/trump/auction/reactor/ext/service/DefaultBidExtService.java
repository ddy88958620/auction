package com.trump.auction.reactor.ext.service;

import com.trump.auction.reactor.api.model.BidResponse;
import com.trump.auction.reactor.api.model.Bidder;
import com.trump.auction.reactor.api.model.AccountCode;
import com.trump.auction.reactor.ext.repository.BidCostRepository;
import com.trump.auction.reactor.ext.repository.BidResponseRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 出价扩展服务
 *
 * @author Owen
 * @since 2018/1/24
 */
@Service
public class DefaultBidExtService implements BidExtService {

    @Autowired
    private BidCostRepository bidCostRepos;

    @Autowired
    private BidResponseRepository bidRespRepos;

    @Value("${auction.bid.remain-count:100}")
    private int bidRemainCount;

    private AtomicLong queryCount = new AtomicLong(0);

    public List<BidResponse> getLatest(@NonNull String auctionNo, int count) {
        if (queryCount.incrementAndGet() % 1000 == 0) {
            bidRespRepos.remainLatest(auctionNo, bidRemainCount);
        }

        return bidRespRepos.getLatest(auctionNo, count);
    }

    public Map<AccountCode, Integer> getCost(@NonNull String auctionNo, @NonNull String userId) {
        return bidCostRepos.get(auctionNo, Bidder.create(userId, userId));
    }
}
