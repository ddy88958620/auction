package com.trump.auction.web.vo;

import java.math.BigDecimal;
import java.util.List;

import com.trump.auction.trade.vo.LabelVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class HotAuctionInfoVo {

    @Getter @Setter private Integer auctionId;

    @Getter @Setter private Integer auctionProdId;

    @Getter @Setter private String productName;//拍品名称

    @Getter @Setter private BigDecimal bidPrice;//最新拍卖价格

    @Getter @Setter private String previewPic;//拍品图片

    @Getter @Setter private Integer isCollect;//拍品图片 1收藏 0没有收藏

    @Getter @Setter private Integer status = 0; //1已结束；0拍卖中

    @Getter @Setter private Integer lastAuctionId ; //最新期数Id

    @Getter @Setter private long dynamicCountdown = 0; //动态倒计时

    @Getter @Setter private List<LabelVo> labelVos; //标签

}
