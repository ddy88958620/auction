package com.trump.auction.reactor.api.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 出价统计
 * @author yangyang
 *
 */
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = { "userId" })
public class BidStatistics implements Serializable {

	/**
	 * 用户编号
	 */
	@Getter
	@Setter
	private String userId;
	/**
	 * 出价次数
	 */
	@Getter
	@Setter
	private Integer bidCount;
	/**
	 * 拍币
	 */
	@Getter
	@Setter
	private Integer pCoin;
	/**
	 * 赠币
	 */
	@Getter
	@Setter
	private Integer zCoin;
}
