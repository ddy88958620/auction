package com.trump.auction.back.appraises.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.appraises.model.OrderAppraisesRules;

import java.util.HashMap;
import java.util.List;

/**
 * @Author=hanliangliang
 * @Date=2018-03-07
 */
public interface OrderAppraisesRulesService {
	
    public Paging<OrderAppraisesRules> findPage(HashMap<String, Object> params);

	public Integer saveOrderAppraisesRules(OrderAppraisesRules rules);

	public Integer updateOrderAppraisesRules(OrderAppraisesRules rules);

	public OrderAppraisesRules selectById(Integer id);

	public Integer deleteAppraisesRules(String [] ids);

	public List<OrderAppraisesRules> findAll();

	public Boolean checkLevelExits(String  level);

	/**
	 * 根据评价规则对象参数查询评价规则对象
	 * @param orderAppraisesRules
	 * @return
	 */
	public OrderAppraisesRules findByParameter(OrderAppraisesRules orderAppraisesRules);

	public List<OrderAppraisesRules> findAllLevel();

	public OrderAppraisesRules findByParameterAll(OrderAppraisesRules orderAppraisesRules);

}
