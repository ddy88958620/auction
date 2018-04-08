package com.trump.auction.cust.dao;

import com.trump.auction.cust.domain.UserProductCollect;
import com.trump.auction.cust.model.UserProductCollectModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProductCollectDao {

	/**
	 * 查询收藏列表信息
	 * @param userProductCollect
	 * @return 收藏列表信息
	 */
	List<UserProductCollectModel> selectUserProductCollectByUserId(UserProductCollect userProductCollect);

	/**
	 * 添加收藏信息
	 * @param obj 用户收藏实体类信息
	 * @return
	 */
	int insertUserProductCollect(UserProductCollect obj);

	/**
	 * 根据用户id，取消收藏状态
	 * @param id
	 * @return 受影响的行数
	 */
	int updateUserProductCollect(@Param("status")String  status, @Param("id")Integer id);

	/**
	 * 判断商品是否已收藏
	 * @param userId
	 * @param productId
	 * @param periodsId
	 * @return
	 */
	int selectUserProductCollectCount(@Param("userId")Integer userId,@Param("productId")Integer productId,@Param("periodsId")Integer periodsId);

	/**
	 * 取消收藏
	 * @param userId 用户id
	 * @param productId 产品id
	 * @param periodsId 期数id
	 * @return 受影响的行数
	 */
	int cancelUserProductCollect(@Param("userId")Integer userId,@Param("productId")Integer productId,@Param("periodsId")Integer periodsId);
}
