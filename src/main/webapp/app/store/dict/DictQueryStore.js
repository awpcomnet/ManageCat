/**
 * 描述: 用于查询
 * 作者: 
 */
Ext.define("MIS.store.dict.DictQueryStore", {
    extend: "Ext.data.Store",

    requires: ["MIS.model.dict.DictItem"],

    model: "MIS.model.dict.DictItem",

    dictcode: "",

    constructor: function (config) {
        this.dictcode = config.dictcode;
        this.callParent();
    },

    proxy: {
        type: "ajax",
        url: "/dictitem/find",
        reader: {
            type: "json",
            root: "results"
        },
        extraParams: {
        	code: "",
        }
    },

    listeners: {
        beforeload: function (store) {
            store.proxy.extraParams.code = store.dictcode;
        }
    }
});
