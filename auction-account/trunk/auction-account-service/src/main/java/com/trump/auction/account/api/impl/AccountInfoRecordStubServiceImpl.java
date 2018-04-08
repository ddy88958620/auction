package com.trump.auction.account.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.util.page.Paging;
import com.trump.auction.account.api.AccountInfoRecordStubService;
import com.trump.auction.account.model.AccountInfoRecordModel;
import com.trump.auction.account.service.AccountInfoRecordService;

/**
 * Created by wangyichao on 2017-12-19 下午 06:33.
 */
@Service(version = "1.0.0")
public class AccountInfoRecordStubServiceImpl implements AccountInfoRecordStubService {
	private AccountInfoRecordService accountInfoRecordService;

	public AccountInfoRecordStubServiceImpl(AccountInfoRecordService accountInfoRecordService) {
		this.accountInfoRecordService = accountInfoRecordService;
	}


	@Override
	public Paging<AccountInfoRecordModel> getAccountInfoRecordList(AccountInfoRecordModel accountInfoRecordModel) {
		return accountInfoRecordService.getAccountInfoRecordList(accountInfoRecordModel);
	}

	@Override
	public AccountInfoRecordModel getAccountInfoRecordById(Integer id) {
		return accountInfoRecordService.getAccountInfoRecordById(id);
	}

	@Override
	public Paging<AccountInfoRecordModel> getPointsRecordList(Integer userId, Integer pageNum, Integer pageSize) {
		return accountInfoRecordService.getPointsRecordList(userId, pageNum, pageSize);
	}

}
