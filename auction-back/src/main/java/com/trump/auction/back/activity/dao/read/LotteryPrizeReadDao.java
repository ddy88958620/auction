package com.trump.auction.back.activity.dao.read;

import com.trump.auction.back.activity.model.LotteryPrize;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 大转盘奖品查询相关
 * @author wangbo 2018/1/17.
 */
@Repository
public interface LotteryPrizeReadDao {

    /**
     * 查询所有奖品列表
     * @return 奖品列表
     */
    List<LotteryPrize> selectLotteryPrizeList();

    /**
     * 查询奖品库存及中奖概率
     * @param id 奖品id
     * @return 奖品库存及中奖概率
     */
    LotteryPrize selectLotteryPrizeById(@Param("id") Integer id);

    /**
     * 查询方案1的奖品列表
     * @return 方案1的奖品列表
     */
    List<Map<String,String>> selectPlan1LotteryList();

    /**
     * 查询方案2的奖品列表
     * @return 方案2的奖品列表
     */
    List<Map<String,String>> selectPlan2LotteryList();

    /**
     * 根据奖品编号获取奖品名称
     * @param prizeNo 奖品编号
     * @return  奖品名称
     */
    String selectPrizeNameByPrizeNo(@Param("prizeNo") String prizeNo);
}
