package com.trump.auction.reactor.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 拍中结果响应
 *
 * @author Owen
 * @since 2018/1/8
 */
@ToString
@NoArgsConstructor
public class BidHitResponse implements Serializable {

    /**
     * 竞拍编号
     */
    @Getter @Setter private String auctionNo;
    /**
     * 用户
     */
    @Getter @Setter private Bidder lastBidder;
    /**
     * 当前拍卖价
     */
    @Getter @Setter private BigDecimal lastPrice;

}
