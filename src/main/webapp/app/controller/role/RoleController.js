/**
 * 描述: 角色请求控制器
 * 作者: 
 */
Ext.define("MIS.controller.role.RoleController", {
    extend: "Ext.app.Controller",

    models: [
        "MIS.model.role.Role"
    ],

    stores: [
        "MIS.store.role.RoleStore",
        "MIS.store.role.RoleAllStore"
    ],

    views: [
        "MIS.view.role.RoleView",
        "MIS.view.role.RoleGrid",
        "MIS.view.role.RoleAdd",
        "MIS.view.role.RoleModify",
        "MIS.view.role.RoleDetail",
        "MIS.view.role.RoleEditPermission",
        "MIS.view.role.RoleEditPermissionPanel"
    ],

    init: function () {
        this.control({
            "rolegrid": {
                render: this.onRoleGridRender
            }
        });
    },

    onRoleGridRender: function (component, options) {
        component.getStore().load();
    }
});

