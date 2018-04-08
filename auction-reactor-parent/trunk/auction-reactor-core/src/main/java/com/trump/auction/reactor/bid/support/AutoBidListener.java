package com.trump.auction.reactor.bid.support;

import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.reactor.api.model.BidType;
import com.trump.auction.reactor.api.model.BidRequest;
import com.trump.auction.reactor.api.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自动出价监听器
 *
 * @author Owen
 * @since 2018/1/11
 */
@Component
public class AutoBidListener<E extends BidEvent> extends AbstractListener<E> {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private BidService bidService;

    @Override
    public boolean support(E event) {
        return event instanceof AutoBidEvent;
    }

    @Override
    protected void onEvent0(E event) {
        BidRequest bidRequest = bidRequest(AutoBidEvent.class.cast(event));
        bidService.bid(bidRequest);
    }

    private BidRequest bidRequest(AutoBidEvent event) {
        BidRequest bidRequest = beanMapper.map(event, BidRequest.class);
        bidRequest.setBidType(BidType.AUTO);
        return bidRequest;
    }
}
