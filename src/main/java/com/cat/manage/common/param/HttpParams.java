package com.cat.manage.common.param;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cat.manage.common.exception.ParameterException;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * @Description: Http请求参数
 * @author 
 * @date 2015年11月25日 下午4:08:27
 */
public class HttpParams {
	
	private static final String PARAM_EXCEPTION_CODE = "1";
	
	private Map<String, String> innerMap = Maps.newHashMap();
	private HttpServletRequest request;
	
	/**
	 * 返回String 类型的值
	 */
	public String getStr(String name) {
		return assertNotNullOrEmpty(name);
	}
	
	/**
	 * 返回int值
	 */
	public int getInt(String name) {
		String value = assertNotNullOrEmpty(name);
		return Integer.valueOf(value);
	}
	
	/**
	 * 返回double值
	 */
	public double getDouble(String name) {
		String value = assertNotNullOrEmpty(name);
		return Double.valueOf(value);
	}
	
	/**
	 * 返回字符数组
	 */
	public String[] getStrArray(String name) {
		String[] arr = request.getParameterValues(name);
		
		if (arr == null)
			throw new ParameterException(PARAM_EXCEPTION_CODE, "参数 [" + name + "] 不能为空");
		
		return arr;
	}
	
	/**
	 * 返回整数数组
	 */
	public int[] getIntArr(String name) {
		String[] arr = request.getParameterValues(name);
		
		if (arr == null)
			throw new ParameterException(PARAM_EXCEPTION_CODE, "参数 [" + name + "] 不能为空");
		
		
		int[] iarr = new int[arr.length];
		for (int i = 0, len = arr.length; i < len; i++) {
			String val = arr[i];
			int ival = Integer.valueOf(val);
			iarr[i] = ival;
		}
		
		return iarr;
	}
	
	/**
	 * 返回参数Map
	 */
	public Map<String, String> getMap() {
		return innerMap;
	}
	
	/**
	 * 检测值不能为空
	 */
	private String assertNotNullOrEmpty(String name) {
		String value = innerMap.get(name);
		
		if (Strings.isNullOrEmpty(value))
			throw new ParameterException(PARAM_EXCEPTION_CODE, "参数 [" + name + "] 的值为空");
		
		return value;
	}
	
	/**
	 * 增加kv
	 */
	private void addParam(String name, String value) {
		innerMap.put(name, value);
	}
	
	/**
	 * 设置请求 
	 */
	private void setHttpServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	private HttpParams() {}
	
	/**
	 * 构造HttpParams对象
	 */
	public static HttpParams buildFrom(HttpServletRequest request) {
		HttpParams params = new HttpParams();
		params.setHttpServletRequest(request);
		
		Enumeration<String> paramsName = request.getParameterNames();
		
		while (paramsName.hasMoreElements()) {
			String name = paramsName.nextElement();
			String value = request.getParameter(name);
			
			params.addParam(name, value);
		}
		
		return params;
	}
}
