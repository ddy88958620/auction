package com.trump.auction.reactor.ext.repository;

import com.trump.auction.reactor.api.model.AccountCode;
import com.trump.auction.reactor.api.model.Bidder;

import java.util.Map;

/**
 * 出价消费仓储
 * <p>
 *     提供出价消费相关的累计信息操作
 * </p>
 *
 * @author Owen
 * @since 2018/1/24
 */
public interface BidCostRepository {

    /**
     * 递增出价消费的币信息
     * @param auctionNo 竞拍编号
     * @param bidder 出价人
     * @param accountCode 币种
     */
    void increase(String auctionNo, Bidder bidder, AccountCode accountCode);

    /**
     * 获取消费的币信息
     * @param auctionNo 竞拍编号
     * @param bidder 出价人
     * @param accountCode 币种
     * @return 消费的币累数量
     */
    int get(String auctionNo, Bidder bidder, AccountCode accountCode);

    /**
     * 获取消费的币信息
     * @param auctionNo 竞拍编号
     * @param bidder 出价人
     * @return 不同币种对应的消费数量
     */
    Map<AccountCode, Integer> get(String auctionNo, Bidder bidder);
}
