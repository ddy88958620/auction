package com.trump.auction.order.util;

import com.cf.common.constant.Constant;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;


public class Base64Utils {

	/**
	 * 
	 * 创建日期2014-4-24上午10:12:38 修改日期 作者：
	 */
	public static String encodeStr(String plainText) {
		try {
			return new String(Base64.encodeBase64(
					plainText.getBytes(Constant.UTF8), true));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * 创建日期2014-4-24上午10:15:11 修改日期 作者：
	 */
	public static String decodeStr(String encodeStr) {
		try {
			return new String(Base64.decodeBase64(encodeStr
					.getBytes(Constant.UTF8)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	public static void main(String[] args) {
		System.out.println(Base64Utils.encodeStr("7"));
	}
}
