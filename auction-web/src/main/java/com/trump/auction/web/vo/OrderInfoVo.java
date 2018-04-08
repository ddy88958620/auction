package com.trump.auction.web.vo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.cf.common.utils.DateUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * Title: 
 * </p>
 * 
 * @author yll
 * @date 2017年12月25日下午5:12:11
 */
@ToString
public class OrderInfoVo  {
	
	//数据类型
	@Getter @Setter private Integer dataType = 2;//1是记录 2是订单
		
	//拍品Id
	@Getter @Setter private Integer auctionProdId = 0;
	
	//拍品Id
	@Setter private Integer auctionId = 0;
	public Integer getAuctionId() {
		if(auctionId != 0){
			return auctionId;
		}
		return  auctionNo;
	}
	
	@Setter private Integer auctionNo = 0;
    
	//拍品图片
    @Getter @Setter private String previewPic = "";
    @JSONField(serialize=false)
    @Getter @Setter private String productPic = "";
  
	//市场价
    @Getter @Setter private BigDecimal productPrice = new BigDecimal("0");
    
	//拍卖价
    @Getter @Setter private BigDecimal bidPrice = new BigDecimal("0");
    
	//出价次数
    @Setter private Integer bidCount = 0;
    
    //1是差价购
    @Getter @Setter private Integer orderType = 0;
    
    @Getter @Setter private Integer  appraisesStatus = 0;
    
	//订单updateTime
    /**
     * 修改时间
     */
    @Setter private Date createTime;
    
    @Setter private String orderTimeStr ;
    public String getUpdateTimeStr() {
    	if(createTime != null && dataType.equals(2)){
    		return DateUtil.getDateFormat(createTime, "yyyy-MM-dd HH:mm:ss");
    	}
    	return "";
	}
	//成交价格
    @Getter @Setter private BigDecimal finalPrice = new BigDecimal("0");
    
	//成交人昵称
    @Getter @Setter private String userName = "";
    
    @Getter @Setter private BigDecimal orderAmount = new BigDecimal("0");
    
    @Getter @Setter private BigDecimal buyCoinMoney = new BigDecimal("0");
	
	//订单状态
    @Getter @Setter private Integer orderStatus = 0;
    @Setter @Getter private Integer status = 0;
    
    @Getter @Setter private String orderId = "";
    @Setter  private Integer bidTimes = 0;
    
    public Integer getBidCount() {
    	if(dataType.equals(2)){
    		return bidTimes;
    	}
    	return bidCount;
	}
    
	//拍卖结束时间
    @Setter private Date endTime;
    
    @Setter private String endTimeStr ;
    public String getEndTimeStr() {
		if(endTime != null){
			return DateUtil.getDateFormat(endTime, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	//已返还多少开心币
    @Getter @Setter private BigDecimal returnPrice = new BigDecimal("0");
	
    //折扣
    @Setter private String percent;
    public String getPercent() {
    	if(finalPrice != null && productPrice != null){
            BigDecimal r = (productPrice.subtract(finalPrice)).divide(productPrice, 4, BigDecimal.ROUND_HALF_EVEN);
            DecimalFormat df = new DecimalFormat("#.00");
            double dd = r.doubleValue();
            if(dd <= 0.0){
            	return  "0.00%";
            }
            return  df.format(r.doubleValue()*100) + "%";
    	}
    	return "";
    }
    public static void main(String[] args) {
		OrderInfoVo vo = new OrderInfoVo();
		vo.setFinalPrice(new BigDecimal(46.2));
		vo.setProductPrice(new BigDecimal(6000));
		System.out.println(vo.getPercent());
	}
}
