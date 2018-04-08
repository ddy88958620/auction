package com.trump.auction.trade.pay.wechat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.Assert;

/**
 * <p>
 * Title: 
 * </p>
 * <p>
 * Description: 
 * </p>
 * 
 * @author yll
 * @date 2017年12月20日下午4:21:06
 */
public class EncryptUtil {

	public static String encodedByMD5(String source) {

		Assert.hasText(source,"source cannot null");

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] results = md.digest(source.getBytes());
			String result = bytesToHex(results);
			return result.toUpperCase();
		}
		catch (NoSuchAlgorithmException e) {

			throw new IllegalArgumentException(e);
		}

	}

	public static String bytesToHex(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0)
			return null;
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString().toUpperCase();
	}

	public static String encryptSHA1(String content) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(content.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用AES的CBC模式加密
	 *
	 * @param key 加密的秘钥
	 * @param text 待加密的内容
	 * @return 经过Base64编码的密文
	 */
	public static String encryptByAESWithCBC(byte[] key, byte[] text) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
			IvParameterSpec iv = new IvParameterSpec(key, 0, 16);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
			byte[] encrypted = cipher.doFinal(text);
			String base64Encrypted = new org.apache.commons.codec.binary.Base64().encodeToString(encrypted);
			return base64Encrypted;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param key 经Base64编码的AES秘钥
	 * @param text 经Base64编码的加密串
	 * @return
	 */
	public static byte[] decryptByAESWithCBC(byte[] key, byte[] text) {
		byte[] original = null;
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec key_spec = new SecretKeySpec(key, "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(key, 0, 16));
			cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
			// 使用BASE64对密文进行解码
			// byte[] encrypted = Base64.decodeBase64(text);
			// 解密
			original = cipher.doFinal(text);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return original;
	}

}
