/**
 * 描述: 邮件用户模型
 * 作者: 
 */
Ext.define("MIS.model.mailuser.MailUser", {
    extend: "Ext.data.Model",

    idProperty: "id",

    fields: [{
        name: "id",
        type: "int"
    }, {
        name: "username",
        type: "string"
    }, {
        name: "arg0",
        type: "string"
    }, {
        name: "createDate",
        type: "date"
    }, {
        name: "createDateFormat",
        type: "string"
    }]
});
