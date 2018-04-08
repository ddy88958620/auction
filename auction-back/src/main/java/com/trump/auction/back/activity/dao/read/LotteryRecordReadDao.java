package com.trump.auction.back.activity.dao.read;

import com.trump.auction.back.activity.model.LotteryRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 中奖记录读操作相关
 * @author wangbo 2018/1/23.
 */
@Repository
public interface LotteryRecordReadDao {

    /**
     * 查询中奖记录列表
     * @param params 查询条件（奖品编号、手机号、订单号、中奖时间）
     * @return 中奖记录列表
     */
    List<LotteryRecord> selectLotteryRecordList(Map<String, Object> params);
}
