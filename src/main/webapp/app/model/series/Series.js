/**
 * 描述：系列模型
 * 作者：王航
 */
Ext.define("MIS.model.series.Series", {
	extend: "Ext.data.Model",
	
	fields: [{
		name: "seriesId",
		type: "int"
	}, {
		name: "seriesName",
		type: "string"
	}, {
		name: "seriesEname",
		type: "string"
	}, {
		name: "ofOrigin",
		type: "string"
	}, {
		name: "isUse",
		type: "date"
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


