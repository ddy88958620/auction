package com.trump.auction.trade.dao.sharding;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.trump.auction.trade.domain.AuctionDetail;
import com.trump.auction.trade.vo.AuctionOrderVo;
import com.trump.auction.trade.vo.ParamVo;

/**
 * 竞拍订单信息Dao
 * 
 * @author Administrator
 */
@Repository
public interface ShardingAuctionDetailDao {

	/**
	 * 查询竞拍列表
	 * 
	 * @param paramVo
	 * @return
	 */
	List<AuctionOrderVo> find(ParamVo paramVo);

	List<AuctionDetail> findList(@Param("status") Integer status, @Param("auctionId") Integer auctionId,
			@Param("userType") Integer userTypes);

	AuctionOrderVo selectByAuctionId(ParamVo paramVo);

	AuctionDetail selectByUserId(@Param("userId") Integer userId, @Param("auctionId") Integer auctionId,
			@Param("subUserId") String subUserId);

	/*int updDetailStatus(@Param("id") Integer id, @Param("bidCount") Integer bidCount, @Param("zCoin") Integer zCoin,
			@Param("pCoin") Integer pCoin);*/

	int updDetailStatusByAuctionIdAndUserIdAndSubUserId(@Param("auctionId") Integer auctionId,
			@Param("userId") Integer userId,@Param("subUserId") Integer subUserId,@Param("bidCount") Integer bidCount, @Param("zCoin") Integer zCoin,
			@Param("pCoin") Integer pCoin);

	int updUserSuccess(@Param("userId") Integer userId, @Param("auctionId") Integer auctionId,
			@Param("status") Integer status, @Param("subUserId") String subUserId);

	int updUserfail(@Param("auctionId") Integer auctionId, @Param("status") Integer status,
			@Param("returnPercent") BigDecimal returnPercent);

	int findDetailBidCount(@Param("auctionId") Integer auctionId);
}