/**
 * 描述: 角色模型
 * 作者: 
 */
Ext.define("MIS.model.role.Role", {
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
        name: "permissionIds",//permissionString
        type: "string"
    }]
});
