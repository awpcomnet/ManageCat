package com.cat.manage.shipped.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮寄清单（子单）领域模型
 * @author wanghang
 *
 */
public class Shipped implements Serializable{
	private Integer id;//邮寄清单子单唯一编号
	private Integer headId;//邮寄主单唯一编号
	private Integer checkId;//下单清单唯一编号
	private String trackingNumber;//国外快递单号
	private String transferCompany;//转运公司
	private Integer brandId;//品牌编号
	private Integer seriesId;//系列编号
	private Integer singleId;//单品编号
	private Integer num;//数量
	private Integer storeNum;//入库数量
	private Double unitPrice;//下单单价（美元单位）
	private String payby;//付款人
	private Double planRmb;//预计单价(人民币)
	private Double planPostage;//预计邮费(人民币)
	private Double planCost;//预计成本
	private String remark;//备注
	private String shippedStatus;//邮寄状态
	private Date createDate;//创建时间
	private Date updateDate;//更新时间
	private String weight;//重量(kg/个)
	private Double rate;//汇率
	private Integer currency;//币种
	
	private String createDateFormat;//格式化后的创建时间
	private String updateDateFormat;//格式化后的修改时间
	private String brandName;//品牌名称
	private String seriesName;//系列名称
	private String singleName;//单品名称
	private String brandEname;//品牌名称
	private String singleEname;//单品名称
	private String sumPrice;//总价
	private String specification;//规格
	
	private String dateOfManufacture;//生产日期
	private String qualityGuaranteePeriod;//有效时长（单位：年）
	private String periodOfValidity;//到期时间
	private String dateOfManufactureFormat;//格式化后的生产日期
	private String periodOfValidityFormat;//格式化后的到期时间
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHeadId() {
		return headId;
	}

	public void setHeadId(Integer headId) {
		this.headId = headId;
	}

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getTransferCompany() {
		return transferCompany;
	}

	public void setTransferCompany(String transferCompany) {
		this.transferCompany = transferCompany;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public Integer getSingleId() {
		return singleId;
	}

	public void setSingleId(Integer singleId) {
		this.singleId = singleId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getPayby() {
		return payby;
	}

	public void setPayby(String payby) {
		this.payby = payby;
	}

	public Double getPlanRmb() {
		return planRmb;
	}

	public void setPlanRmb(Double planRmb) {
		this.planRmb = planRmb;
	}

	public Double getPlanPostage() {
		return planPostage;
	}

	public void setPlanPostage(Double planPostage) {
		this.planPostage = planPostage;
	}

	public Double getPlanCost() {
		return planCost;
	}

	public void setPlanCost(Double planCost) {
		this.planCost = planCost;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShippedStatus() {
		return shippedStatus;
	}

	public void setShippedStatus(String shippedStatus) {
		this.shippedStatus = shippedStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateDateFormat() {
		return createDateFormat;
	}

	public void setCreateDateFormat(String createDateFormat) {
		this.createDateFormat = createDateFormat;
	}

	public String getUpdateDateFormat() {
		return updateDateFormat;
	}

	public void setUpdateDateFormat(String updateDateFormat) {
		this.updateDateFormat = updateDateFormat;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getSingleName() {
		return singleName;
	}

	public void setSingleName(String singleName) {
		this.singleName = singleName;
	}

	public String getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(String sumPrice) {
		this.sumPrice = sumPrice;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Integer getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(Integer storeNum) {
		this.storeNum = storeNum;
	}

	public String getBrandEname() {
		return brandEname;
	}

	public void setBrandEname(String brandEname) {
		this.brandEname = brandEname;
	}

	public String getSingleEname() {
		return singleEname;
	}

	public void setSingleEname(String singleEname) {
		this.singleEname = singleEname;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getCurrency() {
		return currency;
	}

	public void setCurrency(Integer currency) {
		this.currency = currency;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getDateOfManufacture() {
		return dateOfManufacture;
	}

	public void setDateOfManufacture(String dateOfManufacture) {
		this.dateOfManufacture = dateOfManufacture;
	}

	public String getQualityGuaranteePeriod() {
		return qualityGuaranteePeriod;
	}

	public void setQualityGuaranteePeriod(String qualityGuaranteePeriod) {
		this.qualityGuaranteePeriod = qualityGuaranteePeriod;
	}

	public String getPeriodOfValidity() {
		return periodOfValidity;
	}

	public void setPeriodOfValidity(String periodOfValidity) {
		this.periodOfValidity = periodOfValidity;
	}

	public String getDateOfManufactureFormat() {
		return dateOfManufactureFormat;
	}

	public void setDateOfManufactureFormat(String dateOfManufactureFormat) {
		this.dateOfManufactureFormat = dateOfManufactureFormat;
	}

	public String getPeriodOfValidityFormat() {
		return periodOfValidityFormat;
	}

	public void setPeriodOfValidityFormat(String periodOfValidityFormat) {
		this.periodOfValidityFormat = periodOfValidityFormat;
	}

	@Override
	public String toString() {
		return "Shipped [id=" + id + ", headId=" + headId + ", checkId="
				+ checkId + ", trackingNumber=" + trackingNumber
				+ ", transferCompany=" + transferCompany + ", brandId="
				+ brandId + ", seriesId=" + seriesId + ", singleId=" + singleId
				+ ", num=" + num + ", storeNum=" + storeNum + ", unitPrice="
				+ unitPrice + ", payby=" + payby + ", planRmb=" + planRmb
				+ ", planPostage=" + planPostage + ", planCost=" + planCost
				+ ", remark=" + remark + ", shippedStatus=" + shippedStatus
				+ ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", weight=" + weight + ", rate=" + rate + ", currency="
				+ currency + ", createDateFormat=" + createDateFormat
				+ ", updateDateFormat=" + updateDateFormat + ", brandName="
				+ brandName + ", seriesName=" + seriesName + ", singleName="
				+ singleName + ", brandEname=" + brandEname + ", singleEname="
				+ singleEname + ", sumPrice=" + sumPrice + ", specification="
				+ specification + ", dateOfManufacture=" + dateOfManufacture
				+ ", qualityGuaranteePeriod=" + qualityGuaranteePeriod
				+ ", periodOfValidity=" + periodOfValidity
				+ ", dateOfManufactureFormat=" + dateOfManufactureFormat
				+ ", periodOfValidityFormat=" + periodOfValidityFormat + "]";
	}

	private static final long serialVersionUID = -508826833677156720L;

}
