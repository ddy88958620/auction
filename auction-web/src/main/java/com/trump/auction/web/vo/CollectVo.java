package com.trump.auction.web.vo;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class CollectVo {
	
	@Setter private Integer id;
	
	@Setter private Integer collectId;
	
    @Getter @Setter private Integer auctionId;
    
	@Getter @Setter private Integer auctionProdId;

    @Getter @Setter private String productName;//拍品名称

    @Getter @Setter private BigDecimal bidPrice;//最新拍卖价格
    
    @Getter @Setter private String previewPic;//拍品图片
    
    @Getter @Setter private Integer isCollect;//拍品图片 1收藏 0没有收藏
    
    @Getter @Setter private Integer status; //1已结束；0拍卖中

    @Getter @Setter private Integer lastAuctionId ; //最新期数Id
    
    public Integer getCollectId() {
		return id;
	}
}
