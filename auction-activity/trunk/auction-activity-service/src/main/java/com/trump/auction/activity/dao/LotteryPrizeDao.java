package com.trump.auction.activity.dao;

import com.trump.auction.activity.domain.LotteryPrize;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 抽奖奖品
 * @author wangbo 2018/1/9.
 */
@Repository
public interface LotteryPrizeDao {
    /**
     * 插入一条奖品记录
     * @param lotteryPrize 抽奖奖品
     * @return 影响的行数
     */
    int insertLotteryPrize(LotteryPrize lotteryPrize);

    /**
     * 更新中奖概率及库存
     * @param lotteryPrize 奖品信息
     * @return 影响的行数
     */
    int updateRateAndStoreById(LotteryPrize lotteryPrize);

    /**
     * 更新所有的奖品状态为关闭，不属于任何方案
     * @return 影响的行数
     */
    int updateLotteryAndPlanZero();

    /**
     * 更新奖品方案
     * @param isPlan1 方案1
     * @param isPlan2 方案2
     * @param isOpen 是否开启
     * @param orderNumber 奖品排序
     * @param prizeNo 奖品编号
     * @return 影响的行数
     */
    int updatePrizePlan(@Param("isPlan1")String isPlan1, @Param("isPlan2")String isPlan2, @Param("isOpen")Integer isOpen,
                        @Param("orderNumber")Integer orderNumber, @Param("prizeNo")String prizeNo);

    /**
     * 查询开启的奖品
     * @return 开启的奖品列表
     */
    List<LotteryPrize> selectOpenPrizeList();

    /**
     * 查询可中奖的奖品
     * @return 可中奖的奖品列表
     */
    List<LotteryPrize> selectCanPrizeList();

    /**
     * 查找最近的奖品编号
     * @return 奖品编号
     */
    String selectLatestPrizeNo();

    /**
     * 根据奖品编号查找奖品信息
     * @param prizeNo 奖品编号
     * @return 奖品信息
     */
    LotteryPrize selectPrizeByPrizeNo(@Param("prizeNo")String prizeNo);

    /**
     * 修改库存
     * @param prizeNo 奖品编号
     * @param store 新库存
     * @param storeOld 老库存
     * @return 受影响的行数
     */
    int updateStoreByPrizeNo(@Param("prizeNo")String prizeNo,@Param("store")Integer store,@Param("storeOld")Integer storeOld);
}
