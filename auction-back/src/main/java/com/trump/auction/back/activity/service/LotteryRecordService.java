package com.trump.auction.back.activity.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.activity.model.LotteryRecord;

import java.util.Map;

/**
 * 中奖记录相关
 * @author wangbo 2018/1/23.
 */
public interface LotteryRecordService {
    /**
     * 分页查询中奖记录
     * @param params 查询条件（奖品编号、手机号、订单号、中奖时间）
     * @return 中奖记录列表
     */
    Paging<LotteryRecord> findLotteryRecordListByPage(Map<String,Object> params);
}
