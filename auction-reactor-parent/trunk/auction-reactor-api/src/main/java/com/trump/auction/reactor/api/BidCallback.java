package com.trump.auction.reactor.api;

import com.trump.auction.reactor.api.model.BidHitResponse;
import com.trump.auction.reactor.api.model.BidResponse;

/**
 * 出价通知回调
 *
 * @author Owen
 * @since 2018/1/9
 */
public interface BidCallback {

    /**
     * 出价完成后回调
     * @param bidResult 出价结果
     */
    void onBidden(BidResponse bidResult);

    /**
     * 竞拍成功回调
     * @param bidHitResponse 竞拍成功响应
     */
    void onBidHit(BidHitResponse bidHitResponse);
}
