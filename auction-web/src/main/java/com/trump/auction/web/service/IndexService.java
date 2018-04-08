package com.trump.auction.web.service;

import com.trump.auction.web.util.HandleResult;

/**
 * Created by songruihuan on 2017/12/21.
 */
public interface IndexService {

	HandleResult getIndexData(String userId, Integer pageNum, Integer pageSize, Integer type);

	HandleResult getIndexCommonData();
	
}
