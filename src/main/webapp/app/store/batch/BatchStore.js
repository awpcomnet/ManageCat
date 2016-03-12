/**
 * 描述：批次号数据仓库
 * 作者：王航
 */
Ext.define("MIS.store.batch.BatchStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.batch.Batch",
	
	proxy: {
		type: "ajax",
		url: "/batch/query",
		reader: {
			type: "json",
			root: "results",
			totalProperty: "meta.totalRecord"
		},
        extraParams: {
        	batchNo:"",
        	createDateFormat: ""
        }
	}
	
});