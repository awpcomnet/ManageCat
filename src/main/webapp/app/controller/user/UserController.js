/**
 * 功能: 用户管理模块Controller
 * 作者: 
 */
Ext.define("MIS.controller.user.UserController", {
	extend: "Ext.app.Controller",

	requires: [
		"MIS.model.user.User",
		"MIS.store.user.UserStore"
	],

	views: [
		"MIS.view.user.UserView",
		"MIS.view.user.UserGrid",
		"MIS.view.user.UserSearch",
		"MIS.view.user.UserDetailsWindow",
		"MIS.view.user.UserDetailsForm"
	],

	init: function () {
		this.control({
			"usergrid": {
				render: this.onUserGridRender
			}
		});
	},

	onUserGridRender: function (component, options) {
		component.getStore().load();
	}
});
