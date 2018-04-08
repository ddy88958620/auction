package com.trump.auction.activity.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.util.page.Paging;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.activity.api.LotteryRecordStubService;
import com.trump.auction.activity.model.LotteryRecordModel;
import com.trump.auction.activity.service.LotteryRecordService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 中奖记录相关
 * @author wangbo 2018/1/10.
 */
@Service(version = "1.0.0")
public class LotteryRecordStubServiceImpl implements LotteryRecordStubService {
    @Autowired
    private LotteryRecordService lotteryRecordService;

    @Override
    public ServiceResult addLotteryRecord(LotteryRecordModel lotteryRecordModel) {
        return lotteryRecordService.addLotteryRecord(lotteryRecordModel);
    }

    @Override
    public Paging<LotteryRecordModel> findLotteryRecordList(Integer userId, int pageNum, int pageSize) {
        return lotteryRecordService.findLotteryRecordList(userId, pageNum, pageSize);
    }
}
