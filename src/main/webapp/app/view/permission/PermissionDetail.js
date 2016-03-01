/**
 * 描述: 权限细节表单
 * 作者: 
 */
Ext.define("MIS.view.permission.PermissionDetail", {
    extend: "Ext.form.Panel",
    alias: "widget.permissiondetail",


    defaults: {
        padding: "5 5 5 5"
    },
    items: [{
        xtype: "container",
        layout: {
            type: "hbox"
        },
        defaults: {
            labelWidth: 60,
            allowBlank: false
        },
        items: [{
            xtype: "textfield",
            fieldLabel: "名称",
            name: "name"
        }, {
            xtype: "textfield",
            fieldLabel: "权限代码",
            name: "code",
            style: "margin-left: 10px;"
        }]
    }, {
        xtype: "container",
        layout: {
            type: "hbox"
        },
        defaults: {
            labelWidth: 60,
            allowBlank: false
        },
        items: [{
            xtype: "textfield",
            fieldLabel: "类型",
            name: "type"
        }, {
            xtype: "textfield",
            fieldLabel: "关联地址",
            name: "url",
            style: "margin-left: 10px;"
        }]
    }]
});
