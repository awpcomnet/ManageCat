/**
 * 描述：品牌模型
 * 作者：王航
 */
Ext.define("MIS.model.brand.Brand", {
	extend: "Ext.data.Model",
	
	fields: [{
		name: "brandId",
		type: "int"
	}, {
		name: "brandName",
		type: "string"
	}, {
		name: "brandEname",
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
	}]
});


