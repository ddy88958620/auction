package com.trump.auction.cust.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 返回两个时间中相差天数
	 * 
	 * @return
	 */
	public static int getDay(Date date, Date date2) {
		if (null == date || null == date2) {
			return 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		calendar2.set(Calendar.HOUR_OF_DAY, 0);
		calendar2.set(Calendar.SECOND, 0);
		calendar2.set(Calendar.MINUTE, 0);
		return (int) ((calendar.getTime().getTime() - calendar2.getTime().getTime()) / 1000 / 60 / 60 / 24);
	}

	/**
	 * 当前时间减去指定天数
	 * 
	 * @return
	 */
	public static Date getMinusDate(Date date, int day) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - day);
		return calendar.getTime();
	}

	/**
	 * 返回指定日期时间
	 * 
	 * @return
	 */
	public static String getFormatDate(Date date) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat smformat = new SimpleDateFormat("yyyy-MM-dd");
		return smformat.format(date);
	}

	/**
	 * 获取当前时间 format 格式
	 *
	 * @return
	 */
	public static String getDateFormat(String format) {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(format);
		String time = df.format(date);
		return time;
	}
}
