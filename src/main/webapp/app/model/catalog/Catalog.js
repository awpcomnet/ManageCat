/**
 * 描述: 左侧导航栏
 * 作者: 
 */
Ext.define("MIS.model.catalog.Catalog", {
    extend: "Ext.data.Model",

    idProperty: "id",

    fields: [{
        name: "id",
        type: "int"
    }, {
        name: "parentId",
        type: "int"
    }, {
        name: "abbr",
        type: "string"
    }, {
        name: "text",
        type: "string"
    }, {
        name: "url",
        type: "string"
    }, {
        name: "urlType",
        type: "string"
    }, {
        name: "description",
        type: "string"
    }, {
        name: "order",
        type: "int"
    }, {
        name: "state",
        type: "string"
    }, {
        name: "iconCls",
        type: "string"
    }, {
        name: "leaf"
    }, {
        name: "expanded"
    }]
});
