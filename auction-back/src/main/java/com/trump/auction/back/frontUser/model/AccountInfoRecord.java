package com.trump.auction.back.frontUser.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangyichao on 2017-12-20 下午 06:41.
 */
@Data
public class AccountInfoRecord implements Serializable {
	private static final long serialVersionUID = 1818496174885623431L;

	private Integer id;
	/*雪花算法 - 唯一记录号*/
	private String orderNo;
	private Integer userId;
	private String userPhone;
	/*本次交易拍币数量*/
	private Integer transactionCoin;
	/*交易类型(1:注册赠送；2:微信充值；3:支付宝充值 4:充值赠送；5:竞拍消费；6:订单拍币返回；7:取消差价购买)*/
	private Integer transactionType;
	/*交易名称*/
	private String transactionTag;
	/*币的数量*/
	private Integer coin;
	private Integer freezeCoin;
	private String productThumbnail;//缩略图
	private String productImage;
	private String remark;
	private Date createTime;
	private Date updateTime;
	private String orderId;
	private String orderSerial;//第三方流水号
	/*收支类型：1：收入；2：支出*/
	private Integer balanceType;
	/*类型：1：拍币；2：赠币；3：积分；4：购物币*/
	private Integer accountType;
	private String productName;//名称

	private Integer pageNum;
	private Integer pageSize;
	private String viewTransactionCoin;//展示用的字段，无数据库映射
}
