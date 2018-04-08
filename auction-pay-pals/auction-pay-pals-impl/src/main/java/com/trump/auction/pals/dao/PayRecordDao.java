package com.trump.auction.pals.dao;


import com.trump.auction.pals.domain.PayRecord;

public interface PayRecordDao {
    int insertSelective(PayRecord record);

    PayRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PayRecord record);
    
    PayRecord selectByOrderNo(String orderNo);

	void updateByOrderNo(PayRecord payRecord);

	PayRecord selectByBatchNo(String batchNo);

	String queryBatchNoByPrePayId(String prepayId);

	String queryBatchNoByOrderNo(String orderNo);
}