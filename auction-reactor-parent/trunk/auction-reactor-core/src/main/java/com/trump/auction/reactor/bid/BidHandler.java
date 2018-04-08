package com.trump.auction.reactor.bid;

import com.trump.auction.reactor.api.model.BidRequest;
import com.trump.auction.reactor.api.model.BidResponse;

/**
 * 出价处理
 *
 * @author Owen
 * @since 2018/1/16
 */
public interface BidHandler {

    BidResponse handleRequest(BidRequest bidRequest);

    boolean support(BidRequest bidRequest);
}
