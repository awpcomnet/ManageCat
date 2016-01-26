/**
 * 描述：品牌单数据仓库
 * 作者：王航
 */
Ext.define("MIS.store.brand.BrandStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.brand.Brand",
	
	proxy: {
		type: "ajax",
		url: "/brand/query",
		reader: {
			type: "json",
			root: "results",
			totalProperty: "meta.totalRecord"
		},
        extraParams: {
        	brandId:"",
        	isUse: "",
        	ofOrigin: "",
        	startTime: "",
        	endTime: ""
        }
	}
	
});