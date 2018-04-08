package com.trump.auction.back.activity.service;

import com.cf.common.util.page.Paging;
import com.trump.auction.back.activity.model.LotteryPrize;

import java.util.List;
import java.util.Map;

/**
 * 大转盘奖品相关
 * @author wangbo 2018/1/17.
 */
public interface LotteryPrizeService {
    /**
     * 分页查询奖品列表
     * @param params 分页参数
     * @return 奖品列表
     */
    Paging<LotteryPrize> findLotteryPrizeListByPage(Map<String,Object> params);

    /**
     * 查询奖品库存及中奖概率
     * @param id 奖品id
     * @return 奖品库存及中奖概率
     */
    LotteryPrize findLotteryPrizeById(Integer id);

    /**
     * 查询方案1的奖品列表
     * @return 方案1的奖品列表
     */
    List<Map<String,String>> findPlan1LotteryList();

    /**
     * 查询方案2的奖品列表
     * @return 方案2的奖品列表
     */
    List<Map<String,String>> findPlan2LotteryList();

    /**
     * 根据奖品编号获取奖品名称
     * @param prizeNo 奖品编号
     * @return  奖品名称
     */
    String findPrizeNameByPrizeNo(String prizeNo);
}
