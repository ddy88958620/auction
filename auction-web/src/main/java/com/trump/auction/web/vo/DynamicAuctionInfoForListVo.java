package com.trump.auction.web.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
public class DynamicAuctionInfoForListVo {

    @Getter @Setter private Integer auctionId;

    @Getter @Setter private BigDecimal bidPrice;

    @Getter @Setter private long dynamicCountdown = 0;

}
