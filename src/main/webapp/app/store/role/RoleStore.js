/**
 * 描述: 角色信息仓库
 * 作者: 
 */
Ext.define("MIS.store.role.RoleStore", {
    extend: "Ext.data.Store",

    model: "MIS.model.role.Role",

    proxy: {
        type: "ajax",
        url: "/role/page",
        reader: {
            type: "json",
            root: "results",
            totalProperty: "meta.totalRecord"
        }
    }
});
