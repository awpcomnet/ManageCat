/**
 * 描述：邮寄清单子单数据仓库
 * 作者：王航
 */
Ext.define("MIS.store.shipped.ShippedStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.shipped.Shipped",
	
	proxy: {
		type: "ajax",
		url: "/shipped/query",
		reader: {
			type: "json",
			root: "results",
			totalProperty: "meta.totalRecord"
		},
        extraParams: {
        	headId: "",
        	shippedStatus: "1"
        }
	}
	
});