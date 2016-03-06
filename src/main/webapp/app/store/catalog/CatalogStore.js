/**
 * 描述: 栏目仓库
 * 作者: 
 */
Ext.define("MIS.store.catalog.CatalogStore", {
    extend: "Ext.data.TreeStore",

    fields : [{
        name : 'className',
        type : 'string'
    }, {
        name : 'text',
        type : 'string'
    }, {
        name : 'iconCls',
        type : 'string'
    }],

    proxy: {
        type: "ajax",
        url: "/catalog/queryUserCatalog",
        reader: {
            type: "json",
            root: "results"
        },
        extraParams: {
            parentId: "0"
        }
    },

    root: {
        nodeType: 'async',
        text: "系统栏目",
        expanded: true,
        id: "0"
    },

    listeners: {
        beforeexpand: function (node, eOpts) {
            this.proxy.extraParams.parentId = node.raw.id;
        }
    }
});
