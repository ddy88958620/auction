package com.trump.auction.order.service;

import com.trump.auction.order.domain.Logistics;

import java.util.List;

public interface LogisticsService {
    Logistics selectByPrimaryKey(String orderId);
    int insert(Logistics obj);
    int updateByPrimaryKeySelective(Logistics obj);
}
