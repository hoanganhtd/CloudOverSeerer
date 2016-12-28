/**
 * HoangAnh
 * Dec 21, 2016
 * 
 */
package com.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.core.configs.AppConstants;

/**
 * @author HoangAnh
 *
 */
public class DataUtils {
	
	
	public static boolean validate(String inputDate, String formatDate) {
		try {
			if (formatDate == null || "".equals(formatDate)) return validate(inputDate);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
			simpleDateFormat.parse(inputDate);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean validate(String inputDate) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.APP_FORMAT_DATE);
			simpleDateFormat.parse(inputDate);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static Date parseDate(String inputDate, String formatDate) {
		try {
			if (formatDate == null || "".equals(formatDate)) return parseDate(inputDate);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
			return simpleDateFormat.parse(inputDate);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date parseDate(String inputDate) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.APP_FORMAT_DATE);
			return simpleDateFormat.parse(inputDate);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String formatDate(Date date) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.APP_FORMAT_DATE);
			return simpleDateFormat.format(date);
		} catch (Exception e) {
			return null;
		}
	}
}
