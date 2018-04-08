package com.trump.auction.account.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangyichao on 2017-12-20 上午 11:04.
 * 用户账户
 */
@Data
public class AccountInfo implements Serializable {
	private static final long serialVersionUID = -4104458834079326833L;

	private Integer id;
	private Integer userId;
	private String userPhone;
	private String userName;//用户姓名
	private Integer coin;//拍币数额、赠币数量、积分数量
	private Integer freezeCoin;//冻结币
	private Date createTime;
	private Date updateTime;
	private Integer accountType;//用户账户的分类：1：拍币；2：赠币；3：积分
	private String remark;


}
