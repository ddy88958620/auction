package com.trump.auction.account.dao;

import com.trump.auction.account.domain.AccountInfoRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangyichao on 2017-12-20 下午 06:39.
 * 用户账户记录
 */
@Repository
public interface AccountInfoRecordDao {

	int insertAccountInfoRecord(AccountInfoRecord accountInfoRecord);

	/**
	 * 获取账户收支明细列表
	 */
	List<AccountInfoRecord> getAccountInfoRecordList(@Param("userId") Integer userId, @Param("accountType") Integer accountType, @Param("createTime") Date createTime);

	/**
	 * 根据主键获取一条账户记录
	 */
	AccountInfoRecord getAccountInfoRecordById(@Param("id")Integer id);

	/**
	 * 获取用户
	 * @param userId 用户ID
	 * @param transactionType 交易类型
	 * @return 积分兑换赠币的次数
	 */
	int getExchangePointsCount(@Param("userId") Integer userId, @Param("transactionType") Integer transactionType);

	/**
	 * 获取积分详细列表
	 */
	List<AccountInfoRecord> getPointsRecordList(@Param("userId") Integer userId, @Param("accountType") Integer accountType);

	List<AccountInfoRecord> getAccountInfoRecordListByOrderId(@Param("userId") Integer userId,@Param("orderId") String orderId);

	List<AccountInfoRecord> getAccountRecordListByParameter(HashMap<String,Object> map);

    /**
     * 取最新一条竞拍交易记录
     */
    AccountInfoRecord getAccountInfoRecordByOrderSerial(@Param("orderNo") String orderNo, @Param("transactionType") Integer transactionType, @Param("userId") Integer userId);
}
