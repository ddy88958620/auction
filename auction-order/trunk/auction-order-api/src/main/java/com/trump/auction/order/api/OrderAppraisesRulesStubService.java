package com.trump.auction.order.api;


import com.trump.auction.order.model.OrderAppraisesRulesModel;

import java.util.List;

/**
 * 晒单评价规则相关服务
 * @author hanliangliang
 */
public interface OrderAppraisesRulesStubService {

	/**
	 * 查询评价所有规则详情
	 * @return
	 */
	List<OrderAppraisesRulesModel> queryAllRules();

	/**
	 * 根据评论、上传图片评定等级
	 * @param appraisesWords	评论内容
	 * @param appraisesPic				上传图片
	 * @return
	 */
	String orderAppraisesRulesLevelCheck(String appraisesWords, String appraisesPic);


}
