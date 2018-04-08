package com.trump.auction.back.order.dao.read;

import com.trump.auction.back.order.model.PaymentInfo;

public interface PaymentInfoDao {
    /**
     *
     * @mbggenerated 2017-12-20
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-12-20
     */
    int insert(PaymentInfo record);

    /**
     *
     * @mbggenerated 2017-12-20
     */
    int insertSelective(PaymentInfo record);

    /**
     *
     * @mbggenerated 2017-12-20
     */
    PaymentInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-12-20
     */
    int updateByPrimaryKeySelective(PaymentInfo record);

    /**
     *
     * @mbggenerated 2017-12-20
     */
    int updateByPrimaryKey(PaymentInfo record);
}