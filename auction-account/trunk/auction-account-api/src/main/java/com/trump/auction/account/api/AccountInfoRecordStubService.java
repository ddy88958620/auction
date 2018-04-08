package com.trump.auction.account.api;

import com.cf.common.util.page.Paging;
import com.trump.auction.account.model.AccountInfoRecordModel;

/**
 * Created by wangyichao on 2017-12-19 下午 05:34.
 * 用户拍币账户记录服务
 */
public interface AccountInfoRecordStubService {

	/**
	 * 获取收支明细列表: userId, accountType, createTime,pageNum, pageSize
	 * <br>
	 * 此服务适用于获取除了积分的列表---拍币、赠币、开心币的混合列表
	 */
	Paging<AccountInfoRecordModel> getAccountInfoRecordList(AccountInfoRecordModel accountInfoRecordModel);

	/**
	 * @param id 主键ID
	 * @return 根据主键获取一条账户记录
	 */
	AccountInfoRecordModel getAccountInfoRecordById(Integer id);

	/**
	 * 获取积分明细列表
	 * @param userId 用户ID
	 * @param pageNum 页数
	 * @param pageSize 每页条数
	 */
	Paging<AccountInfoRecordModel> getPointsRecordList(Integer userId, Integer pageNum, Integer pageSize);

}
