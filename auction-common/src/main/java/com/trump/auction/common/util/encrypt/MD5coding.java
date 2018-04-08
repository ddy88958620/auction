package com.trump.auction.common.util.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

/**
 * MD5
 * 
 * @author gyh
 * 
 */
public class MD5coding {
	private static MD5coding instance;

	private MD5coding() {
	}

	public static MD5coding getInstance() {
		if (instance == null) {
			synchronized (MD5coding.class) {
				if (instance == null)
					instance = new MD5coding();
			}
		}
		return instance;
	}

	public String code(String str) {
		String hs = "";
		try {
			MessageDigest alga;
			String myinfo = str;
			alga = MessageDigest.getInstance("MD5");
			alga.update(myinfo.getBytes());
			byte[] digesta = alga.digest();
			String stmp = "";
			for (int n = 0; n < digesta.length; n++) {
				stmp = (Integer.toHexString(digesta[n] & 0XFF));
				if (stmp.length() == 1)
					hs = hs + "0" + stmp;
				else
					hs = hs + stmp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hs.toUpperCase();
	}

	/**
	 * MD5加密 32位小写
	 * 
	 * @param inStr
	 * @return
	 */
	public String encrypt(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] byteArray = inStr.getBytes("utf-8");
			byte[] md5Bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws ParseException, NoSuchAlgorithmException {
		System.out.println(MD5coding.getInstance().encrypt(AESUtil.getInstance().encrypt("111111", "")));
	}
}
