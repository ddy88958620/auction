package com.trump.auction.back.sys.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Config implements Serializable {
	public enum inputTypeEnum {
		text, textarea, password, image, textdomain;
	}

	private Integer id;

	private String sysName;

	private String sysValue;

	private String sysValueBig;
	private String sysValueView;

	private String sysKey;

	private String sysType;

	private String inputType;

	private String remark;

	private String limitCode;

	/**
	 * 自动匹配，不用关心类型 但是InputType必须有值
	 * 
	 * @param value
	 */
	public void setSysValueAuto(String value) {

		if (inputTypeEnum.textarea.name().equals(this.getInputType())) {
			this.setSysValueBig(value);
		} else if (inputTypeEnum.text.name().equals(this.getInputType())) {
			this.setSysValue(value);
		} else if (inputTypeEnum.password.name().equals(this.getInputType())) {
			this.setSysValue(value);
		} else if (inputTypeEnum.image.name().equals(this.getInputType())) {
			this.setSysValue(value);
		} else if (inputTypeEnum.textdomain.name().equals(this.getInputType())) {
            this.setSysValueBig(value);
        }
	}

	/**
	 * 自动匹配，不用关心类型 但是InputType必须有值
	 * 
	 * @param
	 */
	public String getSysValueAuto() {
		String sysValueAuto = null;
		if (inputTypeEnum.textarea.name().equals(this.getInputType())) {
			sysValueAuto = this.getSysValueBig();
		} else if (inputTypeEnum.text.name().equals(this.getInputType())) {
			sysValueAuto = this.getSysValue();
		} else if (inputTypeEnum.password.name().equals(this.getInputType())) {
			sysValueAuto = this.getSysValue();
		} else if (inputTypeEnum.image.name().equals(this.getInputType())) {
			sysValueAuto = this.getSysValue();
		} else if (inputTypeEnum.textdomain.name().equals(this.getInputType())) {
            sysValueAuto = this.getSysValueBig();
        }
		// fyc20170705注释，必须这么写否则项目启动时调用hmset方法map值为null会报错
		return sysValueAuto == null ? "" : sysValueAuto;
	}

}
