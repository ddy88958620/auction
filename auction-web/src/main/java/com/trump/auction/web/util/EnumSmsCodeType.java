package com.trump.auction.web.util;

public enum EnumSmsCodeType {
	Register(1,"Register"),
	ForgetPwd(2,"ForgetPwd"),
	CheckPhone(3,"CheckPhone"),
	BindPhone(4,"BindPhone"),
	SmsLogin(5,"SmsLogin");
	
	
		
	private final Integer type;
	public Integer getType() {
		return type;
	}
	private final String	text;
	public String getText() {
		return text;
	}
	EnumSmsCodeType(Integer type,String text){
		this.type = type;
		this.text = text;
	}
    public static String getTypeText(Integer type) {
        String text = null;
        for (EnumSmsCodeType codeType : values()) {
            if (type.equals(codeType.getType())) {
                text = codeType.getText();
                break;
            }
        }
        return text;
    }
}
