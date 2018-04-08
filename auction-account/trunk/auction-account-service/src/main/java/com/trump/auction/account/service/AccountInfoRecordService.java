package com.trump.auction.account.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.account.model.AccountInfoRecordModel;

/**
 * Created by wangyichao on 2017-12-19 下午 06:35.
 */
public interface AccountInfoRecordService {
	/**
	 * 获取收支明细列表
	 */
	Paging<AccountInfoRecordModel> getAccountInfoRecordList(AccountInfoRecordModel accountInfoRecordModel);

	/**
	 * @param id 主键ID
	 * @return 根据主键获取一条账户记录
	 */
	AccountInfoRecordModel getAccountInfoRecordById(Integer id);

	/**
	 * 获取积分明细列表
	 */
	Paging<AccountInfoRecordModel> getPointsRecordList(Integer userId, Integer pageNum, Integer pageSize);
}
