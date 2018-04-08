package com.trump.auction.trade.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * 竞拍VO
 * @author Administrator
 * @date 2018/1/6
 */
@Data
public class AuctionOrderForListVo implements Serializable {

    private static final long serialVersionUID = 5888308302715664749L;

    private Integer auctionId;

    /**
     * 出价金额
     */
    private BigDecimal bidPrice;

    /**
     * 倒计时,毫秒数
     */
    private long dynamicCountdown;

}
