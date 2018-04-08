package com.trump.auction.web.vo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import com.cf.common.utils.DateUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * Title: 订单详情
 * </p>
 * 
 * @author yll
 * @date 2017年12月25日下午5:07:47
 */
@ToString
public class OrderDetailVo {
	
	
    @Getter @Setter private Integer orderType = 0;//订单类型 1. 拍卖 2.差价购

    /**
     * 交易订单id
     */
	@Getter @Setter private String orderId = "";
	
    /**
     * 收货地址ID
     */
	@Getter @Setter private Integer userShippingId = 0;
	
    /**
     * 详细地址
     */
	@Getter @Setter private String address = "";

    /**
     * 省
     */
	@Getter @Setter private String provinceName = "";

    /**
     * 市
     */
	@Getter @Setter private String cityName = "";

    /**
     * 县
     */
	@Getter @Setter private String districtName = "";


    /**
     * 收货人姓名
     */
	@Getter @Setter private String userName = "";

    /**
     * 收货人手机号
     */
	@Getter @Setter private String userPhone = "";
	
	
    /**
     * 商品图片
     */
	@Getter @Setter private String productPic = "";
	
    /**
     * 商品金额
     */
	@Getter @Setter private BigDecimal productPrice = new BigDecimal("0");
	
    /**
     * 开心币抵扣金额
     */
	@Getter @Setter private BigDecimal buyCoinMoney = new BigDecimal("0");

    /**
     * 实付款
     */
	@Getter @Setter private BigDecimal paidMoney = new BigDecimal("0");
	
	
	
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
	

	@Getter @Setter private Integer orderStatus = 0;
	
	@Setter private String company = "顺丰";
	public String getCompany() {
		if(!orderStatus.equals(6)){
			return "";
		}
		return company;
	}
	
	
	@Setter private String expressNo = "";
	public String getExpressNo() {
		if(!orderStatus.equals(6)){
			return "";
		}
		return expressNo;
	}
	
    /**
     * 付款时间
     */
	@Setter private Date buyerPayTime;
	
	public String getPayTimeStr() {
		if(buyerPayTime != null){
			return DateUtil.getDateFormat(buyerPayTime, "yyyy-MM-dd HH:mm:ss"); 
		}
		return "";
	}

	
    /**
     * 收货时间
     */
	@Setter private Date orderReceiveTime;
	
	public String getReceiveTimeStr() {
		if(orderReceiveTime != null){
			return DateUtil.getDateFormat(orderReceiveTime, "yyyy-MM-dd HH:mm:ss"); 
		}
		return "";
	}
	

    /**
     * 订单取消时间
     */
	@Setter private Date cancelTime;
	public String getCcancelTimeStr() {
		if(cancelTime != null){
			return DateUtil.getDateFormat(cancelTime, "yyyy-MM-dd HH:mm:ss"); 
		}
		return "";
	}

 
	@Getter @Setter private Integer provinceCode = 0;

	@Getter @Setter private String cityCode = "0";
  
	@Getter @Setter private String districtCode = "0";

	
    /**
     * 商品名称
     */
	@Getter @Setter private String productName = "";
	
	@Getter @Setter private Long countDown = 0L;
	
	 public String getPercent() {
	    	if(paidMoney == null){
	    		return "";
	    	}
	        BigDecimal r = (productPrice.subtract(paidMoney)).divide(productPrice, 4, BigDecimal.ROUND_HALF_EVEN);
	        DecimalFormat df = new DecimalFormat("#.00");
	        double dd = r.doubleValue();
	        if(dd <= 0.0){
	        	return  "0.00%";
	        }
	        return  df.format(r.doubleValue()*100) + "%";
	 }
	
	
	public static void main(String[] args) {
		OrderDetailVo vo = new OrderDetailVo();
		vo.setPaidMoney(new BigDecimal(46.2));
		vo.setProductPrice(new BigDecimal(6000));
		System.out.println(vo.getPercent());
	}

}