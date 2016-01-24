/**
 * 描述: 字典数据项仓库
 * 作者: 
 */
Ext.define("MIS.store.dict.DictItemStore", {
    extend: "Ext.data.Store",

    model: "MIS.model.dict.DictItem",

    proxy: {
        type: "ajax",
        url: "/dictitem/items",
        reader: {
            type: "json",
            root: "results",
            totalProperty: "meta.totalRecord"
        },
        extraParams: {
            typeId: "0",
            name: "",
			value: "",
			code: ""
        }
    }
});
