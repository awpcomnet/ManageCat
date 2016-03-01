/**
 * 描述: 用户细节表单
 * 作者: 
 */
Ext.define("MIS.view.role.RoleDetail", {
    extend: "Ext.form.Panel",
    alias: "widget.roledetail",

    layout: {
        type: "anchor"
    },

    defaults: { 
        padding: "5 5 5 5",
        width: 400,
        labelWidth: 60,
        allowBlank: false
    },
    items: [{
        xtype: "textfield",
        fieldLabel: "名称",
        name: "name",
        flex: 1
    }, {
        xtype: "textfield",
        fieldLabel: "代码",
        name: "code",
        flex: 1
    }]
});
