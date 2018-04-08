package com.trump.auction.web.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * Title: 差价购参数封装
 * </p>
 * 
 * @author yll
 * @date 2017年12月25日下午5:12:42
 */
@ToString
public class OrderParam {
	
	@Getter @Setter private Integer auctionId;
	@Getter @Setter private Integer userShippingId;
	@Getter @Setter private Integer auctionProdId;
	@Getter @Setter private Integer payType;
	@Getter @Setter private String remark;
	
}
