package com.trump.auction.back.frontUser.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.frontUser.model.AccountInfoRecord;

/**
 * Created by wangyichao on 2017-12-19 下午 06:35.
 */
public interface AccountInfoRecordService {
	/**
	 * 获取收支明细列表
	 */
	Paging<AccountInfoRecord> getAccountInfoRecordList(AccountInfoRecord accountInfoRecord);

}
