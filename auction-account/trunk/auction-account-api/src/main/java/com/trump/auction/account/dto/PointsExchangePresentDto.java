package com.trump.auction.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by wangyichao on 2017-12-21 下午 04:30.
 * 积分兑换赠币
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointsExchangePresentDto implements Serializable {
	private static final long serialVersionUID = -961936321717669998L;

	/*赠币数量*/
	private Integer presentCoin;
	/*积分数量*/
	private Integer points;
	/*标题*/
	private String title;
	/*赠币(可扩展字段)*/
	private String type;

}
