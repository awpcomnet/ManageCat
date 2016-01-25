/**
 * 描述：子订单数据仓库
 * 作者：王航
 */
Ext.define("MIS.store.order.SubOrderStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.order.SubOrder",
	
	proxy: {
		type: "ajax",
		url: "/subOrder/queryMore",
		reader: {
			type: "json",
			root: "results",
			totalProperty: "meta.totalRecord"
		},
        extraParams: {
        	startTime: "",
        	endTime: ""
        }
	}
	
});