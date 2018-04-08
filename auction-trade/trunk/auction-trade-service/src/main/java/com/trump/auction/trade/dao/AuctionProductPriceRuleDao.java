package com.trump.auction.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.trump.auction.trade.domain.AuctionProductPriceRule;

/**
 * 拍品价格规则
 */
@Repository
public interface AuctionProductPriceRuleDao {

	int insert(AuctionProductPriceRule record);

	int deleteByAucProInfoId(@Param("productInfoId") Integer productInfoId);

	List<AuctionProductPriceRule> queryRuleByAucProInfoId(@Param("productInfoId") Integer productInfoId);

}
