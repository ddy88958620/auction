package com.trump.auction.order.dao;

import com.trump.auction.order.domain.Logistics;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface LogisticsDao {
    Logistics selectByPrimaryKey(@Param("orderId") String orderId);
    int insert(Logistics obj);
    int updateByPrimaryKeySelective(Logistics obj);
}
