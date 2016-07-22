/**
 * 描述：邮寄清单主单数据仓库
 * 作者：王航
 */
Ext.define("MIS.store.shipped.ShippedHeadStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.shipped.ShippedHead",
	
	proxy: {
		type: "ajax",
		url: "/shippedHead/query",
		reader: {
			type: "json",
			root: "results",
			totalProperty: "meta.totalRecord"
		},
        extraParams: {
        	id: "",
        	trackingNumber: "",
        	transferCompany:"",
        	submitTime: "",
        	flag: "",
        	orderBy: ""
        }
	}
	
});