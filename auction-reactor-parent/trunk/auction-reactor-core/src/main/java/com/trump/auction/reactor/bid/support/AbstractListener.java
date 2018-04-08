package com.trump.auction.reactor.bid.support;

/**
 * {@link BidListener} 基本实现
 *
 * @author Owen
 * @since 2018/1/11
 */
public abstract class AbstractListener<E extends BidEvent> implements BidListener<E> {

    public abstract boolean support(E event);

    protected abstract void onEvent0(E event);

    @Override
    public void onEvent(E event) {
        if (!support(event)) {
            return;
        }

        onEvent0(event);
    }
}
