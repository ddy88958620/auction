package com.trump.auction.cust.api;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.cust.model.UserProductCollectModel;

/**
 * 收藏相关服务
 * @author wangbo
 */
public interface UserProductCollectStubService {

	/**
	 * 查询所有收藏列表
	 * @param userProductCollect 根据userId查询
	 * @param pageNum 页码
	 * @param pageSize 每页条数
	 * @return Paging<UserProductCollectModel> 分页后的收藏列表信息
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

	/**
	 * 判断该用户对应期数商品是否收藏
	 * @param userId
	 * @param productId
	 * @param periodsId
	 * @return
	 */
	boolean checkUserProductCollect(Integer userId,Integer productId, Integer periodsId);

	/**
	 * 取消收藏
	 * @param userId 用户id
	 * @param productId 产品id
	 * @param periodsId 期数id
	 * @return ServiceResult
	 */
	ServiceResult cancelUserProductCollect(Integer userId,Integer productId, Integer periodsId);
}
