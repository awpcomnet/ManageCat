/**
 * 描述：单品数据仓库
 * 作者：王航
 */
Ext.define("MIS.store.singleproduct.SingleproductStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.singleproduct.Singleproduct",
	
	proxy: {
		type: "ajax",
		url: "/singleproduct/query",
		reader: {
			type: "json",
			root: "results",
			totalProperty: "meta.totalRecord"
		},
        extraParams: {
        	singleName: "",
        	singleEname: "",
        	capacity: "",
        	unit: "",
        	singleId:"",
        	isUse: "",
        	ofOrigin: "",
        	startTime: "",
        	endTime: "",
        	brandId: ""
        }
	}
	
});