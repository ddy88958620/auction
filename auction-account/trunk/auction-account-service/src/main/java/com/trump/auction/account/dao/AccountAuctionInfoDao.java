package com.trump.auction.account.dao;

import com.trump.auction.account.domain.AccountAuctionInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by dingxp on 2018/1/5 0005.
 */
@Repository
public interface AccountAuctionInfoDao {

    int insertAccountAuctionInfo(AccountAuctionInfo accountAuctionInfo);

    int updateAccountStatus(@Param("status") Integer status,@Param("id") Integer id);

}
