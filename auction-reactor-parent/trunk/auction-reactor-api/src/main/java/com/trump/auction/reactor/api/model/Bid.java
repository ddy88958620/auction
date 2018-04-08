package com.trump.auction.reactor.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 出价
 *
 * @author Owen
 * @since 2018/1/9
 */
@ToString
public class Bid implements Serializable {

    /**
     * 竞拍编号
     */
    @Getter @Setter private String auctionNo;
    /**
     * 竞拍用户
     */
    @Getter @Setter private Bidder bidder;
    /**
     * 业务流水
     */
    @Getter @Setter private String bizNo;
    /**
     * 无效出价标识
     */
    @Getter @Setter private boolean invalidBid;
    /**
     * 上一次出价价格
     */
    @Getter @Setter private BigDecimal expectLastPrice;

}
