package com.trump.auction.web.service;

import com.trump.auction.web.util.HandleResult;

public interface ProductCollectionService {

	HandleResult getUserProductCollections(Integer userId,Integer pageNum,Integer pageSize);

	HandleResult updateUserProductCollect(Integer userId,Integer auctionProdId,Integer auctionId,Integer type);

	HandleResult collectAuctionProduct(Integer userId,Integer auctionProdId, Integer periodsId,int count,Integer productId);

}
