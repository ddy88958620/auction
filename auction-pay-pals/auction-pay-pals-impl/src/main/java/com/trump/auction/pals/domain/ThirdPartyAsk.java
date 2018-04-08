package com.trump.auction.pals.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ThirdPartyAsk {
	private Integer id;
	private String userId;
	private Integer assetOrderId;
	private String orderType;
	private String orderNo;
	private String act;
	private String reqParams;
	private String returnParams;
	private Date notifyTime;
	private String notifyParams;
	private Date addTime;
	private String addIp;
	private Date updateTime;
	private Integer status;
	private String tableLastName;//表面后缀

	@Override
	public String toString() {
		return "RiskOrders [userId=" + userId + ", orderType=" + orderType + ", act=" + act + ", addIp=" + addIp + ", addTime=" + addTime + ", id=" + id + ", notifyParams=" + notifyParams + ", notifyTime=" + notifyTime + ", orderNo=" + orderNo + ", reqParams=" + reqParams + ", returnParams=" + returnParams + ", status=" + status + ", updateTime=" + updateTime + ", tableLastName=" + tableLastName + "]";
	}

}
