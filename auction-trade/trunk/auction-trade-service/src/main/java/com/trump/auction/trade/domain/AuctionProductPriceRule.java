package com.trump.auction.trade.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * 拍品价格浮动规则
 * 
 * @author yangyang
 *
 */
@Data
@ToString
public class AuctionProductPriceRule implements Serializable {
	private Long id;
	private Integer minFloatRate;
	private Integer maxFloatRate;
	private Integer randomRate;
	private Integer productInfoId;
}