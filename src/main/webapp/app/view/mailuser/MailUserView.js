/**
 * 描述：邮件用户界面
 * 作者：王航
 */
Ext.define("MIS.view.mailuser.MailUserView", {
	extend: "Ext.panel.Panel",
	alias: "widget.mailuserview",
	
	requires: [
	  "MIS.view.mailuser.MailUserGrid"         
	],
	
	layout: {
		type: 'fit'
	},
	
	items: [{
		xtype: "mailusergrid",
		flex: 1
	}]
	
	
});