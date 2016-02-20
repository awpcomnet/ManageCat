/**
 * 描述：邮寄清单主单模型
 * 作者：王航
 */
Ext.define("MIS.model.shipped.ShippedHead", {
	extend: "Ext.data.Model",
	
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "trackingNumber",
		type: "string"
	}, {
		name: "transferCompany",
		type: "string"
	}, {
		name: "submitTime",
		type: "string"
	}, {
		name: "postage",
		type: "string"
	}, {
		name: "createDate",
		type: "date"
	}, {
		name: "createDateFormat",
		type: "string"
	}, {
		name: "shippedNum",
		type: "string"
	}, {
		name: "storeNum",
		type: "string"
	}]
});


