package com.cat.manage.store.detail.domain;

import java.io.Serializable;

/**
 * 库存汇总领域模型
 * @author wanghang
 *
 */
public class StoreDetail implements Serializable{
	private Integer id;//伪编号
	private String payBy;//付款人
	private String sumUnitRmb;//下单总金额（￥）
	private String sumUnitPostage;//总邮费
	private String residueNum;//剩余货物数量
	private String sumStoreCost;//仓库总价值(含邮费)
	
	public String getPayBy() {
		return payBy;
	}

	public void setPayBy(String payBy) {
		this.payBy = payBy;
	}

	public String getSumUnitRmb() {
		return sumUnitRmb;
	}

	public void setSumUnitRmb(String sumUnitRmb) {
		this.sumUnitRmb = sumUnitRmb;
	}

	public String getSumUnitPostage() {
		return sumUnitPostage;
	}

	public void setSumUnitPostage(String sumUnitPostage) {
		this.sumUnitPostage = sumUnitPostage;
	}

	public String getResidueNum() {
		return residueNum;
	}

	public void setResidueNum(String residueNum) {
		this.residueNum = residueNum;
	}

	public String getSumStoreCost() {
		return sumStoreCost;
	}

	public void setSumStoreCost(String sumStoreCost) {
		this.sumStoreCost = sumStoreCost;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "StoreDetail [payBy=" + payBy + ", sumUnitRmb=" + sumUnitRmb
				+ ", sumUnitPostage=" + sumUnitPostage + ", residueNum="
				+ residueNum + ", sumStoreCost=" + sumStoreCost + "]";
	}

	private static final long serialVersionUID = -566397999263128773L;

}
