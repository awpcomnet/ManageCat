/**
 * 描述：用户数据仓库
 * 作者：王航
 */
Ext.define("MIS.store.user.UserStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.user.User",
	
	proxy: {
		type: "ajax",
		url: "/user/all",
		reader: {
			type: "json",
			root: "results"
		},
        extraParams: {
        	
        }
	}
	
});