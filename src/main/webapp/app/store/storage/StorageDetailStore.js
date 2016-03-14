/**
 * 描述：入库清单汇总数据仓库
 * 作者：王航
 */
Ext.define("MIS.store.storage.StorageDetailStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.storage.StorageDetail",
	
	proxy: {
		type: "ajax",
		url: "/storedetail/all",
		reader: {
			type: "json",
			root: "results"
		},
        extraParams: {
        }
	}
	
});