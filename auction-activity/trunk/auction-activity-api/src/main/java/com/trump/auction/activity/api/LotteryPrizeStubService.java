package com.trump.auction.activity.api;

import com.cf.common.utils.ServiceResult;
import com.trump.auction.activity.model.LotteryPrizeModel;

import java.util.List;

/**
 * 抽奖奖品相关服务接口
 * @author wangbo 2018/1/9.
 */
public interface LotteryPrizeStubService {
    /**
     * 新增奖品信息
     * @param lotteryPrizeModel 奖品信息
     * @return ServiceResult
     */
    ServiceResult addLotteryPrize(LotteryPrizeModel lotteryPrizeModel);

    /**
     * 更新中奖概率及库存
     * @param lotteryPrizeModel 奖品信息
     * @return ServiceResult
     */
    ServiceResult updateRateAndStoreById(LotteryPrizeModel lotteryPrizeModel);

    /**
     * 更新奖品方案及奖品的状态
     * @param plan1PrizeNoList 方案1的奖品编号列表
     * @param plan2PrizeNoList 方案2的奖品编号列表
     * @param validPlan 启用的方案
     * @return ServiceResult
     */
    ServiceResult updatePrizePlanAndValid(List<String> plan1PrizeNoList, List<String> plan2PrizeNoList, String validPlan);

    /**
     * 查询开启的奖品
     * @return 开启的奖品列表
     */
    List<LotteryPrizeModel> findOpenPrizeList();

    /**
     * 查询可中奖的奖品
     * @return 可中奖的奖品列表
     */
    List<LotteryPrizeModel> findCanPrizeList();

    /**
     * 随机抽奖
     * @param userId 用户id
     * @param isExchange 是否是积分兑换抽奖
     * @return 抽奖结果
     */
    ServiceResult getLotteryPrizeByRandom(Integer userId,String isExchange);
}
