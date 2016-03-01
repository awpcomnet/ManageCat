/**
 * 描述: 增加用户窗口
 * 作者: 
 */
Ext.define("MIS.view.user.UserAdd", {
	extend: "Ext.window.Window",
	alias: "widget.useraddwindow",

	requires: ['Ext.form.field.Text'],

	title: "增加用户",

	layout: 'fit',
	width: 600,
	hright: 600,

	items: [],

	buttons: [{
		text: "取消"
	}, {
		text: "新增"
	}]
});
