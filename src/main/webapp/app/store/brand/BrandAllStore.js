/**
 * 描述：品牌单数据仓库
 * 作者：王航
 */
Ext.define("MIS.store.brand.BrandAllStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.brand.Brand",
	
	proxy: {
		type: "ajax",
		url: "/brand/queryAll",
		reader: {
			type: "json",
			root: "results"
		},
        extraParams: {
        	brandId:"",
        	isUse: "",
        	ofOrigin: "",
        	brandName: "",
        	brandEname: ""
        }
	}
	
});