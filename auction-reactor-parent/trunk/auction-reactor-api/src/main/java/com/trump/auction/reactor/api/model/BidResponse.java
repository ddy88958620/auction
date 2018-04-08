package com.trump.auction.reactor.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 出价结果响应
 *
 * @author Owen
 * @since 2018/1/8
 */
@ToString
@NoArgsConstructor
public class BidResponse implements Serializable {

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
     * 无效出价标识
     */
    @Getter @Setter private boolean invalidBid;
    /**
     * 业务流水
     */
    @Getter @Setter private String bizNo;
    /**
     * 最后出价价格
     * <p>
     *     出价成功后返回
     * </p>
     */
    @Getter @Setter private BigDecimal lastPrice;
    /**
     * 出价周期信息
     * <p>
     *     委托多次出价时使用，格式为：总次数-当前第几次
     *     例：5-4，表示委托出价 5 次中的第 4 次出价信息
     * </p>
     */
    @Getter @Setter private String bidCycle;
    /**
     * 响应信息码
     */
    @Getter @Setter private BidCode respCode;
    /**
     * 本次出价消费所属币种
     */
    @Getter @Setter private AccountCode accountCode;

    /**
     * 是否出价失败
     */
    @JSONField(serialize = false)
    public boolean isFailed() {
        return !isSuccess();
    }

    @JSONField(serialize = false)
    public boolean isSuccess() {
        return BidCode.SUCCESS.equals(respCode);
    }
}
