package com.trump.auction.account.service;

import com.alibaba.fastjson.JSONObject;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.account.constant.RedisConstant;
import com.trump.auction.account.dao.AccountInfoRecordDao;
import com.trump.auction.account.domain.AccountInfoRecord;
import com.trump.auction.account.enums.EnumAccountType;
import com.trump.auction.account.enums.EnumBalanceType;
import com.trump.auction.account.model.AccountInfoRecordModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyichao on 2017-12-19 下午 06:35.
 */
@Service
@Slf4j
public class AccountInfoRecordServiceImpl implements AccountInfoRecordService {
	private BeanMapper beanMapper;
	private JedisCluster jedisCluster;
	private AccountInfoRecordDao accountInfoRecordDao;

	public AccountInfoRecordServiceImpl(BeanMapper beanMapper, JedisCluster jedisCluster, AccountInfoRecordDao accountInfoRecordDao) {
		this.beanMapper = beanMapper;
		this.jedisCluster = jedisCluster;
		this.accountInfoRecordDao = accountInfoRecordDao;
	}

	@Override
	public Paging<AccountInfoRecordModel> getAccountInfoRecordList(AccountInfoRecordModel accountInfoRecordModel) {
		Map<String, String> map = jedisCluster.hgetAll(RedisConstant.COIN_IMAGE);
		String productThumbnail = "";
		String productImage = "";
		AccountInfoRecord accountInfoRecord = beanMapper.map(accountInfoRecordModel, AccountInfoRecord.class);
		PageHelper.startPage(accountInfoRecordModel.getPageNum(), accountInfoRecordModel.getPageSize());
		Paging<AccountInfoRecordModel> paging = PageUtils.page(accountInfoRecordDao.getAccountInfoRecordList(accountInfoRecord.getUserId(), accountInfoRecord.getAccountType(), accountInfoRecord.getCreateTime()), AccountInfoRecordModel.class, beanMapper);
		List<AccountInfoRecordModel> list = paging.getList();
		for (AccountInfoRecordModel airm : list) {
			Integer transactionCoin = airm.getTransactionCoin();
			if (transactionCoin > 0) {
				airm.setViewTransactionCoin("+" + transactionCoin / 100);
			} else {
				airm.setViewTransactionCoin("" + transactionCoin / 100);
			}
			if (airm.getAccountType() == EnumAccountType.AUCTION_COIN.getKey().intValue()) {
				String value = map.get(RedisConstant.COIN_AUCTION);
				JSONObject json = JSONObject.parseObject(value);
				productThumbnail = json.getString("productThumbnail");
				productImage = json.getString("productImage");
			} else if (airm.getAccountType() == EnumAccountType.PRESENT_COIN.getKey().intValue()) {
				String value = map.get(RedisConstant.COIN_PRESENT);
				JSONObject json = JSONObject.parseObject(value);
				productThumbnail = json.getString("productThumbnail");
				productImage = json.getString("productImage");
			} else if (airm.getAccountType() == EnumAccountType.POINTS.getKey().intValue()) {
				String value = map.get(RedisConstant.COIN_POINTS);
				JSONObject json = JSONObject.parseObject(value);
				productThumbnail = json.getString("productThumbnail");
				productImage = json.getString("productImage");
			}

			airm.setProductThumbnail(productThumbnail);
			airm.setProductImage(productImage);
		}
		return paging;
	}

	@Override
	public AccountInfoRecordModel getAccountInfoRecordById(Integer id) {
		AccountInfoRecordModel accountInfoRecordModel = beanMapper.map(accountInfoRecordDao.getAccountInfoRecordById(id), AccountInfoRecordModel.class);
		if(null == accountInfoRecordModel) {
			return null;
		}
		Integer transactionCoin = accountInfoRecordModel.getTransactionCoin();
		if (transactionCoin > 0) {
			accountInfoRecordModel.setViewTransactionCoin("+" + transactionCoin / 100);
		} else {
			accountInfoRecordModel.setViewTransactionCoin("" + transactionCoin / 100);
		}
		return accountInfoRecordModel;
	}

	/**
	 * 获取积分明细列表
	 */
	@Override
	public Paging<AccountInfoRecordModel> getPointsRecordList(Integer userId, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Paging<AccountInfoRecordModel> paging = PageUtils.page(accountInfoRecordDao.getPointsRecordList(userId, EnumAccountType.POINTS.getKey()), AccountInfoRecordModel.class, beanMapper);
		List<AccountInfoRecordModel> list = paging.getList();
		for (AccountInfoRecordModel airm : list) {
			Integer transactionCoin = airm.getTransactionCoin();
			if (airm.getBalanceType() == EnumBalanceType.BALANCE_IN.getKey().intValue()) {
				airm.setViewTransactionCoin("+" + transactionCoin / 100);
			} else {
				airm.setViewTransactionCoin("" + transactionCoin / 100);
			}
		}
		return paging;
	}
}
