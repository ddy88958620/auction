package com.trump.auction.web.vo;

import org.apache.commons.lang3.StringUtils;

import com.trump.auction.cust.enums.UserLoginTypeEnum;
import com.trump.auction.cust.model.UserInfoModel;
import com.trump.auction.web.util.Base64Utils;

public class UserSupport {

	public static String getBase64UserNameByLoginType(UserInfoModel userInfoModel) {
		if (StringUtils.isNotBlank(userInfoModel.getNickName())) {
			return userInfoModel.getNickName();
		}
		if (userInfoModel.getLoginType().equals(UserLoginTypeEnum.LOGIN_TYPE_PHONE.getType())) {
			return Base64Utils.encodeStr(userInfoModel.getUserPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
		}

		if (userInfoModel.getLoginType().equals(UserLoginTypeEnum.LOGIN_TYPE_QQ.getType())) {
			return userInfoModel.getQqNickName();
		}

		if (userInfoModel.getLoginType().equals(UserLoginTypeEnum.LOGIN_TYPE_WX.getType())) {
			return userInfoModel.getWxNickName();
		}
		return "";
	}

	public static String getHeadImgByLoginType(UserInfoModel userInfoModel) {
		String headImg = "";
		if (userInfoModel.getLoginType().equals(UserLoginTypeEnum.LOGIN_TYPE_QQ.getType())) {
			headImg = userInfoModel.getQqHeadImg();
		}
		if (userInfoModel.getLoginType().equals(UserLoginTypeEnum.LOGIN_TYPE_WX.getType())) {
			headImg = userInfoModel.getWxHeadImg();
		}
		return headImg;
	}
}
