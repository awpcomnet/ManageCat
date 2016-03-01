/**
 * 描述: 权限树的数据访问仓库 
 * 作者: 
 */
Ext.define("MIS.store.permission.PermissionTreeStore", {
    extend: "Ext.data.TreeStore",

    root: {
        text: "权限根",
        expanded: true,
        id: 0,
        checked: false
    },

    proxy: {
        url: "/permission/tree",
        type: "ajax",
        reader: {
            type: "json",
            root: "results"
        }
    }
});
