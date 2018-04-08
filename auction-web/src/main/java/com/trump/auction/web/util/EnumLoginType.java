package com.trump.auction.web.util;

public enum EnumLoginType {

	Mobile(1),
	WX(2),
	QQ(3);
	
	EnumLoginType(Integer type){
		this.type = type;
	}
	private Integer type;
	public Integer getType() {
		return type;
	}
	
}
