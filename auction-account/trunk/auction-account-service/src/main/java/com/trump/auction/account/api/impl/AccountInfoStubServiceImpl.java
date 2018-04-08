package com.trump.auction.account.api.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.cf.common.utils.ServiceResult;
import com.google.common.collect.Lists;
import com.trump.auction.account.api.AccountInfoStubService;
import com.trump.auction.account.constant.RedisConstant;
import com.trump.auction.account.dto.AccountDto;
import com.trump.auction.account.dto.PointsExchangePresentDto;
import com.trump.auction.account.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisCluster;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyichao on 2017-12-19 下午 06:33.
 */
@Slf4j
@Service(version = "1.0.0")
public class AccountInfoStubServiceImpl implements AccountInfoStubService {
	private AccountInfoService accountInfoService;
	private JedisCluster jedisCluster;

	AccountInfoStubServiceImpl(AccountInfoService accountInfoService, JedisCluster jedisCluster) {
		this.accountInfoService = accountInfoService;
		this.jedisCluster = jedisCluster;
	}

	@Override
	public AccountDto getAccountInfo(Integer userId) {
		return accountInfoService.getAccountInfo(userId);
	}

	@Override
	public int getAuctionCoinByUserId(Integer userId, Integer type) {
		return accountInfoService.getAuctionCoinByUserId(userId, type);
	}

	/**
	 * 创建充值订单
	 */
	@Override
	public ServiceResult createAccountRechargeOrder(Integer userId, String userName, String userPhone, BigDecimal money, Integer transactionType, String outTradeNo) {
		return accountInfoService.createAccountRechargeOrder(userId, userName, userPhone, money, transactionType, outTradeNo);
	}

	@Override
	public ServiceResult rechargeUserAccount(boolean success, String outTradeNo, String resultJson) {
		try {
			return accountInfoService.rechargeUserAccount(success, outTradeNo, resultJson);
		} catch (Exception e) {
			log.error("充值拍币失败:{}", e);
			return new ServiceResult(ServiceResult.FAILED, e.getMessage());
		}
	}

	@Override
	public List<PointsExchangePresentDto> getPointsExchangeList() {
		List<PointsExchangePresentDto> list = Lists.newArrayList();
		Map<String, String> map = jedisCluster.hgetAll(RedisConstant.EXCHANGE_POINTS);
		for (String key : map.keySet()) {
			String value = map.get(key);
			JSONObject json = JSONObject.parseObject(value);
			int presentCoin = json.getInteger("presentCoin");
			int points = json.getInteger("points");
			String type = json.getString("type");
			String title = "￥" + presentCoin + type;
			PointsExchangePresentDto p = new PointsExchangePresentDto(presentCoin, points * 100, title, type);
			list.add(p);
		}
		return list;
	}

	/**
	 * 获取每天积分兑换赠币次数限制
	 */
	@Override
	public String getPointsExchangeTimesLimit() {
		Map<String, String> map = jedisCluster.hgetAll(RedisConstant.EXCHANGE_POINTS_EXCHANGE_LIMIT);
		if(null == map) {
			return "一";
		}
		String str = map.get(RedisConstant.EXCHANGE_POINTS_EXCHANGE_LIMIT);
		if(StringUtils.isEmpty(str)) {
			return "一";
		}
		return str.split(",")[0];
	}

	@Override
	public ServiceResult exchangePoints(Integer userId, Integer presentCoin) {
		try {
			return accountInfoService.exchangePoints(userId, presentCoin);
		} catch (Exception e) {
			log.error("兑换失败:{}", e);
			return new ServiceResult(ServiceResult.FAILED, "兑换失败");
		}
	}

	@Override
	public ServiceResult signGainPoints(Integer userId, String userPhone, Integer transactionCoin) {
		try {
			return accountInfoService.signGainPoints(userId, userPhone, transactionCoin);
		} catch (Exception e) {
			log.error("操作失败:{}", e);
			return new ServiceResult(ServiceResult.FAILED, "操作失败");
		}
	}

	@Override
    public ServiceResult initUserAccount(Integer userId, String userPhone, String userName, Integer accountType) {
        return accountInfoService.initUserAccount(userId, userPhone, userName, accountType);
    }

	/**
	 * 使用赠币或者拍币支付
	 */
	@Override
	public ServiceResult paymentWithCoin(Integer userId, String userPhone, String productName, Integer coin, String orderId, String orderSerial, String productImage) {
		try {
			return accountInfoService.paymentWithCoin(userId, userPhone, productName, coin, orderId, orderSerial, productImage);
		} catch (Exception e) {
			log.error("paymentWithCoin 使用赠币或者拍币支付:{} \n {}", e.getMessage(), e);
			return new ServiceResult(ServiceResult.FAILED, "出价失败");
		}
	}

	/**
	 * 差价购买，扣除开心币操作
	 */
	@Override
	public ServiceResult reduceBuyCoin(Integer userId, String userPhone, BigDecimal transactionCoin, String orderNo, Integer type) {
		try {
			return accountInfoService.reduceBuyCoin(userId, userPhone, transactionCoin, orderNo, type);
		} catch (Exception e) {
			log.error("扣除开心币失败:{}", e);
			return new ServiceResult(ServiceResult.FAILED, "扣除开心币失败");
		}
	}

	/**
	 * 返币操作
	 */
	@Override
	public ServiceResult backCoinOperation(String orderNo, Integer transactionCoin, Integer accountType, Integer productId, String productName, String productImage, Integer coinType, String userPhone, Integer userId) {
		try {
			return accountInfoService.backCoinOperation(orderNo, transactionCoin, accountType, productId, productName, productImage, coinType, userPhone, userId);
		} catch (Exception e){
			log.error("返币失败：orderNo:{}<br>{}", orderNo, e);
			return new ServiceResult(ServiceResult.FAILED, "操作失败");
		}
	}

	@Override
	public ServiceResult returnBuyCoin() {

		try {
			return accountInfoService.returnBuyCoin();
		} catch (Exception e) {
			log.error("首充返币操作失败：{}",e);
			return new ServiceResult(ServiceResult.FAILED, "操作失败");
		}
	}

	/**
	 * 拍卖-大转盘积分消耗
	 */
	@Override
	public ServiceResult lotteryCostPoints(Integer userId, Integer transactionPoints) {
		try {
			return accountInfoService.lotteryCostPoints(userId, transactionPoints);
		} catch (Exception e){
			log.error("大转盘积分扣除失败:{}", e);
			return new ServiceResult(ServiceResult.FAILED, "大转盘积分扣除失败");
		}
	}

	/**
	 * 大转盘中奖-操作账户
	 */
	@Override
	public ServiceResult lotteryPrizeUserAccount(Integer userId, Integer accountType, Integer transactionAmount, Integer productId, String productName, String productImage, Integer coinType, String userPhone) {
		try {
			return accountInfoService.lotteryPrizeUserAccount(userId, accountType, transactionAmount, productId, productName, productImage, coinType, userPhone);
		} catch (Exception e) {
			log.error("大转盘积分抽奖-写入奖品时出错:{}", e);
			return new ServiceResult(ServiceResult.FAILED, "大转盘积分抽奖,写入奖品时出错");
		}
	}

	@Override
	public ServiceResult backCoinByShareAuctionOrder(Integer userId, Integer coinNum, Integer accountType) {
		try {
			return accountInfoService.backCoinByShareAuctionOrder(userId, coinNum, accountType);
		} catch(Exception e) {
			log.error("晒单返币失败:{}", e);
			return new ServiceResult(ServiceResult.FAILED, "晒单返币失败");
		}
	}

	@Override
	public ServiceResult backShareCoin(Integer userId, Integer coinNum, Integer accountType) {
		try {
			return accountInfoService.backShareCoin(userId, coinNum, accountType);
		} catch(Exception e) {
			log.error("分享返币失败:{}", e);
			return new ServiceResult(ServiceResult.FAILED, "分享返币失败");
		}
	}
}
