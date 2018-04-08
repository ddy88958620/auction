package com.trump.auction.reactor.api;

import java.util.List;

/**
 * 竞拍回调
 *
 * @deprecated 进行中的竞拍会被注册到 zookeeper ，不需再使用该接口。
 *             since 1.0.8-SNAPSHOT
 * @author Owen
 * @since 2018/1/22
 */
@Deprecated
public interface AuctionCallback {

    /**
     * 获取所有竞拍中的竞拍编号
     */
    List<String> getAll();
}
