package com.trump.auction.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.trump.auction.order.domain.OrderAppraises;

public interface OrderAppraisesDao {


    int insertAppraise(OrderAppraises record);

    OrderAppraises selectByPrimaryKey(Integer id);

    int updateAppraise(OrderAppraises record);

	List<OrderAppraises> getAppraisesByUserId(@Param("userId")String userId);

	void orderAppraisesCheck(@Param("appraisesId")String appraisesId, @Param("isShow")String isShow,@Param("appraisesLevel")String appraisesLevel,@Param("appraisesPic")String appraisesPic);

	List<OrderAppraises> queryAppraisesByProductId(@Param("productId")String productId);

	OrderAppraises getNewestAppraises(@Param("userId")String userId, @Param("orderId")String orderId);

}