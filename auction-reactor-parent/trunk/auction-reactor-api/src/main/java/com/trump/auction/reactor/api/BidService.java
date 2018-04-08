package com.trump.auction.reactor.api;

import com.trump.auction.reactor.api.model.BidHitResponse;
import com.trump.auction.reactor.api.model.BidRequest;
import com.trump.auction.reactor.api.model.BidResponse;
import com.trump.auction.reactor.api.model.MultiBidRequest;

import java.util.concurrent.Future;

/**
 * 出价服务
 *
 * @author Owen
 * @since 2018/1/8
 */
public interface BidService {

    /**
     * 出价
     * <p>
     *     异步接口
     * </p>
     * @param bidRequest 出价请求
     */
    Future<BidResponse> bid(BidRequest bidRequest);


    /**
     * 出价
     * <p>
     *     同步接口
     * </p>
     * @param bidRequest 出价请求
     * @param sync 同步操作标识，表示是否在当前线程执行
     * @return 出价结果
     */
    BidResponse bid(BidRequest bidRequest, boolean sync);

    /**
     * 委托出价
     * @param bidRequest 出价请求
     */
    void bid(MultiBidRequest bidRequest);

    /**
     * 出价结果响应
     * @param bidResult 出价结果
     */
    void onBidden(BidResponse bidResult);

    /**
     * 拍中结果响应
     * @param bidHitResponse 拍中响应
     */
    void onBidHit(BidHitResponse bidHitResponse);
}
