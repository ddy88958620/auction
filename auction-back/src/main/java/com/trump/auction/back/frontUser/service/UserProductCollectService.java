package com.trump.auction.back.frontUser.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.frontUser.model.UserProductCollect;

import java.util.HashMap;


public interface UserProductCollectService {

	/**
	 * 分页查询收藏列表信息
	 * @param params
	 * @return 收藏列表信息
	 */

	Paging<UserProductCollect> selectUserProductCollectByUserId(HashMap<String,Object> params);
}
