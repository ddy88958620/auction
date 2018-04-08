package com.trump.auction.account.dao;

import com.trump.auction.account.domain.AccountBackcoinRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by wangyichao on 2018-01-05 下午 01:59.
 */
@Repository
public interface AccountBackCoinRecordDao {

	AccountBackcoinRecord getAccountBackCoinRecordByOrderNo(@Param("orderNo") String orderNo, @Param("accountType") Integer accountType, @Param("userId") Integer userId);

	int insertAccountBackCoinRecord(AccountBackcoinRecord accountBackcoinRecord);
}
