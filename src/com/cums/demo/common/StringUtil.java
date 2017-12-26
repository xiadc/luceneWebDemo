/* @(#)
 *
 * Project:DWZ-MyBatis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   ShuangPan      2013-9-18        first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2013 China UMS All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       China UMS ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with China UMS.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.cums.demo.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 工具类 <br>
 * 创建日期：2013-9-18 <br>
 * <b>Copyright 2013　银联商务有限公司　All Rights Reserved</b>
 * 
 * @author 潘爽
 * @since 1.0
 * @version 1.0
 */
public class StringUtil {

	/** 时间Format(yyyy-MM-dd HH:mm:ss). */
	public static final String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

	/** 时间Forma(yyyy-MM-dd). */
	public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
	/**
	 * 日期格式化(yyyymmdd)
	 */
	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	
	/** 时间Forma(yyyy/M/d). */
	public static final String DATE_FORMAT_CSV = "yyyy/M/d";
	
	public static final String emailReg = "^[a-zA-Z0-9]+([._\\-]*[a-zA-Z0-9])*@([a-zA-Z0-9]+[-a-zA-Z0-9]*[a-zA-Z0-9]+.){1,63}[a-zA-Z0-9]+$";
	public static final String mobileNoReg = "^1[0-9]{10}$";
	public static final String docCodeReg = "^(\\d{17}([0-9]|X|x))|(\\d{15})$";
	public static final String telephoneReg="^(([0-9]{3}[-][0-9]{8})|([0-9]{4}[-][0-9]{7,8}))$";
	public static final String imeiReg="^[0-9]{0,32}$";
	public static final String bankNoReg="^[0-9]{0,32}$";
	public static final String ipReg="^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$";
	public static final String macReg="^([0-9A-Z]{2})-([0-9A-Z]{2})-([0-9A-Z]{2})-([0-9A-Z]{2})-([0-9A-Z]{2})-([0-9A-Z]{2})|(([0-9A-Z]{2}):([0-9A-Z]{2}):([0-9A-Z]{2}):([0-9A-Z]{2}):([0-9A-Z]{2}):([0-9A-Z]{2}))$";
	public static final String legDocCodeReg="^(\\d{17}([0-9]|X|x))|(\\d{15})$";
	public static final String orgCodeReg="^[0-9]{8}[\\-][0-9A-Za-z]$";
	public static final String busLicCodeReg="^\\d{15}$";
	public static final String taxRegCerReg="^([0-9]{15})|([0-9]{20})$";
	public static final String icpReg="^\\d{0,16}$";
	public static final String serverIpReg="^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$";
	public static final String urlReg="^(http:\\/\\/[a-zA-Z0-9._]{0,2041})|(https:\\/\\/[a-zA-Z0-9._]{0,2040})$";
	public static final String socialUnityCreditCodeReg = "^[0-9A-Za-z]{18}$";

	public static boolean isEmail(String email) {  
        return Pattern.matches(emailReg, email);  
    } 
	public static boolean isMobileNo(String mobileNo) {  
        return Pattern.matches(mobileNoReg, mobileNo);  
    } 
	public static boolean isDocCode(String docCode) {  
        return Pattern.matches(docCodeReg, docCode);  
    } 
	public static boolean isTelephone(String telephone) {  
        return Pattern.matches(telephoneReg, telephone);  
    } 
	public static boolean isImei(String imei) {  
        return Pattern.matches(imeiReg, imei);  
    } 
	public static boolean isBankNo(String bankNo) {  
        return Pattern.matches(bankNoReg, bankNo);  
    } 
	public static boolean isIp(String ip) {  
        return Pattern.matches(ipReg, ip);  
    } 
	public static boolean isMac(String mac) {  
        return Pattern.matches(macReg, mac);  
    } 
	public static boolean isLegDocCode(String legDocCode) {  
        return Pattern.matches(legDocCodeReg, legDocCode);  
    } 
	public static boolean isOrgCode(String orgCode) {  
        return Pattern.matches(orgCodeReg, orgCode);  
    } 
	public static boolean isBusLicCode(String busLicCode) {  
        return Pattern.matches(busLicCodeReg, busLicCode);  
    } 
	public static boolean isTaxRegCer(String taxRegCer) {  
        return Pattern.matches(taxRegCerReg, taxRegCer);  
    } 
	public static boolean isIcp(String icp) {  
        return Pattern.matches(icpReg, icp);  
    } 
	public static boolean isServerIp(String serverIp) {  
        return Pattern.matches(serverIpReg, serverIp);  
    } 
	public static boolean isUrl(String url) {  
        return Pattern.matches(urlReg, url);  
    } 
	public static boolean isSocialUnityCreditCode(String socialUnityCreditCode) {  
        return Pattern.matches(socialUnityCreditCodeReg, socialUnityCreditCode);  
    } 
	
	/**
	 * 判断对象（也可能是字符串）是否为空 <br>
	 * <b>作者： 潘爽</b> <br>
	 * 创建时间：2013-9-18 上午11:59:32
	 * 
	 * @since 1.0
	 * @param obj
	 *            要判断的对象
	 * @return true-是；false-否
	 */
	public static boolean isNullorEmpty(Object obj) {
		if (obj == null || "".equals(obj.toString().trim()))
			return true;
		return false;
	}

	/**
	 * 判断对象（也可能是字符串）是否不为空 <br>
	 * <b>作者： 潘爽</b> <br>
	 * 创建时间：2013-9-18 下午5:59:14
	 * 
	 * @since 1.0
	 * @param obj
	 *            要判断的对象
	 * @return true-是；false-否
	 */
	public static boolean notNullorEmpty(Object obj) {
		if (obj != null && !"".equals(obj.toString().trim()))
			return true;
		return false;
	}

	/**
	 * 字符串数字转换为数值数字 <br>
	 * <b>作者： 潘爽</b> <br>
	 * 创建时间：2013-9-18 下午6:03:46
	 * 
	 * @since 1.0
	 * @param obj
	 *            要转换的数字对象
	 * @return 数值
	 */
	public static int aToi(Object obj) {
		if (notNullorEmpty(obj))
			return Integer.parseInt(obj.toString());
		throw new RuntimeException("数值转换的对象为空");
	}

	/**
	 * 对象转换为JSON格式的字符串 <br>
	 * <b>作者： 潘爽</b> <br>
	 * 创建时间：2013-9-23 上午10:10:19
	 * 
	 * @since 1.0
	 * @param object
	 *            要转换为JSON格式的对象
	 * @return JSON格式字符串
	 */
	public static String objectToJson(Object object) {
		return JSONObject.fromObject(object).toString();
	}

/*	*//**
	 * 对象转换为JSON格式
	 * @param obj 待转换的对象
	 * @param excludes 对象中需要排除的属性
	 * @param jsonDateProcesssor 日期处理器
	 * @return
	 *//*
	public static JSONObject objectToJson(Object obj, String[] excludes, JsonDateProcessor jsonDateProcessor){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, jsonDateProcessor);
		jsonConfig.setExcludes(excludes);
		JSONObject jsonData = JSONObject.fromObject(obj, jsonConfig);
		return jsonData;
	}*/
	
	/**
	 * 集合转换为JSON字符串 <br>
	 * <b>作者： 潘爽</b> <br>
	 * 创建时间：2013-10-14 上午11:25:46
	 * 
	 * @since 1.0
	 * @param object
	 *            集合信息
	 * @return JSON字符串
	 */
	public static String listToJson(Object object) {
		return JSONArray.fromObject(object).toString();
	}

	/**
	 * 获得UUID信息 <br>
	 * <b>作者： 潘爽</b> <br>
	 * 创建时间：2013-10-16 上午10:49:44                                                                                                                                                                                                          
	 * 
	 * @since 1.0
	 * @return UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

	/**
	 * 获得字符串的MD5格式 <br>
	 * <b>作者： 潘爽</b> <br>
	 * 创建时间：2013-10-16 上午11:00:33
	 * 
	 * @since 1.0
	 * @param source
	 *            源字符串
	 * @return MD5后的字符串
	 */
	public static String md5(String source) {
		return DigestUtils.md5Hex(source);
	}

	/**
	 * 获取字符串的字节长度 <br>
	 * <b>作者： 朱海斌</b> <br>
	 * 创建时间：2014-10-24 上午16:30:33
	 * 
	 * @since 1.0
	 * @param source
	 *            源字符串
	 * @return int
	 */
	public static int getWordCount(String source) {
		if (source == null) {
			return -1;
		}
		source = source.replaceAll("[^\\x00-\\xff]", "**");
		int length = source.length();
		return length;
	}

	/**
	 * 日期格式变换：将日期格式变换为字符串<br>
	 * <br>
	 * 对象为NULL的情况下 返回空字符串
	 * 
	 * @param date
	 *            需要变换的日期格式
	 * @param format
	 *            指定的转化为的日期字符串格式（SimpleDateFormat使用）
	 * @return 变换后的日期字符串
	 * @see java.text.SimpleDateFormat
	 */
	public static String convertDateToString(final Date date,
			final String format) {

		if (null == date) {
			return "";
		}

		String defaultFormat = DATE_FORMAT_YMDHMS;
		if (null != format && !"".equals(format)) {
			defaultFormat = format;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(defaultFormat);
		return formatter.format(date);
	}

	/**
	 * 日期格式变换：字符串转换为日期<br />
	 * 
	 * @param targetDate
	 *            需要变化的字符串
	 * @param format
	 *            指定的转化为的日期字符串格式（SimpleDateFormat使用）
	 * @return 变换后的java日期类型对象
	 * @throws Exception
	 *             转化时发生的异常
	 */
	public static Date convertStringToDate(final String targetDate,
			final String format) {

		if (null == targetDate || "".equals(targetDate)) {
			return null;
		}

		String defaultFormat = DATE_FORMAT_YMDHMS;
		if (null != format && !"".equals(format)) {
			defaultFormat = format;
		}

		SimpleDateFormat dtm = new SimpleDateFormat(defaultFormat);

		Date dt = null;
		try {
			dt = dtm.parse(targetDate);
		} catch (ParseException e) {
			return null;
		}
		return dt;
	}

	/**
	 * 数字转换成字符串(四舍五入)
	 * 
	 * @param num
	 *            long数字
	 * @param precision
	 *            小数点后精度
	 * @return 转换后的字符串
	 */
	public static String convertNumToStr(long num, int precision) {
		if(isNullorEmpty(num)){
			return "0";
		}
		return convertNumToStr(new BigDecimal(num), precision);
	}

	/**
	 * 数字转换成字符串(四舍五入)
	 * 
	 * @param num
	 *            double数字
	 * @param precision
	 *            小数点后精度
	 * @return 转换后的字符串
	 */
	public static String convertNumToStr(double num, int precision) {
		if(isNullorEmpty(num)){
			return "0";
		}
		return convertNumToStr(new BigDecimal(num), precision);
	}

	/**
	 * 数字转换成字符串(四舍五入)
	 * 
	 * @param num
	 *            BigDecimal数字
	 * @param precision
	 *            小数点后精度
	 * @return 转换后的字符串
	 */
	public static String convertNumToStr(BigDecimal num, int precision) {
		
		if(isNullorEmpty(num)){
			return "0";
		}

		StringBuffer sb = new StringBuffer("0");
		if (precision > 0) {
			sb.append(".");
		}

		while (precision > 0) {
			sb.append("0");
			precision--;
		}
		DecimalFormat format = new DecimalFormat(sb.toString());
		return format.format(num);
	}

	/**
	 * 数字转换成字符串(四舍五入)
	 * 
	 * @param num
	 *            String数字
	 * @param precision
	 *            小数点后精度
	 * @return 转换后的字符串
	 */
	public static String convertNumToStr(String num, int precision) {
		
		if(isNullorEmpty(num)){
			return "0";
		}
		
		if (!isNumber(num)) {
			return "";
		}
		return convertNumToStr(new BigDecimal(num), precision);
	}
	
	/**
	 * 判断是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if(StringUtils.isBlank(str)){
			return false;
		}
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * 判断字符串是否是日期(格式:YYYY/M/D)
	 * @param str
	 * @return boolean true-是
	 * @author xuyang.xu
	 * @date 2016-3-4 上午11:39:01
	 */
	public static boolean isCsvDate(String str) {
		SimpleDateFormat dtm = new SimpleDateFormat(DATE_FORMAT_CSV);
		dtm.setLenient(false);
		try {
			Date dt = dtm.parse(str);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断字符串是否是日期(格式:YYYY-MM-DD)
	 * @param str
	 * @return boolean true-是
	 * @author xuyang.xu
	 * @date 2016-3-4 上午11:39:01
	 */
	public static boolean isExcelDate(String str) {
		SimpleDateFormat dtm = new SimpleDateFormat(DATE_FORMAT_YMD);
		dtm.setLenient(false);
		try {
			Date dt = dtm.parse(str);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * HTML特殊字符处理
	 * 
	 * @param str
	 * @return
	 */
	public static String escape(String str) {
		if (str == null || str.equals("")) {
			return "";
		}
		return str.replace("<", "&lt;").replace(">", "&gt;");
	}

	/**
	 * 对象转换成字符串.<br/>
	 * 
	 * @param obj
	 *            对象
	 * @return
	 */
	public static String converObjToString(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}
	
	/**
	 * 通过机构的organize_bm截取后00
	 * @param orgBm 机构编号
	 * @return 截取后 00的机构编号
	 */
	public static String supOrgBm(String orgBm){
		if(orgBm!=null&&!orgBm.equals("")){
			boolean fals = true;
			String org = orgBm.substring(orgBm.length()-2);
			while (org.equals("00")) {
				orgBm = orgBm.substring(0,orgBm.length()-2);
				org = orgBm.substring(orgBm.length()-2);
			}
		}else{
			orgBm = "";
		}
		return orgBm;
	}
	
	public static String subStringDate(String date){
		return date.substring(0, date.lastIndexOf(" "));
	}
		


	/**
	 * 用于AJAX传来的中文参数unicode解码
	 * @param dataStr
	 * @return 返回解码后的字符串
	 * @author xuyang.xu
	 */
	public static String Unicode2GBK(String dataStr) {
		if (dataStr == null || "".equals(dataStr)) {
			return dataStr;
		}
		int index = 0;

		StringBuffer buffer = new StringBuffer();

		while (index < dataStr.length()) {
			try {
				if (!"\\u".equals(dataStr.substring(index, index + 2))) {
					buffer.append(dataStr.charAt(index));
					index++;
					continue;
				}
			} catch (Exception e) {
				for (int j = index; j < dataStr.length(); j++) {
					buffer.append(dataStr.charAt(index));
				}
				return buffer.toString();
			}
			String charStr = "";
			charStr = dataStr.substring(index + 2, index + 6);
			char letter = (char) Integer.parseInt(charStr, 16);
			buffer.append(letter);
			index += 6;
		}
		return buffer.toString();
	}
	
	/**
	 * 将json格式的字符串解析成Map对象 <li>
     * json格式：{"name":"admin","retries":"3fff","testname":"ddd","testretries":"fffffffff"}
	 * @author xuyang.xu
	 * @date 2015-11-18 下午3:52:26
	 */
    public static HashMap<String, Object> jsonToHashMap(Object object)
    {
        HashMap<String, Object> data = new HashMap<String, Object>();
        // 将json字符串转换成jsonObject
        JSONObject jsonObject = JSONObject.fromObject(object);
        Iterator<?> it = jsonObject.keys();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext())
        {
            String key = String.valueOf(it.next());
            Object value = jsonObject.get(key);
            data.put(key, value);
        }
        return data;
    }
    
   /**
    * 转换日期格式,从YYYY-MM-DD转换成YYYYMMDD,如果日期非法则返回空字符串<p>
    * 主要用于前台页面接收日期控件的值转换
    * @param dateStr 待转换的日期字符串 YYYY-MM-DD
    * @return
    * String
    * @author xuyang.xu
    * @date 2015-12-29 下午5:58:47
    */
   public static String convertDateStrToDateStr(String dateStr){
	   String resultDate="";
	   if(StringUtils.isBlank(dateStr)) return resultDate;
	   Date idate=convertStringToDate(dateStr, DATE_FORMAT_YMD);
	   if(idate != null){
		   resultDate=convertDateToString(idate, DATE_FORMAT_YYYYMMDD);
	   }
	   return resultDate;
   }
   
   /**
    * 验证是否是弱密码,正则表达式验证
    * @param passwd 明文
    * @return true-弱密码
    * @author xuyang.xu
    * @date 2016-2-25 下午6:09:23
    */
   public static boolean validateWeakPassword(String passwd){
		if(StringUtils.isBlank(passwd) || passwd.length()<8){
			return true;
		}
	   //小写字母
	   Pattern p_lW = Pattern.compile("[a-z]");
	   //大写字母
	   Pattern p_uW = Pattern.compile("[A-Z]");
	   //数字
	   Pattern p_nW = Pattern.compile("[0-9]");
	   //特殊字符
	   Pattern p_sW = Pattern.compile("[~!@#$^*()=|{}:;+,\\[\\].<>/?~！@#￥……&*（）—|{}【】‘；：”“。，、？]");
	   boolean m_lW=false,m_uW=false,m_nW=false,m_sW=false;
	   for (int i = 0; i < passwd.length(); i++) {
		   char array_element = passwd.charAt(i);
		   if(p_lW.matcher(array_element+"").matches()){
			   m_lW=true;
		   }if(p_uW.matcher(array_element+"").matches()){
			   m_uW=true;
		   }if(p_nW.matcher(array_element+"").matches()){
			   m_nW=true;
		   }if(p_sW.matcher(array_element+"").matches()){
			   m_sW=true;
		   }
	   }
       if((m_lW && m_uW && m_nW) || (m_lW && m_uW && m_sW) 
    		   || (m_lW && m_nW && m_sW) || (m_uW && m_nW && m_sW)
    		   || (m_lW && m_uW && m_nW && m_sW)){
        	return false;
       }else{
    	   return true;
       }
   }
   
   /**
    * 验证字符串格式
    * @param checkVal
    * @param len 限制长度
    * @param str_range 可选值范围
    * @return
    * boolean
    * @author xuyang.xu
    * @date 2016-3-8 上午9:56:37
    */
   public static boolean matchValueRangeLen(String checkVal,int len,String str_range){
	   boolean flag=false;
	   if(StringUtils.isBlank(str_range)){
		   return flag;
	   }
	   String reg_str="["+str_range+"]{"+len+"}";
	   Pattern p = Pattern.compile(reg_str);
       Matcher m = p.matcher(checkVal);
       return m.matches();
   }
   
   /**
    * 计算一个字符在给定的字符串中出现的次数
    * @param str 字符串
    * @param c 字符
    * @return 返回 字符 出现的次数
    * @author xuyang.xu
    * @date 2016-3-8 下午3:54:49
    */
   public static int counter(String str,char c){
	   int count=0;
	   if(StringUtils.isNotBlank(str)){
		   for(int i=0;i<str.length();i++){
			   if(str.charAt(i)==c){
				   count++;
			   }
		   }
	   }
	   return count;
	  }
   
   /**
    * 日期相加减
    * @param dateStr 日期格式yyyyMMDD
    * @param days +1/-1
    * @return 返回日期格式yyyyMMDD
    * String
    * @author xuyang.xu
    * @date 2016-3-9 下午8:46:50
    */
   public static String addDate(String dateStr,int days){
	   String newDate="";
	   if(StringUtils.isBlank(dateStr)){
		   return newDate;
	   }
	   Calendar cal=Calendar.getInstance();
	   cal.setTime(convertStringToDate(dateStr,DATE_FORMAT_YYYYMMDD));
	   cal.add(Calendar.DAY_OF_YEAR,days);
	   newDate=convertDateToString(cal.getTime(), DATE_FORMAT_YYYYMMDD);
	   return newDate;
   }
/**
 * 给日期增加小时
 * @param date
 * @param hour
 * @return
 * Date
 * @author xuyang.xu
 * @date 2017-7-12 下午3:53:06
 */
   public static Date addHour(Date date,int hour){
	   if(date==null){
		   return null;
	   }
	   Calendar cal=Calendar.getInstance();
	   cal.setTime(date);
	   cal.add(Calendar.HOUR_OF_DAY,hour);
	   return cal.getTime();
   }
   
public static String formatNumber(int xmlNo, int length) {
	String result = "";
	String no = String.valueOf(xmlNo);
	if(no.length()>length){
		result = no.substring(no.length()-length);
	} else {
		int lack = length-no.length();
		while(lack-- > 0){
			result += "0";
		}
		result += no;
	}
	return result;
}

/**
 * 分转换成元,适用于string存放的数值
 * @param numberStr
 * @return
 * String
 * @author xuyang.xu
 * @date 2016-12-6 上午9:40:49
 */
public static String StringPointToYuanStr(String numberStr) {
	String result = "0";
	if(StringUtils.isBlank(numberStr) || !isNumber(numberStr)){
		return result;
	}
	int pos=numberStr.indexOf(".");
	if(numberStr.length()>9){
		if(-1 == pos){
			result=numberStr.substring(0,numberStr.length()-2)+"."+numberStr.substring(numberStr.length()-2, numberStr.length()).replace(".", "");
		}else{
			if(pos==1 || pos == 0){
				result="0.0"+numberStr.replace(".", "");
			}else if(pos ==2 ){
				result="0."+numberStr.replace(".", "");
			}else{
				result=numberStr.substring(0,pos-2)+"."+numberStr.substring(pos-2, numberStr.length()).replace(".", "");
			}
		}
	}else{
		BigDecimal integer=new BigDecimal(numberStr);
		integer=integer.divide(new BigDecimal(100));
		result=integer.toPlainString();
	}
	return result;
}

	/**
	 * 获取两个日期之间的相差天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getBetweenDays(Date date1, Date date2){
		int days = 0;
		days = (int) ((date1.getTime() - date2.getTime()) / (1000*3600*24));
		return days;
	}
}
