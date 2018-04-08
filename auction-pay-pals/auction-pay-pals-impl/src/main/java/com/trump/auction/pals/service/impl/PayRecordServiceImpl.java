package com.trump.auction.pals.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trump.auction.pals.dao.PayRecordDao;
import com.trump.auction.pals.domain.PayRecord;
import com.trump.auction.pals.service.PayRecordService;

import java.util.Date;


@Service
public class PayRecordServiceImpl implements PayRecordService {

    private Logger logger = LoggerFactory.getLogger(PayRecordServiceImpl.class);

    @Autowired
    private PayRecordDao payRecordDao;

    @Override
    public boolean insert(String userId, String payType, String payMode, String merchantId, String payFrom, Integer payAmount, String batchNo, String orderNo, Date orderTime){
        PayRecord record = new PayRecord();
        record.setUserId(userId);
        record.setMerchantId(merchantId);
        record.setPayType(payType);
        record.setPayMode(payMode);
        record.setPayFrom(payFrom);
        record.setPayAmount(payAmount.intValue());
        record.setBatchNo(batchNo);
        record.setOrderNo(orderNo);
        record.setOrderTime(orderTime);
        return payRecordDao.insertSelective(record) > 0;
    }

    @Override
    public boolean insertSelective(PayRecord record) {
        return payRecordDao.insertSelective(record) > 0;
    }

    @Override
    public PayRecord selectByPrimaryKey(Long id) {
        return payRecordDao.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateByPrimaryKeySelective(PayRecord record) {
        return payRecordDao.updateByPrimaryKeySelective(record) > 0;
    }
}

