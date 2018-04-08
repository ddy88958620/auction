package com.trump.auction.cust.service;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.model.UserProductCollectModel;

public interface UserProductCollectService {

	/**
	 * 分页查询收藏列表信息
	 * @param userProductCollect 根据userId查询
	 * @param pageNum 页码
	 * @param pageSize 每页条数
	 * @return
	 */
	Paging<UserProductCollectModel> findUserProductCollectPage(UserProductCollectModel userProductCollect , Integer pageNum , Integer pageSize);

	/**
	 * 添加收藏信息
	 * @param obj 用户收藏信息
	 * @return 影响记录条数
	 */
	ServiceResult saveUserProductCollect(UserProductCollectModel obj);

	/**
	 * 根据用户id，取消收藏状态
	 * @param id
	 * @return 受影响的行数
	 */
	ServiceResult updateUserProductCollect(String  status, Integer id);

	boolean checkUserProductCollect(Integer userId, Integer productId, Integer periodsId);

	/**
	 * 取消收藏
	 * @param userId 用户id
	 * @param productId 产品id
	 * @param periodsId 期数id
	 * @return ServiceResult
	 */
	ServiceResult cancelUserProductCollect(Integer userId,Integer productId, Integer periodsId);
}
