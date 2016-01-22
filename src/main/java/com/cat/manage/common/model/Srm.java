package com.cat.manage.common.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cat.manage.common.util.JsonUtil;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 标准的结果模型, 同StandardResultModel, 是StandardResultModel的缩写
 * @author 
 * @date 
 */
public class Srm {

	private String resultCode;
	private String resultMessage;
	private List<Object> results;
	private Map<String, Object> meta;
	
	public Srm() {
		results = new LinkedList<Object>();
		meta = new HashMap<String, Object>();
	}

	public String getResultCode() {
		return resultCode;
	}

	public Srm setResultCode(String resultCode) {
		this.resultCode = resultCode;
		return this;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public Srm setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
		return this;
	}
	
	public List<Object> getResults() {
		return results;
	}

	public Srm addResult(Object result) {
		results.add(result);
		return this;
	}
	
	public Srm addAll(Collection<? extends Object> coll) {
		results.addAll(coll);
		return this;
	}
	
	public Srm addMetaInfo(String key, Object value) {
	    meta.put(key, value);
	    return this;
	}

    /**
	 * 将PageInfo信息构建至Srm的meta数据中
	 */
	public Srm buildPageInfo(PageInfo<?> page) {
	    addMetaInfo("isPage", true);
	    addMetaInfo("page", page.getPageNum());
	    addMetaInfo("totalPage", page.getPages());
	    addMetaInfo("totalRecord", page.getTotal());
	    addMetaInfo("limit", page.getPageSize());
	    addAll(page.getList());
	    return this;
	}
	
    public Map<String, Object> getMeta() {
        return meta;
    }
	
	public String toString() {
		return JsonUtil.stringify(this);
	}
}
