package com.trump.auction.web.vo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import com.cf.common.utils.DateUtil;

import lombok.Getter;
import lombok.Setter;

public class TransferOrderDetail {
	
	 /**
     * 交易订单id
     */
	@Getter @Setter private String orderId = "";
	
	@Getter @Setter private Integer orderStatus = 0;
	
	@Getter @Setter private BigDecimal returnPrice;
	
    /**
     * 订单类型
     */
	@Getter @Setter private Integer orderType;
	
	/**
     * 创建时间
     */
	@Setter private Date createTime;
	
	
	public String getBidSuccessTimeStr() {
		if(createTime != null){
			return DateUtil.getDateFormat(createTime, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	
	@Setter private Date buyerPayTime;
	
	public String getPayTimeStr() {
		if(buyerPayTime != null){
			return DateUtil.getDateFormat(buyerPayTime, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	
    /**
     * 实付款
     */
	@Getter @Setter private BigDecimal paidMoney = new BigDecimal("0");
	
    /**
     * 订单取消时间
     */
	@Setter private Date cancelTime;
	public String getCancelTimeStr() {
		if(cancelTime != null){
			return DateUtil.getDateFormat(cancelTime, "yyyy-MM-dd HH:mm:ss"); 
		}
		return "";
	}
	
    /**
     * 收货时间
     */
	@Setter private Date orderReceiveTime;
	@Setter private Date updateTime;
	public String getOrderReceiveTimeStr() {
		if(orderReceiveTime != null){
			return DateUtil.getDateFormat(orderReceiveTime, "yyyy-MM-dd HH:mm:ss"); 
		}
		return "";
	}
	
	
	@Setter private Date appraisesTime;
	public String getAppraisesTimeStr() {
		if(appraisesTime != null){
			return DateUtil.getDateFormat(appraisesTime, "yyyy-MM-dd HH:mm:ss"); 
		}
		return "";
	}
	
	@Setter private String company = "";
	public String getCompany() {
		if(company == null){
			return "";
		}
		return company;
	}
	
	
	@Setter private String expressNo = "";
	public String getExpressNo() {
		if(expressNo == null){
			return "";
		}
		return expressNo;
	}
	
	public String appraiseTimeStr() {
		if(orderStatus.equals(5)){
			if(updateTime != null){
				return DateUtil.getDateFormat(updateTime, "yyyy-MM-dd HH:mm:ss"); 
			}
		}
		return "";
	}
	
	
	 /**
     * 商品图片
     */
	@Getter @Setter private String productPic = "";
	
    /**
     * 商品金额
     */
	@Getter @Setter private BigDecimal productPrice = new BigDecimal("0");
	


    /**
     *成交价
     */
    @Getter @Setter private BigDecimal finalPrice = new BigDecimal("0");
    
    @Getter @Setter private String winnerName = "";
	
	
    /**
     * 收货人姓名
     */
	@Getter @Setter private String userName = "";

    /**
     * 收货人手机号
     */
	@Getter @Setter private String userPhone = "";
	
    /**
     * 详细地址
     */
	@Setter private String address = "";
	
	public String getAddress() {
		return provinceName + cityName + districtName + address;
	}

    /**
     * 省
     */
	@Setter private String provinceName = "";

    /**
     * 市
     */
	@Setter private String cityName = "";

    /**
     * 县
     */
	@Setter private String districtName = "";


	
    /**
     * 商品名称
     */
	@Getter @Setter private String productName = "";
	
	 public String getPercent() {
    	if(finalPrice == null){
    		return "";
    	}
        BigDecimal r = (productPrice.subtract(finalPrice)).divide(productPrice, 4, BigDecimal.ROUND_HALF_EVEN);
        DecimalFormat df = new DecimalFormat("#.00");
        double dd = r.doubleValue();
        if(dd <= 0.0){
        	return  "0.00%";
        }
        return  df.format(r.doubleValue()*100) + "%";
	 }
	 
	 public static void main(String[] args) {
		TransferOrderDetail vo = new TransferOrderDetail();
		vo.setFinalPrice(new BigDecimal(46.2));
		vo.setProductPrice(new BigDecimal(6000));
		System.out.println(vo.getPercent());
	 }
}
