package com.trump.auction.reactor.bid.rule;

import com.trump.auction.reactor.api.model.AuctionContext;

/**
 * 拍中规则
 * <p>
 *     表示满足拍中的前置条件
 * </p>
 *
 * @author Owen
 * @since 2018/1/10
 */
public interface BidHitRule {

    /**
     * 判断是否满足规则
     */
    boolean check(AuctionContext context);

}
