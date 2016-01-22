package com.cat.manage.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonUtil {
	
	private JsonUtil() {}
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 将对象转化为Json字符串
	 */
	public static String stringify(Object obj) {
		String jsonStr = "";
		
		try {
			jsonStr = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			jsonStr = "";
			LOG.error("", e);
		}
		
		return jsonStr;
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);
}
