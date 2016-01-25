/**
 * 描述：子订单模型
 * 作者：王航
 */
Ext.define("MIS.model.order.SubOrder", {
	extend: "Ext.data.Model",
	
	fields: [{
		name: "suborderId",
		type: "int"
	}, {
		name: "superOrderId",
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
		name: "num",
		type: "int"
	}, {
		name: "orderPrice",
		type: "string"
	}, {
		name: "transferPrice",
		type: "string"
	}, {
		name: "costPrice",
		type: "string"
	}, {
		name: "sellingPrice",
		type: "string"
	}, {
		name: "curState",
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
	}]
});

