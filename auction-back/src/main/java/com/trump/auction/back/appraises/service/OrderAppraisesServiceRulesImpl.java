package com.trump.auction.back.appraises.service;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.back.appraises.dao.read.OrderAppraisesRulesReadDao;
import com.trump.auction.back.appraises.dao.write.OrderAppraisesRulesDao;
import com.trump.auction.back.appraises.model.OrderAppraisesRules;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class OrderAppraisesServiceRulesImpl implements OrderAppraisesRulesService {
	@Autowired
	private OrderAppraisesRulesReadDao orderAppraisesRulesReadDao;

	@Autowired
	private OrderAppraisesRulesDao orderAppraisesRulesDao;
	@Override
	public Paging<OrderAppraisesRules> findPage(HashMap<String, Object> params) {
		PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
				Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));

		Paging<OrderAppraisesRules>  list = PageUtils.page(orderAppraisesRulesReadDao.findAll(params));
		return list;
	}

	@Override
	public Integer saveOrderAppraisesRules(OrderAppraisesRules rules) {
		Integer count =orderAppraisesRulesDao.insert(rules);
		return count;
	}

	@Override
	public OrderAppraisesRules selectById(Integer id) {
		long startTime = System.currentTimeMillis();
		log.info("findChannelSource invoke,StartTime:{},params:{}",startTime,id);
		OrderAppraisesRules result = null;
		try {
			result = new OrderAppraisesRules();
			result= orderAppraisesRulesReadDao.selectById(id);
		} catch (NumberFormatException e) {
			log.error("findChannelSource error:", e);
		}
		long endTime = System.currentTimeMillis();
		log.info("findChannelSource end,duration:{}", endTime - startTime);
		return result;
	}

	@Override
	public Integer updateOrderAppraisesRules(OrderAppraisesRules rules) {
		long startTime = System.currentTimeMillis();
		log.info("updateOrderAppraisesRules invoke,StartTime:{},params:{}",startTime,rules);
		Integer result = 0;
		try {
			result= orderAppraisesRulesDao.updateOrderAppraisesRules(rules);
		} catch (NumberFormatException e) {
			log.error("updateOrderAppraisesRules error:", e);
		}
		long endTime = System.currentTimeMillis();
		log.info("updateOrderAppraisesRules end,duration:{}", endTime - startTime);
		return  result;
	}

	@Override
	public Integer deleteAppraisesRules(String [] ids) {
		long startTime = System.currentTimeMillis();
		log.info("deleteAppraisesRules invoke,StartTime:{},params:{}",startTime,ids);
		Integer result = 0;
		try {
			result= orderAppraisesRulesDao.deleteAppraisesRules(ids);
		} catch (NumberFormatException e) {
			log.error("deleteAppraisesRules error:", e);
		}
		long endTime = System.currentTimeMillis();
		log.info("deleteAppraisesRules end,duration:{}", endTime - startTime);
		return  result;
	}

	@Override
	public  List<OrderAppraisesRules> findAll() {
		long startTime = System.currentTimeMillis();
		log.info("findAll invoke,StartTime:{},params:{}",startTime);
		List<OrderAppraisesRules> result=null;
		try {
			 result= orderAppraisesRulesReadDao.findAll();
		} catch (NumberFormatException e) {
			log.error("findAll error:", e);
		}
		long endTime = System.currentTimeMillis();
		log.info("findAll end,duration:{}", endTime - startTime);
		return result;
	}

	@Override
	public Boolean checkLevelExits(String level) {
		long startTime = System.currentTimeMillis();
		log.info("findAll invoke,StartTime:{},params:{}",startTime);
		Boolean result =false;
		try {
			if(orderAppraisesRulesReadDao.checkLevelExits(level)!=null){
				result=true;
			}
		} catch (NumberFormatException e) {
			log.error("findAll error:", e);
		}
		long endTime = System.currentTimeMillis();
		log.info("findAll end,duration:{}", endTime - startTime);
		return result;
	}

	@Override
	public OrderAppraisesRules findByParameter(OrderAppraisesRules orderAppraisesRules) {
		long startTime = System.currentTimeMillis();
		log.info("findByParameter invoke,OrderAppraisesRules:{}",orderAppraisesRules);
		OrderAppraisesRules result = null;
		try {
			result = new OrderAppraisesRules();
			result= orderAppraisesRulesReadDao.findByParameter(orderAppraisesRules);
		} catch (NumberFormatException e) {
			log.error("findByParameter error:", e);
		}
		long endTime = System.currentTimeMillis();
		log.info("findByParameter end,duration:{}", endTime - startTime);
		return result;
	}

	@Override
	public List<OrderAppraisesRules> findAllLevel() {
		long startTime = System.currentTimeMillis();
		log.info("findAllLevel invoke,StartTime:{},params:{}",startTime);
		List<OrderAppraisesRules> result=null;
		try {
			result= orderAppraisesRulesReadDao.findAllLevel();
		} catch (NumberFormatException e) {
			log.error("findAllLevel error:", e);
		}
		long endTime = System.currentTimeMillis();
		log.info("findAllLevel end,duration:{}", endTime - startTime);
		return result;
	}

	@Override
	public OrderAppraisesRules findByParameterAll(OrderAppraisesRules orderAppraisesRules) {
		long startTime = System.currentTimeMillis();
		log.info("findByParameterAll invoke,OrderAppraisesRules:{}",orderAppraisesRules);
		OrderAppraisesRules result = null;
		try {
			result = new OrderAppraisesRules();
			result= orderAppraisesRulesReadDao.findByParameterAll(orderAppraisesRules);
		} catch (NumberFormatException e) {
			log.error("findByParameter error:", e);
		}
		long endTime = System.currentTimeMillis();
		log.info("findByParameter end,duration:{}", endTime - startTime);
		return result;
	}
}
