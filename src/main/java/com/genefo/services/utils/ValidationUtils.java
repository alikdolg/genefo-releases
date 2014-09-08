package com.genefo.services.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Alexey
 *
 */
public class ValidationUtils {

	public static boolean isEMailValid(String email) {
		//String regexp = "^([a-zA-Z0-9\\-\\.\\_]+)'+'(\\@)([a-zA-Z0-9\\-\\.]+)'+'(\\.)([a-zA-Z]{2,4})$";
		String regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		Pattern email_pattern = Pattern.compile(regexp);
		Matcher m = email_pattern.matcher(email);
		if(m.find()) {
			return true;
		}
		return false;
	}

}
