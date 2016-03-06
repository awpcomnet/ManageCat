/**
 * 描述: 用于处理对栏目信息访问
 * 作者: 
 */
Ext.define("MIS.store.catalog.CatalogInfoStore", {
    extend: "Ext.data.Store",

    model: "MIS.model.catalog.Catalog",

    proxy: {
        type: "ajax",
        url: "/catalog/queryUserCatalog",
        reader: {
            type: "json",
            root: "results"
        },
        extraParams: {
            parentId: "0",
            name: "",
            abbr: "",
            urlType: ""
        }
    },

    listeners: {
        "load": function (store, records, opt) {
        }
    }
});
