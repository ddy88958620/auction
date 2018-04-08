package com.trump.auction.back.appraises.dao.write;

import com.trump.auction.back.appraises.model.OrderAppraises;
import com.trump.auction.back.appraises.model.OrderAppraisesRules;


public interface OrderAppraisesRulesDao {

    /**
     *
     * @mbggenerated 2017-12-20
     */
    int insert(OrderAppraisesRules record);

    /**
     *
     * @mbggenerated 2017-12-20
     */
    int insertSelective(OrderAppraisesRules record);
    
    /**
     *
     * @mbggenerated 2017-12-20
     */
    int updateOrderAppraisesRules(OrderAppraisesRules record);


    int deleteAppraisesRules(String [] ids);

}