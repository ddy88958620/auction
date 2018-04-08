package com.trump.auction.web.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class UserInfoVo {
	@Getter @Setter private Integer id;
	@Getter @Setter private String userType;
	@Getter @Setter private String userPhone;
	@Getter @Setter private String realName;
	@Getter @Setter private String idNumber;
	@Getter @Setter private String loginPassword;
	@Getter @Setter private String payPassword;
	@Getter @Setter private String wxNickName;
	@Getter @Setter private String wxOpenId;
	@Getter @Setter private String wxHeadImg;
	@Getter @Setter private String qqNickName;
	@Getter @Setter private String qqOpenId;
	@Getter @Setter private String qqHeadImg;
	@Getter @Setter private Date addTime;
	@Getter @Setter private String addIp;
	@Getter @Setter private Date updateTime;
	@Getter @Setter private String status;
	@Getter @Setter private String provinceName;
	@Getter @Setter private String cityName;
	@Getter @Setter private String headImg;
	@Getter @Setter private String nickName;
}
