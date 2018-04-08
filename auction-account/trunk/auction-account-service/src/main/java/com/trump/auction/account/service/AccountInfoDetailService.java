package com.trump.auction.account.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.account.model.AccountInfoDetailModel;

import java.util.List;


/**
 * Created by dingxp on 2017-12-26 0027.
 * 开心币类型
 */
public interface AccountInfoDetailService {
	/**
	 * 获取收支明细列表
	 */
	List<AccountInfoDetailModel> getAccountInfoDetailListByUserId(Integer userId);

	/**
	 * @param id 主键ID
	 * @return 根据主键获取一条账户记录
	 */
	AccountInfoDetailModel getAccountInfoDetailById(Integer id);

	/**
	 * 插入一条账户详情记录
	 */
	int insertAccountInfoDetail(AccountInfoDetailModel accountInfoDetailModel);

	/**
	 * @param userId 用户ID
	 * @param pageNum 页数
	 * @param pageSize 分页大小
	 * @return 获取开心币列表
	 */
	Paging<AccountInfoDetailModel> getAccountInfoDetailList(Integer userId, Integer pageNum, Integer pageSize, Integer listType);
}
