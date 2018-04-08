package com.trump.auction.web.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * Title:登陆参数封装 
 * </p>
 * 
 * @author yll
 * @date 2017年12月25日下午5:14:39
 */
@ToString
public class RegisterParam {
	@Getter @Setter private String userPhone;
	@Getter @Setter private String smsCode;
	@Getter @Setter private String password;
	@Getter @Setter private String province;
	@Getter @Setter private String city;
	
}
