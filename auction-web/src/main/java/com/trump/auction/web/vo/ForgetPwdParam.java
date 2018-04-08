package com.trump.auction.web.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * Title:忘记密码分装类
 * </p>
 * 
 * @author yll
 * @date 2017年12月25日下午5:14:39
 */
@ToString
public class ForgetPwdParam {
	@Getter @Setter String userPhone;
	@Getter @Setter String smsCode;
}
