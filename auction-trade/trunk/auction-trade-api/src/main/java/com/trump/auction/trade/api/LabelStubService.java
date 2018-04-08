package com.trump.auction.trade.api;

import com.trump.auction.trade.vo.LabelVo;

import java.util.List;

/**
 * Author: zhanping
 */
public interface LabelStubService {
    /**
     * 根据拍品id查询标签列表
     * @param productId
     * @return
     */
    List<LabelVo> findByProductId(Integer productId);
}
