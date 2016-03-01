/**
 * 描述: 用户管理界面
 * 作者: 
 */
Ext.define("MIS.view.user.UserView", {
	extend: "Ext.panel.Panel",
	alias: "widget.userview",

    layout: {
        type: "border"
    },

    items: [{
        xtype: "usersearch",
        region: "north",
        hidden: true
    }, {
        xtype: "usergrid",
        region: "center"
    }]
});
