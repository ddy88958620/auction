package com.trump.auction.web.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class LoginInfo {
	@Getter @Setter private String userPhone;
	@Getter @Setter private String loginPassword;
	@Getter @Setter private String province;
	@Getter @Setter private String city;
}
