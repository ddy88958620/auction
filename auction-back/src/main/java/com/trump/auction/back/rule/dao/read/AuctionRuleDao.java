package com.trump.auction.back.rule.dao.read;

import com.trump.auction.back.product.vo.ParamVo;
import com.trump.auction.back.rule.model.AuctionRule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 竞拍规则管理
 *
 * @author
 * @create 2018-01-03 15:57
 **/

@Repository
public interface AuctionRuleDao {
    /**
     * 根据条件查询规则信息
     * @param paramVo
     * @return
     */
    List<AuctionRule> findAuctionRuleList(ParamVo paramVo);



    /**
     * 根据主键id查询规则信息
     * @param id
     * @return
     */
    AuctionRule findAuctionRuleById(Integer id);
    /**
     * 获取
     * @param id
     * @return
     */
    AuctionRule getAuctionRule(@Param(value = "id")Integer id);
}
