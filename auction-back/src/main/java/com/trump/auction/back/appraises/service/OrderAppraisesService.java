package com.trump.auction.back.appraises.service;

import java.util.HashMap;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.appraises.model.OrderAppraises;

public interface OrderAppraisesService {
	
    public Paging<OrderAppraises> findPage(HashMap<String, Object> params);

	public Integer saveOrderAppraises(HashMap<String, Object> params);

	public OrderAppraises selectById(Integer id);

	public OrderAppraises findByOrderId(String orderId);

	/**
	 *  根据内容数和图片数生成评价级别
	 */
	// public String returnRemarkLevel(OrderAppraises appraises, String[] pic,String level);
}
