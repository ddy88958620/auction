package com.trump.auction.activity.api;

import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.activity.model.LotteryRecordModel;

/**
 * 中奖记录相关
 * @author wangbo 2018/1/10.
 */
public interface LotteryRecordStubService {
    /**
     * 插入一条中奖记录
     * @param lotteryRecordModel 中奖记录
     * @return 受影响的行数
     */
    ServiceResult addLotteryRecord(LotteryRecordModel lotteryRecordModel);

    /**
     * 获取用户的中奖记录
     * @param userId 用户id
     * @return 中奖记录列表
     */
    Paging<LotteryRecordModel> findLotteryRecordList(Integer userId, int pageNum, int pageSize);
}
