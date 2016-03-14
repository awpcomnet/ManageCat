/**
 * 描述：下单清单数据仓库
 * 作者：王航
 */
Ext.define("MIS.store.check.CheckStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.check.Check",
	
	proxy: {
		type: "ajax",
		url: "/check/query",
		reader: {
			type: "json",
			root: "results",
			totalProperty: "meta.totalRecord"
		},
        extraParams: {
        	orderStatus: "0",
        	trackingNumber: "",
        	transferCompany: "",
        	orderTime: "",
        	orderAddr: "",
        	brandId: "",
        	seriesId: "",
        	singleId: "",
        	payby: "",
        	batchNo: ""
        }
	}
	
});