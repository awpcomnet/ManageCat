/**
 * 描述: 字典模型
 * 作者: 
 */
Ext.define("MIS.model.dict.Dict", {
    extend: "Ext.data.Model",

    idProperty: "id",

    fields: [{
        name: "id",
        type: "int"
    }, {
        name: "name",
        type: "string"
    }, {
        name: "code",
        type: "string"
    }, {
        name: "type",
        type: "string"
    }, {
        name: "description",
        type: "string"
    }]
});
