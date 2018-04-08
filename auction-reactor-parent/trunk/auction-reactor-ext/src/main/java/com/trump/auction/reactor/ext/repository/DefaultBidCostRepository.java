package com.trump.auction.reactor.ext.repository;

import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import com.trump.auction.reactor.api.model.Bidder;
import com.trump.auction.reactor.common.repository.AbstractRedisRepository;
import com.trump.auction.reactor.api.model.AccountCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * {@link BidCostRepository} 实现类
 *
 * @author Owen
 * @since 2018/1/24
 */
@Component
public class DefaultBidCostRepository extends AbstractRedisRepository implements BidCostRepository {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void increase(String auctionNo, Bidder bidder, AccountCode accountCode) {
        redisTemplate.opsForHash().increment(key(auctionNo), field(bidder, accountCode), 1);
    }

    @Override
    public int get(String auctionNo, Bidder bidder, AccountCode accountCode) {
        Object result = redisTemplate.opsForHash().get(key(auctionNo), field(bidder, accountCode));
        return parseInt(result);
    }

    private int parseInt(Object result) {
        return parseInt((String) result);
    }

    private int parseInt(String result) {
        return StringUtils.isEmpty(result) ? 0 : Integer.valueOf(result);
    }

    @Override
    public Map<AccountCode, Integer> get(String auctionNo, Bidder bidder) {
        List<Object> cost = redisTemplate.opsForHash().multiGet(key(auctionNo), Collections2.transform(
                AccountCode.ALL_OF, (code) -> field(bidder, code)));

        if (CollectionUtils.isEmpty(cost)) {
            return Collections.EMPTY_MAP;
        }

        Map<AccountCode, Integer> result = Maps.newHashMap();

        int index = 0;
        for (AccountCode accountCode : AccountCode.ALL_OF) {
            result.put(accountCode, parseInt(cost.get(index)));
            index++;
        }

        return result;
    }

    protected String key(String auctionNo) {
        return makeKey(auctionNo, "bid-cost");
    }

    protected String field(Bidder bidder, AccountCode accountCode) {
        return bidder.getId() + ":" + accountCode.val();
    }

}
