package com.trump.auction.back.userRecharge.dao.read;

import com.trump.auction.back.userRecharge.model.AccountRechargeOrder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


@Repository
public interface AccountRechargeOrderDao {

	/**
	 * 查询用户拍币充值订单列表信息
	 * @param params
	 * @return 用户拍币充值订单信息
	 */
	List<AccountRechargeOrder> selectAccountRechargeOrder(HashMap<String, Object> params);


}
