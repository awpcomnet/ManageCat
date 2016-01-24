/**
 * 描述: 
 * 作者: 
 */
Ext.define("MIS.store.dict.DictStore", {
    extend: "Ext.data.Store",

    model: "MIS.model.dict.Dict",

    proxy: {
        type: "ajax",
        url: "/dict/type",
        reader: {
            type: "json",
            root: "results"
        },
        extraParams: {
            type: "0"
        }
    }
});
