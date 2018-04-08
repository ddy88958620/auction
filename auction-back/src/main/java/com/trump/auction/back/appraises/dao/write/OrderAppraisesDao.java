package com.trump.auction.back.appraises.dao.write;

import com.trump.auction.back.appraises.model.OrderAppraises;


public interface OrderAppraisesDao {

    /**
     *
     * @mbggenerated 2017-12-20
     */
    int insert(OrderAppraises record);

    /**
     *
     * @mbggenerated 2017-12-20
     */
    int insertSelective(OrderAppraises record);
    
    /**
     *
     * @mbggenerated 2017-12-20
     */
    int updateByPrimaryKeySelective(OrderAppraises record);

	void orderAppraisesCheck(String appraisesId, String isShow);

	Integer saveOrderAppraises(OrderAppraises orderAppraises);

}