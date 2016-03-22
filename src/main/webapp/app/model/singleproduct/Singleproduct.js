/**
 * 描述：单品模型
 * 作者：王航
 */
Ext.define("MIS.model.singleproduct.Singleproduct", {
	extend: "Ext.data.Model",
	
	fields: [{
		name: "singleId",
		type: "int"
	}, {
		name: "singleName",
		type: "string"
	}, {
		name: "singleEname", 
		type: "string"
	}, {
		name: "capacity",
		type: "string"
	}, {
		name: "unit",
		type: "string"
	}, {
		name: "ofOrigin",
		type: "string"
	}, {
		name: "isUse",
		type: "string"
	}, {
		name: "createDate",
		type: "date"
	}, {
		name: "createBy",
		type: "string"
	}, {
		name: "updateDate",
		type: "date"
	}, {
		name: "updateBy",
		type: "string"
	}, {
		name: "createDateFormat",
		type: "string"
	}, {
		name: "updateDateFormat",
		type: "string"
	}, {
		name: "ofOriginName",
		type: "string"
	}, {
		name: "brandId",
		type: "int"
	}, {
		name: "brandName",
		type: "string"
	}]
});


