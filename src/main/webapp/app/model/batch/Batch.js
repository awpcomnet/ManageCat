/**
 * 描述：批次号模型
 * 作者：王航
 */
Ext.define("MIS.model.batch.Batch", {
	extend: "Ext.data.Model",
	
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "batchNo",
		type: "string"
	}, {
		name: "createDate",
		type: "date"
	}, {
		name: "orderNum",
		type: "int"
	}, {
		name: "sumPrice",
		type: "string"
	}, {
		name: "createDateFormat",
		type: "string"
	}]
});


