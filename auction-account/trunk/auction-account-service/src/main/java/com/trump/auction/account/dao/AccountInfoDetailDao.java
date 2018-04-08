package com.trump.auction.account.dao;

import com.trump.auction.account.domain.AccountInfoDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountInfoDetailDao {

	/**
	 * 添加账户详情记录
	 * @param accountInfoDetail
	 * @return
	 */
	int insertAccountInfoDetail(AccountInfoDetail accountInfoDetail);

	/**
	 * 获取账户详情列表列表
	 */
	List<AccountInfoDetail> getAccountInfoDetailListByUserId(Integer userId);

	/**
	 * 根据主键获取一条账户详情记录
	 */
	AccountInfoDetail getAccountInfoDetailById(@Param("id") Integer id);


	List<AccountInfoDetail> getAccountInfoDetailList(@Param("userId") Integer userId, @Param("listType") Integer listType, @Param("coinType") Integer coinType);

    /**
     * 更新开心币为已使用，并将使用开心币的数量写在unavailable_coin里，使用开心币操作时会用到
     * @param id 开心币ID
     * @param status 已使用状态、正在使用
     */
	int reduceBuyCoin(@Param("id") Integer id, @Param("status") Integer status, @Param("transactionCoin") Integer transactionCoin);

	/**
	 * 获取用户可用的开心币数量
	 * @param userId   用户ID
	 * @param coinType 类型
	 */
	int getAccountInfoDetail(@Param("userId") Integer userId, @Param("coinType") Integer coinType);

    /**
     * 根据期号查询最新一条开心币详情
     * @param status 1:未使用，null：只查询开心币详情
     */
    List<AccountInfoDetail> getAvailableBuyCoinById(@Param("orderNo") String orderNo, @Param("status") Integer status, @Param("userId") Integer userId);

    /**
     * 更新开心币状态
     */
    int updateAccountInfoDetailStatus(AccountInfoDetail accountInfoDetail);
}
