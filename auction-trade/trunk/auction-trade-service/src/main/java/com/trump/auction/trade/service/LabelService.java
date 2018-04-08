package com.trump.auction.trade.service;

import com.trump.auction.trade.domain.Label;
import com.trump.auction.trade.vo.LabelVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author: zhanping
 */
public interface LabelService {
    /**
     * 根据拍品id查询标签列表
     * @param productId
     * @return
     */
    List<LabelVo> findByProductId(Integer productId);
}
