package com.trump.auction.account.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by wangyichao on 2017-12-21 上午 10:43.
 */
@Data
public class AccountDto implements Serializable {
	private static final long serialVersionUID = -295159040042648419L;

	private Integer auctionCoin = 0;//拍币
	private Integer presentCoin = 0;//赠币
	private Integer points = 0;//积分
	private Integer shoppingCoin = 0;//开心币
	private Integer freezeCoin = 0;//冻结拍币
}
