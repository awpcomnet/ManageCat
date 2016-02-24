/**
 * 描述：入库清单模型
 * 作者：王航
 */
Ext.define("MIS.model.storage.Storage", {
	extend: "Ext.data.Model",
	
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "shippedId",
		type: "int"
	}, {
		name: "checkId",
		type: "int"
	}, {
		name: "trackingNumber",
		type: "string"
	}, {
		name: "transferCompany",
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
		name: "num",
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
		name: "remark",
		type: "string"
	}, {
		name: "sellNum",
		type: "int"
	}, {
		name: "storeTime",
		type: "string"
	}, {
		name: "storeStatus",
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
		name: "seriesName",
		type: "string"
	}, {
		name: "singleName",
		type: "string"
	}, {
		name: "residueNum",
		type: "string"
	}]
});

