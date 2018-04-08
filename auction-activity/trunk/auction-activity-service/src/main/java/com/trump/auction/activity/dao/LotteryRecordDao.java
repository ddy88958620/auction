package com.trump.auction.activity.dao;

import com.trump.auction.activity.domain.LotteryRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 中奖记录
 * @author wangbo 2018/1/10.
 */
@Repository
public interface LotteryRecordDao {

    /**
     * 插入一条中奖记录
     * @param lotteryRecord 中奖记录
     * @return 受影响的行数
     */
    int insertLotteryRecord(LotteryRecord lotteryRecord);

    /**
     * 获取用户的中奖记录
     * @param userId 用户id
     * @return 中奖记录列表
     */
    List<LotteryRecord> selectLotteryRecordList(@Param("userId") Integer userId);
}
