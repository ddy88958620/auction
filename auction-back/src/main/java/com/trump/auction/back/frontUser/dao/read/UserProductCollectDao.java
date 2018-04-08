package com.trump.auction.back.frontUser.dao.read;

import com.trump.auction.back.frontUser.model.UserProductCollect;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


@Repository
public interface UserProductCollectDao {

	/**
	 * 查询收藏列表信息
	 * @param params
	 * @return 收藏列表信息
	 */
	List<UserProductCollect> selectUserProductCollectByUserId(HashMap<String,Object> params);

}
