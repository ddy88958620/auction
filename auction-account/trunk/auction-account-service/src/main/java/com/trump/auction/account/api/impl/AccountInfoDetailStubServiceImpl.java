package com.trump.auction.account.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.id.SnowflakeGenerator;
import com.cf.common.util.page.Paging;
import com.trump.auction.account.api.AccountInfoDetailStubService;
import com.trump.auction.account.dto.AccountInfoDetailDto;
import com.trump.auction.account.enums.EnumAccountType;
import com.trump.auction.account.model.AccountInfoDetailModel;
import com.trump.auction.account.service.AccountInfoDetailService;

import java.util.List;

/**
 * Created by dingxp on 2017-12-26 0027.
 */
@Service(version = "1.0.0")
public class AccountInfoDetailStubServiceImpl implements AccountInfoDetailStubService {
	private AccountInfoDetailService accountInfoDetailService;

	AccountInfoDetailStubServiceImpl(AccountInfoDetailService accountInfoDetailService) {
		this.accountInfoDetailService = accountInfoDetailService;
	}

	@Override
	public int insertAccountInfoDetail(AccountInfoDetailDto accountInfoDetailDto) {
		AccountInfoDetailModel infoDetailModel = new AccountInfoDetailModel();
		Long snowKey = SnowflakeGenerator.create().next();//雪花算法生成订单号
		infoDetailModel.setOrderNo(snowKey.toString());
		infoDetailModel.setUserId(accountInfoDetailDto.getUserId());
		infoDetailModel.setUserPhone(accountInfoDetailDto.getUserPhone());
		infoDetailModel.setCoinType(accountInfoDetailDto.getCoinType());
		Integer transactionType = accountInfoDetailDto.getTransactionType();
		infoDetailModel.setTransactionType(transactionType);
		String tag = transactionType == null ? "" : EnumAccountType.getUserAccountTypeName(transactionType);
		infoDetailModel.setTransactionTag(tag);
		infoDetailModel.setBuyCoinType(accountInfoDetailDto.getBuyCoinType());
		infoDetailModel.setCoin(accountInfoDetailDto.getCoin());
		infoDetailModel.setAvailableCoin(accountInfoDetailDto.getCoin());
		infoDetailModel.setUnavailableCoin(0);
		infoDetailModel.setProductId(accountInfoDetailDto.getProductId());
        infoDetailModel.setProductName(accountInfoDetailDto.getProductName());
        infoDetailModel.setValidStartTime(accountInfoDetailDto.getValidStartTime());
        infoDetailModel.setValidEndTime(accountInfoDetailDto.getValidEndTime());
        infoDetailModel.setRemark(tag);
		return accountInfoDetailService.insertAccountInfoDetail(infoDetailModel);
	}

	@Override
	public List<AccountInfoDetailModel> getAccountInfoDetailListByUserId(Integer userId) {
		return accountInfoDetailService.getAccountInfoDetailListByUserId(userId);
	}

	@Override
	public AccountInfoDetailModel getAccountInfoDetailById(Integer id) {
		return accountInfoDetailService.getAccountInfoDetailById(id);
	}

	@Override
	public Paging<AccountInfoDetailModel> getAccountInfoDetailList(Integer userId, Integer pageNum, Integer pageSize, Integer listType) {
		return accountInfoDetailService.getAccountInfoDetailList(userId, pageNum, pageSize, listType);
	}

}
