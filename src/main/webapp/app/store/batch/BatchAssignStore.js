/**
 * 描述：批次号数据仓库 固定查询最新的50条
 * 作者：王航
 */
Ext.define("MIS.store.batch.BatchAssignStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.batch.Batch",
	
	proxy: {
		type: "ajax",
		url: "/batch/queryLast",
		reader: {
			type: "json",
			root: "results"
		},
        extraParams: {
        	batchNo:"",
        	createDateFormat: ""
        }
	}
	
});