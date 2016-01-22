package com.cat.manage.common.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @Description: 将Key转换成小写
 * @author 
 * @date 
 */
@SuppressWarnings("rawtypes")
public class KeyNameUtil {
	
	/**
	 * 将数据中的key全部小写
	 */
	public static List<Map<String, Object>> lowercase(Object target) {
		List<Map<String, Object>> lowercaseResult = Lists.newArrayList();
		
		if (target != null)
			dispatch(target, lowercaseResult);
		
		return lowercaseResult;
	}
	
	/**
	 * 集合类型分发
	 */
	private static void dispatch(Object obj, List<Map<String, Object>> result) {
		if (obj instanceof List) {
			processList((List)obj, result);
			
		} else if (obj instanceof Map) {
			processMap((Map)obj, result);
			
		} else {
			processObject(obj, result);
		}
	}
	
	/**
	 * 处理列表数据
	 */
	private static void processList(List list, List<Map<String, Object>> container) {
		Iterator it = list.iterator();
		
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj != null)
				dispatch(obj, container);
		}
	}
	
	/**
	 * 处理Map数据
	 */
	private static void processMap(Map map, List<Map<String, Object>> container) {
		Map<String, Object> ret = Maps.newHashMap();
		
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			Object val = map.get(key);
			
			String lowerKey = new String((String)key);
			lowerKey = lowerKey.toLowerCase();
			ret.put(lowerKey, val);
		}
		
		container.add(ret);
	}
	
	/**
	 * 处理Bean对象
	 */
	private static void processObject(Object obj, List<Map<String, Object>> container) {
		try {
			Map oriMap = BeanUtils.describe(obj);
			processMap(oriMap, container);
			
		} catch (Exception e) {
			LOG.info("转换Bean["+obj+"]失败", e);
		}
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(KeyNameUtil.class);
}
