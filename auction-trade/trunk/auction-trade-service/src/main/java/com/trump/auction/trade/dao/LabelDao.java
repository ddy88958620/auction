package com.trump.auction.trade.dao;

import com.trump.auction.trade.domain.Label;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: zhanping
 */
@Repository
public interface LabelDao {
    /**
     * 根据拍品id查询标签列表
     * @param productId
     * @return
     */
    List<Label> findByProductId(@Param("productId") Integer productId);
}
