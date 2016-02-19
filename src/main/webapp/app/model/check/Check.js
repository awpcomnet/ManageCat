/**
 * 描述：下单清单模型
 * 作者：王航
 */
Ext.define("MIS.model.check.Check", {
	extend: "Ext.data.Model",
	
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "trackingNumber",
		type: "string"
	}, {
		name: "orderTime",
		type: "string"
	}, {
		name: "transferCompany",
		type: "string"
	}, {
		name: "orderAddr",
		type: "string"
	}, {
		name: "brandId",
		type: "int"
	}, {
		name: "seriesId",
		type: "int"
	}, {
		name: "singleId",
		type: "int"
	}, {
		name: "brandName",
		type: "string"
	}, {
		name: "seriesName",
		type: "string"
	}, {
		name: "singleName",
		type: "string"
	}, {
		name: "num",
		type: "int"
	}, {
		name: "unitPrice",
		type: "string"
	}, {
		name: "remark",
		type: "string"
	}, {
		name: "remark",
		type: "string"
	}, {
		name: "payby",
		type: "string"
	}, {
		name: "orderStatus",
		type: "string"
	}, {
		name: "createDate",
		type: "date"
	}, {
		name: "updateDate",
		type: "date"
	}, {
		name: "createDateFormat",
		type: "string"
	}, {
		name: "updateDateFormat",
		type: "string"
	}, {
		name: "sumPrice",
		type: "string"
	}]
});


