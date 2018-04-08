package com.trump.auction.reactor.ext.repository;

import com.trump.auction.reactor.api.model.BidResponse;
import com.trump.auction.reactor.common.repository.AbstractRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * {@link BidCostRepository} 默认实现
 *
 * @author Owen
 * @since 2018/1/24
 */
@Component
public class DefaultBidResponseRepository extends AbstractRedisRepository implements BidResponseRepository {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<BidResponse> getLatest(String auctionNo, int count) {
        Assert.notNull(auctionNo, "[auctionNo]");
        Assert.isTrue(count > 0, "[count]");

        List<String> results = redisTemplate.opsForList().range(key(auctionNo), 0, count - 1);
        return convertToBean(results, BidResponse.class);
    }

    @Override
    public void remainLatest(String auctionNo, int count) {
        Assert.notNull(auctionNo, "[auctionNo]");
        Assert.isTrue(count > 0, "[count]");

        redisTemplate.opsForList().trim(key(auctionNo), 0, count - 1);
    }

    private String key(String auctionNo) {
        return makeKey(auctionNo, "bid-result-queue");
    }
}
