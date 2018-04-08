package com.trump.auction.web.util;

import java.util.UUID;

public class UUidUtil {
	
	public static String getUUid() {
		return UUID.randomUUID().toString().replaceAll("\\-", "");
	}
}
