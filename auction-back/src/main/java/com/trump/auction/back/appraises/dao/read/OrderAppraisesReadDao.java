package com.trump.auction.back.appraises.dao.read;

import java.util.HashMap;
import java.util.List;

import com.trump.auction.back.appraises.model.OrderAppraises;


public interface OrderAppraisesReadDao {

    OrderAppraises selectById(Integer id);

	List<OrderAppraises> findAll(HashMap<String, Object> params);

    OrderAppraises findByOrderId(String orderId);
}