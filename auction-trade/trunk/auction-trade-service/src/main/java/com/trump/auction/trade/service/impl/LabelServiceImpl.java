package com.trump.auction.trade.service.impl;

import com.cf.common.util.mapping.BeanMapper;
import com.trump.auction.trade.dao.LabelDao;
import com.trump.auction.trade.domain.Label;
import com.trump.auction.trade.service.LabelService;
import com.trump.auction.trade.vo.LabelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: zhanping
 */
@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private LabelDao labelDao;

    @Override
    public List<LabelVo> findByProductId(Integer productId) {
        return beanMapper.mapAsList(labelDao.findByProductId(productId),LabelVo.class);
    }
}
