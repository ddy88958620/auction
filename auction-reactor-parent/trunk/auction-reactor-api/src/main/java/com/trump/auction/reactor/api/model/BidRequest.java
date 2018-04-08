package com.trump.auction.reactor.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 出价请求
 *
 * @author Owen
 * @since 2017/12/29
 */
@ToString
@EqualsAndHashCode(of = {"auctionNo", "bidder", "bizNo"})
public class BidRequest implements Serializable {

    /**
     * 竞拍编号
     */
    @Getter @Setter private String auctionNo;
    /**
     * 竞拍用户
     */
    @Getter @Setter private Bidder bidder;
    /**
     * 出价类型
     */
    @Getter @Setter private BidType bidType;
    /**
     * 业务流水
     */
    @Getter @Setter protected String bizNo;
    /**
     * 期望上一次出价价格
     * <p>
     *     自动出价时使用
     * </p>
     */
    @Getter @Setter private BigDecimal expectLastPrice;
    /**
     * 出价周期信息
     * <p>
     *     委托多次出价时使用，格式为：总次数-当前第几次
     *     例：5-4，表示委托出价 5 次中的第 4 次出价信息
     * </p>
     */
    @Getter @Setter private String bidCycle;
    /**
     * 交易账户类型
     */
    @Getter @Setter private AccountCode accountCode;
    
    @Getter @Setter private Integer orderRondom;

    /**
     * 无效出价标识
     */
    @JSONField(serialize = false)
    public boolean isInvalidBid() {
        if (BidType.AUTO.equals(bidType)) {
            return true;
        }

        return this.accountCode.isInvalidBid();
    }

}
