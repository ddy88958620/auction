package com.trump.auction.web.vo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import com.cf.common.utils.DateUtil;
import com.trump.auction.web.util.Base64Utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * Title: 最新成交Vo
 * </p>
 * 
 * @author yll
 * @date 2017年12月25日下午5:12:11
 */
@ToString
public class LastAuctionInfoVo  {
	
	@Setter private Integer id;
	
	@Setter private Integer auctionId;
	
	public Integer getAuctionId(){
		return id;
	}

	@Getter @Setter private Integer auctionProdId;
	
    @Getter @Setter private String productName;//拍品名称

    @Getter @Setter private BigDecimal productPrice;//市场价
    
    @Setter private BigDecimal finalPrice;;//成交价
    
    @Getter @Setter private BigDecimal bidPrice;;//成交价
    
    @Getter @Setter private Integer lastAuctionId;
    
    public BigDecimal getFinalPrice() {
    	if(finalPrice != null){
    		return finalPrice;
    	}
		return bidPrice;
	}
    

    @Getter @Setter private String previewPic;//拍品图片

    @Setter private Date endTime;
    
    @Setter private String updateTimeStr;
    
    @Setter private String percent;
    
    @Setter private String userName;
    
    @Getter @Setter private String headImg;
    
    public String getUserName() {
		if(userName != null){
			return Base64Utils.decodeStr(userName);
		}
		return "";
	}
    
    public String getPercent() {
    	if(getFinalPrice() == null){
    		return "";
    	}
        BigDecimal r = (productPrice.subtract(getFinalPrice())).divide(productPrice, 4, BigDecimal.ROUND_HALF_UP);
        DecimalFormat df = new DecimalFormat("#.00");
        double dd = r.doubleValue();
        if(dd <= 0.0){
        	return  "0.00%";
        }
        return  df.format(r.doubleValue()*100) + "%";
    }
    
    public String getUpdateTimeStr() {
    	if(endTime == null){
    		return "";
    	}
		return DateUtil.getDateFormat(endTime, "yyyy-MM-dd HH:mm:ss");
		
	}
    
}
