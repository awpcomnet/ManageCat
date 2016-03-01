/**
 * 描述: 以权限身份出现的栏目树 
 * 作者: 
 */
Ext.define("MIS.view.permission.PermissionCatalogTree", {
    extend: "Ext.tree.Panel",
    alias: "widget.permissioncatalogtree",
    
    store: "MIS.store.catalog.CatalogStore",
    split: true,

    listeners: {
        itemclick: function (component, record, item, index, event, eOpts) {
            var catalogId = record.raw.id,
                isLeaf = record.data.leaf,
                grid = component.up("permissionview").down("permissiongrid");
                  
            if (isLeaf) {
                grid.catalogId = catalogId;
                grid.getStore().proxy.extraParams.catalogId = catalogId;
                grid.getStore().reload();
            }
            grid.catalogRecord = record;
        }
    }
});
