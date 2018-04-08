package com.trump.auction.account.service;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.account.dto.AccountDto;

import java.math.BigDecimal;

/**
 * Created by wangyichao on 2017-12-19 下午 06:35.
 */
public interface AccountInfoService {
	/**
	 * 获取时时的拍币、赠币、开心币、积分
	 */
	AccountDto getAccountInfo(Integer userId);

	/**
	 * 初始化财产账户
	 * @param userId      用户id
	 * @param userPhone   用户手机号
	 * @param userName    用户名
	 * @param accountType 账户类型
	 */
	ServiceResult initUserAccount(Integer userId, String userPhone, String userName, Integer accountType);

	/**
	 * 查询用户账户余额
	 */
	int getAuctionCoinByUserId(Integer userId, Integer type);

	/**
	 * 创建充值订单
	 */
	ServiceResult createAccountRechargeOrder(Integer userId, String userName, String userPhone, BigDecimal money, Integer transactionType, String outTradeNo);

	/**
	 * 拍币充值
	 */
	ServiceResult rechargeUserAccount(boolean success, String outTradeNo, String resultJson) throws Exception;

	/**
	 * 积分兑换
	 */
	ServiceResult exchangePoints(Integer userId, Integer presentCoin) throws Exception;

	/**
	 * 按类型更新账户
	 * <br>
	 * transactionCoin 本次交易拍币数量
	 * @return 结果
	 */
	ServiceResult signGainPoints(Integer userId, String userPhone, Integer transactionCoin) throws Exception;

	/**
	 * 使用赠币或者拍币支付
	 */
	ServiceResult paymentWithCoin(Integer userId, String userPhone, String productName, Integer coin,String orderId ,String orderSerial,String productImage) throws Exception;

	/**
	 * 差价购买，扣除开心币操作
	 */
	ServiceResult reduceBuyCoin(Integer userId, String userPhone, BigDecimal transactionCoin, String orderNo, Integer type) throws Exception;

	/**
	 * 返币操作
	 */
	ServiceResult backCoinOperation(String orderNo, Integer transactionCoin, Integer accountType, Integer productId, String productName, String productImage, Integer coinType, String userPhone, Integer userId) throws Exception;

	/**
	 * 用户首充返币操作
	 */
	ServiceResult returnBuyCoin();

	/**
	 * 拍卖-大转盘积分消耗
	 */
	ServiceResult lotteryCostPoints(Integer userId, Integer transactionPoints) throws Exception;

	/**
	 * 大转盘中奖-操作账户
	 */
	ServiceResult lotteryPrizeUserAccount(Integer userId, Integer accountType, Integer transactionAmount, Integer productId, String productName, String productImage, Integer coinType, String userPhone) throws Exception;

	/**
	 * 晒单返币
	 */
	ServiceResult backCoinByShareAuctionOrder(Integer userId, Integer coinNum, Integer accountType) throws Exception;

	/**
	 * 分享返赠币
	 * @param userId
	 * @param coinNum
	 * @param accountType
	 * @return
	 * @throws Exception
	 */
	ServiceResult backShareCoin(Integer userId, Integer coinNum, Integer accountType) throws Exception;
}
