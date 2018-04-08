package com.trump.auction.reactor.mock;

import com.trump.auction.reactor.api.AuctionCallback;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MockAuctionCallback implements AuctionCallback {

    @Override
    public List<String> getAll() {
        return null;
    }
}
