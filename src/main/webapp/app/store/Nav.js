/**
 * 描述: 从服务器中获取导航栏数据
 * 作者: 
 */
Ext.define("MIS.store.Nav", {
	extend: "Ext.data.Store",

	requires: [
		"MIS.model.nav.Root"
	],

	model: "MIS.model.nav.Root",

	proxy: {
		type: "ajax",
		url: "/catalog/queryUserCatalog",
		
		reader: {
			type: "json",
			root: "results"
		},

		extraParams: {
			parentId: 0
		}
	}
});
