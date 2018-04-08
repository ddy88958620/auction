package com.trump.auction.account.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangyichao on 2018-01-05 下午 01:11.
 * 用户账户返币记录表
 */
@Getter
@Setter
@ToString
public class AccountBackcoinRecord implements Serializable {
	private static final long serialVersionUID = 7053036936266967863L;

	private Integer id;
	private Integer userId;
	private String orderNo;
	private Integer transactionCoin;//返币数量(分)
	private Integer accountType;//账户类型
	private String accountTypeName;//账户名称
	private Date createTime;
	private Date updateTime;

}
