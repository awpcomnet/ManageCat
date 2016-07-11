/**
 * 描述：用户数据仓库(添加状态判断)
 * 作者：王航
 */
Ext.define("MIS.store.user.UserStateStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.user.User",
	
	proxy: {
		type: "ajax",
		url: "/user/queryAll",
		reader: {
			type: "json",
			root: "results"
		},
        extraParams: {
        	state : 0
        }
	}
	
});