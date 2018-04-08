package com.trump.auction.order.service;

import com.trump.auction.order.model.OrderAppraisesRulesModel;

import java.util.List;

/**
 * @Author=hanliangliang
 * @Date=2018/03/09
 */
public interface OrderAppraisesRulesService {
    /**
     * 查询评价所有规则详情
     * @return
     */
    List<OrderAppraisesRulesModel> queryAllRules();

    /**
     * 根据评论、上传图片评定等级
     * @param appraisesWords	评论内容
     * @param appraisesPic		上传图片
     * @return
     */
    String orderAppraisesRulesLevelCheck(String appraisesWords, String appraisesPic);


}
