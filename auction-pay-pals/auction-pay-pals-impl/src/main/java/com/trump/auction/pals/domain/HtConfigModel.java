package com.trump.auction.pals.domain;

import java.io.Serializable;

public class HtConfigModel implements Serializable {
	/** config表中富友的配置 */
	public static final String FUYOU = "FUYOU";
	/** config表中连连的配置 */
	public static final String LIANLIAN = "LIANLIAN";
	/** configParams表中益码通的配置 */
	public static final String YIMATONG = "YIMATONG";
	
	/** configParams表中支付宝的配置 */
	public static final String ALIPAY = "ALIPAY";
	
	/** configParams表中支付宝的配置 */
	public static final String WECHAT = "WECHAT";

	private Integer id;

	private String sysName;

	private String sysValue;

	private String sysValueBig;
	private String sysValueView;

	public String getSysValueView() {
		return sysValueView;
	}

	public void setSysValueView(String sysValueView) {
		this.sysValueView = sysValueView;
	}

	private String sysKey;

	private String sysType;

	private String inputType;

	private String remark;

	private String limitCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName == null ? null : sysName.trim();
	}

	public String getSysValue() {
		return sysValue;
	}

	public void setSysValue(String sysValue) {
		this.sysValue = sysValue == null ? null : sysValue.trim();
	}

	public String getSysKey() {
		return sysKey;
	}

	public void setSysKey(String sysKey) {
		this.sysKey = sysKey == null ? null : sysKey.trim();
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType == null ? null : sysType.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getLimitCode() {
		return limitCode;
	}

	public void setLimitCode(String limitCode) {
		this.limitCode = limitCode == null ? null : limitCode.trim();
	}

	public String getSysValueBig() {
		return sysValueBig;
	}

	public void setSysValueBig(String sysValueBig) {
		this.sysValueBig = sysValueBig == null ? null : sysValueBig.trim();
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	@Override
	public String toString() {
		return "HtConfig [id=" + id + ", inputType=" + inputType + ", limitCode=" + limitCode + ", remark=" + remark + ", sysKey=" + sysKey + ", sysName=" + sysName + ", sysType=" + sysType
				+ ", sysValue=" + sysValue + ", sysValueBig=" + sysValueBig + ", sysValueView=" + sysValueView + "]";
	}

}