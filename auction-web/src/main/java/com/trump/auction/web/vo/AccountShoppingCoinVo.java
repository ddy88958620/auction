package com.trump.auction.web.vo;

import lombok.Getter;
import lombok.Setter;


public class AccountShoppingCoinVo {

    @Getter
    @Setter
    private String productName;
    @Getter
    @Setter
    private String productImage;
    @Getter
    @Setter
    private Integer daysRemain;//有效天数
    @Getter
    @Setter
    private String coin;
    @Getter
    @Setter
    private Integer productId;//商品id
    @Getter
    @Setter
    private String status;//交易状态(1:未使用；2、部分使用,3:已使用，4：已过期)

}
