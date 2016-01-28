/**
 * 描述：主订单模型
 * 作者：王航
 */
Ext.define("MIS.model.order.Order", {
	extend: "Ext.data.Model",
	
	fields: [{
		name: "orderId",
		type: "int"
	}, {
		name: "foreignState",
		type: "string"
	}, {
		name: "transfer",
		type: "string"
	}, {
		name: "affirmState",
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
		name: "sumOrderPrice",
		type: "string"
	}, {
		name: "sumTransferPrice",
		type: "string"
	}, {
		name: "sumCostPrice",
		type: "string"
	}, {
		name: "sumSellingPrice",
		type: "string"
	}, {
		name: "sumSubOrderNum",
		type: "string"
	}]
});


