package com.trump.auction.activity.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cf.common.utils.ServiceResult;
import com.trump.auction.activity.api.LotteryPrizeStubService;
import com.trump.auction.activity.model.LotteryPrizeModel;
import com.trump.auction.activity.service.LotteryPrizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 抽奖奖品相关服务
 * @author wangbo 2018/1/9.
 */
@Slf4j
@Service(version = "1.0.0")
public class LotteryPrizeStubServiceImpl implements LotteryPrizeStubService {
    @Autowired
    private LotteryPrizeService lotteryPrizeService;

    @Override
    public ServiceResult addLotteryPrize(LotteryPrizeModel lotteryPrizeModel) {
        return lotteryPrizeService.addLotteryPrize(lotteryPrizeModel);
    }

    @Override
    public ServiceResult updateRateAndStoreById(LotteryPrizeModel lotteryPrizeModel) {
        return lotteryPrizeService.updateRateAndStoreById(lotteryPrizeModel);
    }

    @Override
    public ServiceResult updatePrizePlanAndValid(List<String> plan1PrizeNoList, List<String> plan2PrizeNoList, String validPlan) {
        try {
            return lotteryPrizeService.updatePrizePlanAndValid(plan1PrizeNoList, plan2PrizeNoList, validPlan);
        } catch (Exception e) {
            return new ServiceResult(ServiceResult.FAILED,"操作失败");
        }
    }

    @Override
    public List<LotteryPrizeModel> findOpenPrizeList() {
        return lotteryPrizeService.findOpenPrizeList();
    }

    @Override
    public List<LotteryPrizeModel> findCanPrizeList() {
        return lotteryPrizeService.findCanPrizeList();
    }

    @Override
    public ServiceResult getLotteryPrizeByRandom(Integer userId, String isExchange) {
        try {
            return lotteryPrizeService.getLotteryPrizeByRandom(userId,isExchange);
        } catch (Exception e) {
            log.error("getLotteryPrizeByRandom error,userId:{},isExchange:{}",userId,isExchange,e);
            return new ServiceResult("-3","网络繁忙");
        }
    }
}
