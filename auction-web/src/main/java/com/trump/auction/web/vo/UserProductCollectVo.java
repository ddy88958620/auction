package com.trump.auction.web.vo;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class UserProductCollectVo {

    @Getter @Setter private Integer productId;
    @Getter @Setter private Integer periodsId;
    @Getter @Setter private String productPic;
    @Getter @Setter private String productName;
    @Getter @Setter private BigDecimal currentPrice;
    @Getter @Setter private Integer status;
    
}
