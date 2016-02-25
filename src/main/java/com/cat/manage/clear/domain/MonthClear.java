package com.cat.manage.clear.domain;

import java.io.Serializable;

/**
 * @Description: 月结算领域模型
 * @author 王航
 * @date 2016年2月24日 下午2:12:08
 */
public class MonthClear implements Serializable{
	private String startTime;//结算开始日期
	private String endTime;//结算结束日期
	private Double sumSellPrice;//售出总金额(￥)
	private Double sumUnitCost;//实际成本总金额(￥)
	private Double sumRefund;//补损总金额(￥)
	private Double sumUnitCostPlan;//定义成本总金额(￥)
	
	private Double sumUnitPrice;//下单总金额($)
	private Double sumUnitPostage;//邮费总金额(￥)
	
	private Double sumProfit;//实际总利润(￥)
	private Double sumProfitPlan;//定义总利润(￥)
	
	private String remark;//备注
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Double getSumUnitPrice() {
		return sumUnitPrice;
	}

	public void setSumUnitPrice(Double sumUnitPrice) {
		this.sumUnitPrice = sumUnitPrice;
	}

	public Double getSumUnitPostage() {
		return sumUnitPostage;
	}

	public void setSumUnitPostage(Double sumUnitPostage) {
		this.sumUnitPostage = sumUnitPostage;
	}

	public Double getSumUnitCost() {
		return sumUnitCost;
	}

	public void setSumUnitCost(Double sumUnitCost) {
		this.sumUnitCost = sumUnitCost;
	}

	public Double getSumSellPrice() {
		return sumSellPrice;
	}

	public void setSumSellPrice(Double sumSellPrice) {
		this.sumSellPrice = sumSellPrice;
	}

	public Double getSumProfit() {
		return sumProfit;
	}

	public void setSumProfit(Double sumProfit) {
		this.sumProfit = sumProfit;
	}

	public Double getSumRefund() {
		return sumRefund;
	}

	public void setSumRefund(Double sumRefund) {
		this.sumRefund = sumRefund;
	}

	public Double getSumUnitCostPlan() {
		return sumUnitCostPlan;
	}

	public void setSumUnitCostPlan(Double sumUnitCostPlan) {
		this.sumUnitCostPlan = sumUnitCostPlan;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getSumProfitPlan() {
		return sumProfitPlan;
	}

	public void setSumProfitPlan(Double sumProfitPlan) {
		this.sumProfitPlan = sumProfitPlan;
	}

	@Override
	public String toString() {
		return "MonthClear [startTime=" + startTime + ", endTime=" + endTime
				+ ", sumUnitPrice=" + sumUnitPrice + ", sumUnitPostage="
				+ sumUnitPostage + ", sumUnitCost=" + sumUnitCost
				+ ", sumSellPrice=" + sumSellPrice + ", sumRefund=" + sumRefund
				+ ", sumProfit=" + sumProfit + "]";
	}

	private static final long serialVersionUID = -2397262231993669826L;

}
