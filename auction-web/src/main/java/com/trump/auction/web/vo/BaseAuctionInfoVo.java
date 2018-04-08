package com.trump.auction.web.vo;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class BaseAuctionInfoVo {
	
	@Getter @Setter private Integer auctionId;
	
	@Getter @Setter private Integer auctionProdId;
	
    @Getter @Setter private String productName;
	
	@Getter @Setter private List<JSONObject> masterPics;
	
	@Getter @Setter private List<JSONObject> detailPics;
    
    @Getter @Setter private BigDecimal productPrice = new BigDecimal("0");
    
    @Getter @Setter private Integer isCollect;
    
    /**
     * 状态（1正在拍 2已完结 3未开始）
     */
    @Getter @Setter private Integer status;
    
    @Getter @Setter private String ruleDetail =  "";
    
    public JSONArray getRule() {
    	JSONArray array =   new  JSONArray();
		if(startBidName != null && startPrice != null){
			JSONObject json1 = new JSONObject();
			json1.put("key", startBidName);
			json1.put("value",startPrice+"元");
			array.add(json1);
		}
		if(increaseBidName != null && increasePrice != null){
			JSONObject json2 = new JSONObject();
			json2.put("key", increaseBidName);
			json2.put("value",increasePrice+"元");
			array.add(json2);
		}
		if(poundageName != null && poundage != null){
			JSONObject json3 = new JSONObject();
			json3.put("key", poundageName);
			json3.put("value",poundage+"拍币");
			array.add(json3);
		}
		if(countdownName != null && countdown != null){
			JSONObject json4 = new JSONObject();
			json4.put("key", countdownName);
			json4.put("value",countdown+"s");
			array.add(json4);
		}
		if(differenceName != null && buyFlag != null){
			JSONObject json5 = new JSONObject();
			json5.put("key", differenceName);
			json5.put("value",buyFlag==1?"有":"无");
			array.add(json5);
		}
		if(proportionName != null && returnPercent != null){
			JSONObject json6 = new JSONObject();
			json6.put("key", proportionName);
			json6.put("value",returnPercent+"%");
			array.add(json6);
		}
		return array;
	}
    
    /**
     * 起拍名称
     */
    @Setter private String startBidName;
    @Setter private BigDecimal startPrice;

    /**
     * 每次加价名称
     */
    @Setter private String increaseBidName;
    @Getter @Setter private BigDecimal increasePrice;

    /**
     * 手续费名称
     */
    @Setter private String poundageName;
    @Getter @Setter private BigDecimal  poundage =  new BigDecimal("0");

    /**
     * 倒计时名称
     */
    @Setter private String countdownName;
    @Getter @Setter private Integer countdown;

    /**
     * 差价购买名称
     */
    @Setter private String differenceName;
    @Setter private Integer buyFlag;

    
    
    /**
     * 退币比例名称
     */
    @Setter private String proportionName;
    @Setter private BigDecimal returnPercent;


}
