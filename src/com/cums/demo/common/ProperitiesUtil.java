package com.cums.demo.common;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class ProperitiesUtil {

	
	/**读取properties文件内容
	 * @param propertiesPath
	 * @return
	 */
	public static Map<String,String>  getProperities(String propertiesPath) {
		Map<String,String> map= new HashMap<String,String>();
		Properties prop = new Properties();
		try {
			// 读取属性文件a.properties
			InputStream in = ProperitiesUtil.class.getClassLoader().getResourceAsStream(propertiesPath);
			InputStream bin = new BufferedInputStream(in);
			prop.load(bin); // /加载属性列表
			Iterator<String> it = prop.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = prop.getProperty(key);
				map.put(key, value);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
