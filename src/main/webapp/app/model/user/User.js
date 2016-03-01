/**
 * 描述：用户模型
 * 作者：王航
 */
Ext.define("MIS.model.user.User", {
	extend: "Ext.data.Model",
	
	fields: [{
		name: "userId",
		type: "int"
	}, {
		name: "username",
		type: "string"
	}, {
		name: "realname",
		type: "string"
	}, {
		name: "email",
		type: "string"
	}, {
		name: "state",
		type: "string"
	}, {
		name: "loginTimes",
		type: "int"
	}, {
		name: "roles",
		type: "string"
	}]
});

