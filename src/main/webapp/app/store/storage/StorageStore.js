/**
 * 描述：入库清单数据仓库
 * 作者：王航
 */
Ext.define("MIS.store.storage.StorageStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.storage.Storage",
	
	proxy: {
		type: "ajax",
		url: "/store/query",
		reader: {
			type: "json",
			root: "results",
			totalProperty: "meta.totalRecord"
		},
        extraParams: {
        	
        }
	}
	
});