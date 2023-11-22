package com.workshift.util;

import com.workshift.constant.CommonConstant;

public class StringUtil {

	private static final String BLANK_STRING = "";

    public static boolean isBlankOrNull(String value){
        return value == null || value.trim().equals(BLANK_STRING) ? true : false;
    }
    
    public static String trimString(String value){
    	if(StringUtil.isBlankOrNull(value)){
    		return value;
    	}else{
    		return value.trim();
    	}
    }
    
    public static String truncateString(String value, int length){
    	if(StringUtil.isBlankOrNull(value)){
    		return value;
    	}else if(value.length() <= length){
    		return value;
    	}else{
    		return value.trim().substring(0,length);
    	}
    }

	public static int getLenthByRemovingPercentageChar(String filterCharString) {
		filterCharString = filterCharString.replace(CommonConstant.PERCENTAGE_CHAR, CommonConstant.EMPTY_STRING);
		return filterCharString.length();
    }
	public static boolean isNumber(String data){
		boolean ret = true;
	    try {
	        Double.parseDouble(data);
	    }catch (NumberFormatException e) {
	        ret = false;
	    }
	    return ret;
	}

}
