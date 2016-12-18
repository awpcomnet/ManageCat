/**
 * 描述：售出清单模型
 * 作者：王航
 */
Ext.define("MIS.model.selled.Selled", {
	extend: "Ext.data.Model",
	
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "storeId",
		type: "int"
	}, {
		name: "checkId",
		type: "int"
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
		name: "sellNum",
		type: "int"
	}, {
		name: "unitPrice",
		type: "string"
	}, {
		name: "payby",
		type: "string"
	}, {
		name: "unitRmb",
		type: "string"
	}, {
		name: "unitPostage",
		type: "string"
	}, {
		name: "unitCost",
		type: "string"
	}, {
		name: "sellingPrice",
		type: "string"
	}, {
		name: "refund",
		type: "string"
	}, {
		name: "remark",
		type: "string"
	}, {
		name: "sellTime",
		type: "string"
	}, {
		name: "selledStatus",
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
		name: "brandName",
		type: "string"
	}, {
		name: "brandEname",
		type: "string"
	}, {
		name: "seriesName",
		type: "string"
	}, {
		name: "singleName",
		type: "string"
	}, {
		name: "singleEname",
		type: "string"
	}, {
		name: "sumPrice",
		type: "string"
	}, {
		name: "trackingNumber",
		type: "string"
	}, {
		name: "headTrackingNumber",
		type: "string"
	}, {
		name: "batchNo",
		type: "string"
	}, {
		name: "specification",
		type: "string"
	}]
});


