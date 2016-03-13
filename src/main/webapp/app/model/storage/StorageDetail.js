/**
 * 描述：入库清单汇总模型
 * 作者：王航
 */
Ext.define("MIS.model.storage.StorageDetail", {
	extend: "Ext.data.Model",
	
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "payBy",
		type: "string"
	}, {
		name: "sumUnitRmb",
		type: "string"
	}, {
		name: "sumUnitPostage",
		type: "string"
	}, {
		name: "residueNum",
		type: "string"
	}, {
		name: "sumStoreCost",
		type: "string"
	}]
});


