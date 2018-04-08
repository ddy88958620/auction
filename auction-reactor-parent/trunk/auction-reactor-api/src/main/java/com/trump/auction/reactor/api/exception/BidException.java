package com.trump.auction.reactor.api.exception;

import com.trump.auction.reactor.api.model.BidCode;

/**
 * 业务异常
 *
 * @author Owen
 * @since 2018/01/15
 */
public class BidException extends RuntimeException {

    public BidException(BidCode code) {
        super(code.name());
    }

    public BidException(BidCode code, Throwable cause) {
        super(code.name(), cause);
    }

    public final BidCode getCode() {
        return BidCode.valueOf(getMessage());
    }

    public static BidCode code(Throwable e) {
        if (support(e)) {
            return BidException.class.cast(e).getCode();
        }

        return BidCode.DEFAULT_ERROR;
    }

    public static boolean support(Throwable e) {
        return BidException.class.isInstance(e);
    }

    public static BidException auctionCompleted() {
        return new BidException(BidCode.AUCTION_COMPLETED);
    }

    public static BidException lastBidder() {
        return new BidException(BidCode.LAST_BIDDER);
    }

    public static BidException create(BidCode bidCode, Throwable cause) {
        return new BidException(bidCode, cause);
    }

    public static BidException defaultError() {
        return create(BidCode.DEFAULT_ERROR, null);
    }

    public static BidException defaultError(Throwable cause) {
        return create(BidCode.DEFAULT_ERROR, cause);
    }

    public static BidException bidderQueued() {
        return new BidException(BidCode.BIDDER_QUEUED);
    }

    public static BidException lastPriceExpired() {
        return new BidException(BidCode.LAST_PRICE_EXPIRED);
    }
}
