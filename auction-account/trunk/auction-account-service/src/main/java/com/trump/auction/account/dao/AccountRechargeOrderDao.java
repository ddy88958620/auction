package com.trump.auction.account.dao;

import com.trump.auction.account.domain.AccountRechargeOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangyichao on 2017-12-19 下午 06:43.
 */
@Repository
public interface AccountRechargeOrderDao {
	/**
	 * 更新充值订单的状态
	 */
	int updateUserAccountRechargeOrderStatus(AccountRechargeOrder userAccountRechargeOrder);

	int createAccountRechargeOrder(AccountRechargeOrder accountRechargeOrder);

	/**
	 * 根据订单号获取订单信息
	 */
	AccountRechargeOrder getAccountRechargeOrderByOutTradeNo(@Param("outTradeNo") String outTradeNo);

	/**
	 * 更新充值订单为失败
	 */
	int updateUserAccountRechargeOrderFailed(@Param("outTradeNo") String orderNo, @Param("tradeStatus") Integer tradeStatus, @Param("payRemark") String payRemark, @Param("resultJson") String resultJson);

	/**
	 * 查询未完成的充值订单
	 */
    List<AccountRechargeOrder> getUnfinishedRechargeOrder(@Param("tradeStatus") Integer tradeStatus, @Param("orderStatus") Integer orderStatus);

}
