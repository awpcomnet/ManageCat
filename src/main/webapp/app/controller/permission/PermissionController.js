/**
 * 描述: 资源权限控制器
 * 作者: 
 */
Ext.define("MIS.controller.permission.PermissionController", {
    extend: "Ext.app.Controller",

    models: ["MIS.model.permission.Permission"],
    stores: [
        "MIS.store.permission.PermissionStore",
        "MIS.store.permission.PermissionTreeStore"
    ],

    views: [
        "MIS.view.permission.PermissionView",
        "MIS.view.permission.PermissionGrid",
        "MIS.view.permission.PermissionSearch",
        "MIS.view.permission.PermissionCatalogTree",
        "MIS.view.permission.PermissionAdd",
        "MIS.view.permission.PermissionDetail"
    ],

    init: function () {
        this.control({
            "permissiongrid": {
                beforerender: this.onBeforePermissionGridRender
            }
        });
    },

    /**
     * 在数据表格渲染之前执行
     */
    onBeforePermissionGridRender: function (component, options) {
        component.getStore().load();
    }
});
