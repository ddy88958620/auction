package com.trump.auction.trade.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 拍品价格浮动规则
 * 
 * @author yangyang
 *
 */
@Data
public class AuctionProductPriceRuleVo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer minFloatRate;
	private Integer maxFloatRate;
	private Integer randomRate;
}