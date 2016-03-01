/**
 * 描述: 权限的数据访问对象
 * 作者: 
 */
Ext.define("MIS.store.permission.PermissionStore", {
    extend: "Ext.data.Store",

    model: "MIS.model.permission.Permission",

    proxy: {
        type: "ajax",
        url: "/permission/page",
        reader: {
            type: "json",
            root: "results",
            totalProperty: "meta.totalRecord"
        },
        extraParams: {
            catalogId: 0
        }
    }
});
