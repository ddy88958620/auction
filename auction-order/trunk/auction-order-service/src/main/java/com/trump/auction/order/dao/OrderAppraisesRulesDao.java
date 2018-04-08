package com.trump.auction.order.dao;

import com.trump.auction.order.domain.OrderAppraisesRules;

import java.util.List;

public interface OrderAppraisesRulesDao {

	List<OrderAppraisesRules> findAll();

}