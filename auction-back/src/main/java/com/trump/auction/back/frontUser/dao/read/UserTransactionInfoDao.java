package com.trump.auction.back.frontUser.dao.read;

import com.trump.auction.back.appraises.model.OrderAppraises;
import com.trump.auction.back.frontUser.model.UserInfo;
import com.trump.auction.back.frontUser.model.UserTransactionInfo;
import com.trump.auction.back.sys.model.ZTree;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public interface UserTransactionInfoDao {

	/**
	 * 查询用户交易列表信息
	 * @param params
	 * @return 用户交易列表信息
	 */
	List<UserTransactionInfo> selectUserTransactionInfo(HashMap<String, Object> params);

	/**
	 * 获取充值明细列表
	 * @param params
	 * @return
	 */
	List<UserTransactionInfo> getInfoRecord(HashMap<String, Object> params);
	/**
	 * 查询充值次数
	 */
	int countAccountRecharge( @Param("userId")Integer userId);
}
