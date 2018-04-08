package com.trump.auction.pals.api.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by IntelliJ IDEA
 * YhInfo : zhangsh
 * Date : 2016/12/26 0026
 * Time : 18:00
 */
public class MobilePaySendModel implements Serializable{
	private static final long serialVersionUID = -8221711996982670198L;
	private String BACKURL;
    private String HOMEURL;
    private String REURL;
	public String getBACKURL() {
		return BACKURL;
	}
	public void setBACKURL(String bACKURL) {
		BACKURL = bACKURL;
	}
	public String getHOMEURL() {
		return HOMEURL;
	}
	public void setHOMEURL(String hOMEURL) {
		HOMEURL = hOMEURL;
	}
	public String getREURL() {
		return REURL;
	}
	public void setREURL(String rEURL) {
		REURL = rEURL;
	}
    
}
