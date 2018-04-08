package com.trump.auction.reactor.bid;

import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.api.model.BidCode;
import com.trump.auction.reactor.api.model.BidRequest;
import com.trump.auction.reactor.api.model.BidResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 出价响应
 *
 * @author Owen
 * @since 2018/1/16
 */
@Component
public class BidResponses {

    @Autowired
    private BeanMapper beanMapper;

    public BidResponse success(BidRequest bidRequest, AuctionContext context) {
        return create0(BidCode.SUCCESS, bidRequest, context);
    }

    public BidResponse failed(BidCode bidCode, BidRequest bidRequest) {
        return create0(bidCode, bidRequest, null);
    }

    private BidResponse create0(BidCode bidCode, BidRequest bidRequest, AuctionContext context) {
        if (BidCode.SUCCESS.equals(bidCode)) {
            Assert.notNull(context, "[context]");
        }

        BidResponse result = beanMapper.map(bidRequest, BidResponse.class);

        if (context != null) {
            result.setLastPrice(context.getLastPrice());
        }

        result.setRespCode(bidCode);
        return result;
    }
}
