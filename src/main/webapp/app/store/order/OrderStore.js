/**
 * 描述：主订单数据仓库
 * 作者：王航
 */
Ext.define("MIS.store.order.OrderStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.order.Order",
	
	proxy: {
		type: "ajax",
		url: "/order/queryAll",
		reader: {
			type: "json",
			root: "results",
			totalProperty: "meta.totalRecord"
		},
        extraParams: {
        	foreignState:"",
        	transfer: "",
        	affirmState: "",
        	startTime: "",
        	endTime: ""
        }
	}
	
});