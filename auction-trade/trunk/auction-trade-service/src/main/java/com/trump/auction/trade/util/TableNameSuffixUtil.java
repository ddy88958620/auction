package com.trump.auction.trade.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TableNameSuffixUtil {

	public static void main(String[] args) throws ParseException {

		String time1 = "20180302";
		String time2 = "20180307";
		String time3 = "201803014";
		String time4 = "201803018";
		String time5 = "201803026";
		String time6 = "201803030";

		List<String> currentList = Arrays.asList(time1, time2, time3, time4, time5, time6);
		for (String current : currentList) {
			System.out.println(current + ": " + getTableNameSuffix(current));
		}

		
	}

	/**
	 * <p>
	 * Title: 获取表名后缀
	 * </p>
	 * 
	 * @param currentDateStr
	 * @return
	 * @throws ParseException
	 */
	public static String getTableNameSuffix(String currentDateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		String suffix = "";
		try {
			Date date = sdf.parse(currentDateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			
			setTableNameDate(calendar);
			
			 suffix = sdf.format(calendar.getTime());
		} catch (Exception e) {
		}
		
		return suffix;
	}

	/**
	 * <p>
	 * Title: 获取当前表名后缀
	 * </p>
	 * 
	 * @param currentDateStr
	 * @return
	 * @throws ParseException
	 */
	public static String getCurrentTableNameSuffix(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();

		setTableNameDate(calendar);
		String suffix = "";
		try {
			 suffix = sdf.format(calendar.getTime());
		} catch (Exception e) {
		}
		
		return suffix;
	}

	private static void setTableNameDate(Calendar calendar) {
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		if (day < 6) {
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			return;
		}
		if (day <11) {
			calendar.set(Calendar.DAY_OF_MONTH, 6);
			return;
		}

		if (day < 16) {
			calendar.set(Calendar.DAY_OF_MONTH,11);
			return;
		}

		if ( day < 21) {
			calendar.set(Calendar.DAY_OF_MONTH, 16);
			return;
		}
		
		if ( day<  26) {
			calendar.set(Calendar.DAY_OF_MONTH, 21);
			return;
		}
		
		calendar.set(Calendar.DAY_OF_MONTH, 26);
	}

}
