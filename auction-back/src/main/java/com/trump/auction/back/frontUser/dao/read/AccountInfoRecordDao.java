package com.trump.auction.back.frontUser.dao.read;

import com.trump.auction.back.frontUser.model.AccountInfoRecord;
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

	/**
	 * 获取账户收支明细列表
	 */
	List<AccountInfoRecord> getAccountInfoRecordList(@Param("userId") Integer userId, @Param("accountType") Integer accountType, @Param("createTime") Date createTime);

}
