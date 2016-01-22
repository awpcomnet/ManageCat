package com.cat.manage.common.model;

import java.util.List;

import com.cat.manage.common.util.JsonUtil;


/**
 * @Description: 标准的结果模型
 * @author
 * @date 
 */
public class StandardResultModel {

	private String resultCode;
	private String resultMessage;
	private List<Object> results;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public void addResult(Object result) {
		results.add(result);
	}
	
	public String toString() {
		return JsonUtil.stringify(this);
	}
}
