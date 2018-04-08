package com.trump.auction.pals.service;

import java.util.Date;

import com.trump.auction.pals.domain.PayRecord;

public interface PayRecordService {
    boolean insert(String userId, String payType, String payMode, String merchantId, String payFrom, Integer payAmount, String batchNo, String orderNo, Date orderTime);

    boolean insertSelective(PayRecord record);

    PayRecord selectByPrimaryKey(Long id);

    boolean updateByPrimaryKeySelective(PayRecord record);
}
