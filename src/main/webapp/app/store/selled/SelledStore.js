/**
 * 描述：售出数据仓库
 * 作者：王航
 */
Ext.define("MIS.store.selled.SelledStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.selled.Selled",
	
	proxy: {
		type: "ajax",
		url: "/selled/query",
		reader: {
			type: "json",
			root: "results",
			totalProperty: "meta.totalRecord"
		},
        extraParams: {
        	brandId: "",
        	seriesId: "",
        	singleId: "",
        	selledStatus: "",
        	startTime: "",
        	endTime: ""
        }
	}
	
});