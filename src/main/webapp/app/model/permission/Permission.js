/**
 * 描述: 权限模型
 * 作者: 
 */
Ext.define("MIS.model.permission.Permission", {
    extend: "Ext.data.Model",

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
        name: "url",
        type: "string"
    }, {
        name: "catalogId",
        type: "int"
    }]  
});
