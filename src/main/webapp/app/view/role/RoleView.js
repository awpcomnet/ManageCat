/**
 * 描述: 角色信息管理面板
 * 作者: 
 */
Ext.define("MIS.view.role.RoleView", {
    extend: "Ext.panel.Panel",
    alias: "widget.roleview",

    layout: {
        type: "fit"
    },

    items: [{
        xtype: "rolegrid"
    }]
});
