package com.trump.auction.pals.service.impl;

import com.cf.common.utils.RequestUtils;
import com.trump.auction.pals.dao.ThirdPartyAskDao;
import com.trump.auction.pals.domain.ThirdPartyAsk;
import com.trump.auction.pals.service.ThirdPartyAskService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ThirdPartyAskServiceImpl implements ThirdPartyAskService {
	@Autowired
	private ThirdPartyAskDao ordersDao;

	@Override
	public int insert(ThirdPartyAsk orders) {
		if (StringUtils.isBlank(orders.getAddIp())) {
			orders.setAddIp(RequestUtils.getIpAddr());
		}
		return ordersDao.insert(orders);

	}

	@Override
	public int update(ThirdPartyAsk orders) {
		return ordersDao.update(orders);
	}

	@Override
	public int updateByOrderNo(ThirdPartyAsk orders){
		return ordersDao.updateByOrderNo(orders);
	}

	@Override
	public ThirdPartyAsk findById(Integer id) {
		return  ordersDao.findById(id);
	}

	@Override
	public ThirdPartyAsk findByOrderNo(String orderNo){
		return ordersDao.findByOrderNo(orderNo);
	}

	@Override
	public int insertByTableLastName(ThirdPartyAsk orders) {
		return ordersDao.insertByTableLastName(orders);
	}

	@Override
	public int updateByTableLastName(ThirdPartyAsk orders) {
		return ordersDao.updateByTableLastName(orders);
	}

	@Override
	public int updateByOrderNoByTableLastName(ThirdPartyAsk orders) {
		return ordersDao.updateByOrderNoByTableLastName(orders);
	}

	@Override
	public ThirdPartyAsk findByIdByTableLastName(Integer id, String tableLastName) {
		return   ordersDao.findByIdByTableLastName(id, tableLastName);
	}

	@Override
	public ThirdPartyAsk findByOrderNoByTableLastName(String orderNo, String tableLastName) {
		return  ordersDao.findByOrderNoByTableLastName(orderNo, tableLastName);
	}

	@Override
	public ThirdPartyAsk findByAct(String orderNo){
		return ordersDao.findByAct(orderNo);
	}
}
