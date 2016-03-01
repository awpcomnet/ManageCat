/**
 * 描述: 权限视图入口
 * 作者: 
 */
Ext.define("MIS.view.permission.PermissionView", {
    extend: "Ext.panel.Panel",
    alias: "widget.permissionview",

    layout: {
        type: "border"
    }, 

    items: [{
        xtype: "permissioncatalogtree",
        title: "",
        width: 260,
        region: "west"
    }, {
        xtype: "panel",
        region: "center",
        layout: {
            type: "border"
        },
        items: [{
            xtype: "permissionsearch",
            region: "north",
            height: 105,
            hidden: true
        }, {
            xtype: "permissiongrid",
            region: "center"
        }]
    }]
});
