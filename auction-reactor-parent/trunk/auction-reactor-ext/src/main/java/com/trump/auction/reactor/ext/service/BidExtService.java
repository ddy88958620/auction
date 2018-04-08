package com.trump.auction.reactor.ext.service;

import com.trump.auction.reactor.api.model.BidResponse;
import com.trump.auction.reactor.api.model.AccountCode;

import java.util.List;
import java.util.Map;

/**
 * 出价扩展服务
 *
 * @author Owen
 * @since 2018/1/24
 */
public interface BidExtService {

    /**
     * 查询最近出价记录
     * @param auctionNo 竞拍编号
     * @param count 查询数量
     */
    List<BidResponse> getLatest(String auctionNo, int count);

    /**
     * 获取消费的币信息
     * @param auctionNo 竞拍编号
     * @param userId 用户编号
     * @return 不同币种对应的消费数量
     */
    Map<AccountCode, Integer> getCost(String auctionNo, String userId);
}
