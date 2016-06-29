/**
 * 描述：单品数据仓库(所有)
 * 作者：王航
 */
Ext.define("MIS.store.singleproduct.SingleproductAllStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.singleproduct.Singleproduct",
	
	proxy: {
		type: "ajax",
		url: "/singleproduct/queryAll",
		reader: {
			type: "json",
			root: "results"
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