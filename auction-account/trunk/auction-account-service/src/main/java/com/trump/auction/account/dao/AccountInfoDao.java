package com.trump.auction.account.dao;

import com.trump.auction.account.domain.AccountInfo;
import com.trump.auction.account.dto.AccountDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangyichao on 2017-12-19 下午 06:36.
 */
@Repository
public interface AccountInfoDao {

	void insertUserAccount(AccountInfo userAuctionAccount);

	/**
	 * 获取时时的拍币、赠币、开心币、积分、冻结积分
	 */
	AccountDto getAccountInfoByUserId(@Param("userId") Integer userId, @Param("auctionCoin") Integer auctionCoin, @Param("presentCoin") Integer presentCoin, @Param("points") Integer points);

	/**
	 * 查询用户账户余额
	 */
	AccountInfo getAuctionCoinByUserId(@Param("userId") Integer userId, @Param("type") Integer type);

	/**
	 * 公共的更新账户的方法
	 * @param userId 用户ID
	 * @param transactionAmount 交易的coin， 为正数时是加，为负数时是减
	 * @param accountType 账户的类型
	 * @return 0或1
	 */
	int updateAccount(@Param("userId") Integer userId, @Param("transactionAmount") Integer transactionAmount, @Param("accountType") Integer accountType);
}
