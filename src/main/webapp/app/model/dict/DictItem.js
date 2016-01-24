/**
 * 描述: 字典条目项模型
 * 作者: 
 */
Ext.define("MIS.model.dict.DictItem", {
    extend: "Ext.data.Model",

    idProperty: "id",

    fields: [{
        name: "id",
        type: "int"
    }, {
        name: "typeId",
        type: "int"
    }, {
        name: "name",
        type: "string"
    }, {
        name: "value",
        type: "string"
    }, {
        name: "code",
        type: "string"
    }, {
        name: "description",
        type: "string"
    }]
});
