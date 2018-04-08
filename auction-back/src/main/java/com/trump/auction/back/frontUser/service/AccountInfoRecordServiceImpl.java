package com.trump.auction.back.frontUser.service;

import com.alibaba.fastjson.JSONObject;
import com.cf.common.util.mapping.BeanMapper;
import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.github.pagehelper.PageHelper;
import com.trump.auction.account.constant.RedisConstant;
import com.trump.auction.account.enums.EnumAccountType;
import com.trump.auction.account.enums.EnumBalanceType;
import com.trump.auction.back.frontUser.dao.read.AccountInfoRecordDao;
import com.trump.auction.back.frontUser.model.AccountInfoRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyichao on 2017-12-19 下午 06:35.
 */
@Service
@Slf4j
public class AccountInfoRecordServiceImpl implements AccountInfoRecordService {
	@Autowired
	private BeanMapper beanMapper;
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private AccountInfoRecordDao accountInfoRecordDao;

	@Override
	public Paging<AccountInfoRecord> getAccountInfoRecordList(AccountInfoRecord accountInfoRecordModel) {
		Map<String, String> map = jedisCluster.hgetAll(RedisConstant.COIN_IMAGE);
		String productThumbnail = "";
		String productImage = "";
		AccountInfoRecord accountInfoRecord = beanMapper.map(accountInfoRecordModel, AccountInfoRecord.class);
		PageHelper.startPage(accountInfoRecordModel.getPageNum(), accountInfoRecordModel.getPageSize());
		Paging<AccountInfoRecord> paging = PageUtils.page(accountInfoRecordDao.getAccountInfoRecordList(accountInfoRecord.getUserId(), accountInfoRecord.getAccountType(), accountInfoRecord.getCreateTime()), AccountInfoRecord.class, beanMapper);
		List<AccountInfoRecord> list = paging.getList();
		for (AccountInfoRecord airm : list) {
			Integer transactionCoin1 = airm.getTransactionCoin();
			if (transactionCoin1 == null) transactionCoin1 = 0;
			int transactionCoin = transactionCoin1;
			airm.setTransactionCoin(transactionCoin);
			if (transactionCoin > 0) {
				airm.setViewTransactionCoin("+" + new BigDecimal(transactionCoin).divide(new BigDecimal(100)).toString());
			} else {
				airm.setViewTransactionCoin(new BigDecimal(transactionCoin).divide(new BigDecimal(100)).toString());
			}
			if (airm.getAccountType() != null && airm.getAccountType() == EnumAccountType.AUCTION_COIN.getKey().intValue()) {
				String value = map.get(RedisConstant.COIN_AUCTION);
				JSONObject json = JSONObject.parseObject(value);
				productThumbnail = json.getString("productThumbnail");
				productImage = json.getString("productImage");
			} else if (airm.getAccountType() != null && airm.getAccountType() == EnumAccountType.PRESENT_COIN.getKey().intValue()) {
				String value = map.get(RedisConstant.COIN_PRESENT);
				JSONObject json = JSONObject.parseObject(value);
				productThumbnail = json.getString("productThumbnail");
				productImage = json.getString("productImage");
			} else if (airm.getAccountType() != null && airm.getAccountType() == EnumAccountType.POINTS.getKey().intValue()) {
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

}
