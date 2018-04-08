package com.trump.auction.account.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangyichao on 2017-10-24 下午 03:31.
 * 用户账户
 */
@Data
public class AccountInfoModel implements Serializable {
	private static final long serialVersionUID = -1045436771814318711L;

	private Integer id;
	private Integer userId;
	private String userPhone;
	private String userName;
	/*拍币数额、赠币数量、积分数量*/
	private Integer coin;
	private Integer freezeCoin;//冻结币
	private Date createTime;
	private Date updateTime;
	/*用户账户的分类：1：拍币；2：赠币；3：积分*/
	private Integer accountType;
	private String remark;

}
