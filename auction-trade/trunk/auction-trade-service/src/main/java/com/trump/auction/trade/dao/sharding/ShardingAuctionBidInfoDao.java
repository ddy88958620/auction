package com.trump.auction.trade.dao.sharding;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.trump.auction.trade.domain.AuctionBidInfo;

@Repository
public interface ShardingAuctionBidInfoDao {
	int deleteByPrimaryKey(Integer id);

	AuctionBidInfo selectByPrimaryKey(Integer id);

	/**
	 * 获取用户某期的出价信息
	 * 
	 * @param auctionId
	 * @param userId
	 * @return
	 */
	AuctionBidInfo getBidInfoByAuctionIdAndUserId(@Param("auctionId") Integer auctionId,
			@Param("userId") Integer userId);

	AuctionBidInfo findBidInfo(@Param("userId") Integer userId, @Param("status") Integer status,
			@Param("auctionNo") Integer auctionNo);

	/*int updStatus(@Param("id") Integer id, @Param("status") Integer status, @Param("usedCount") Integer usedCount,
			@Param("pCoin") Integer pCoin, @Param("zCoin") Integer zCoin);
*/
	int updStatusByTxnSeqNo(@Param("txnSeqNo") String txnSeqNo, @Param("status") int status,
			@Param("usedCount") Integer usedCount, @Param("pCoin") Integer pcoin, @Param("zCoin") Integer zcoin);

}