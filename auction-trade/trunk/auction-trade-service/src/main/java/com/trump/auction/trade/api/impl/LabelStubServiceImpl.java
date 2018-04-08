package com.trump.auction.trade.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.trump.auction.trade.api.LabelStubService;
import com.trump.auction.trade.service.LabelService;
import com.trump.auction.trade.vo.LabelVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Author: zhanping
 */
@Service(version = "1.0.0")
public class LabelStubServiceImpl implements LabelStubService {
    @Autowired
    private LabelService labelService;
    @Override
    public List<LabelVo> findByProductId(Integer productId) {
        return labelService.findByProductId(productId);
    }
}
