/**
 * 描述: 用户细节设置表单
 * 作者: 
 */
Ext.define("MIS.view.user.UserDetailsForm", {
    extend: "Ext.form.Panel",
    alias: "widget.userdetailsform",

    requires: [
        "MIS.view.component.MultiSelectBox"
    ],

    layout: {
        type: "anchor"
    },
    defaults: {
        padding: "5 10 5 10",
        anchor: '100%'
    },
    style: "margin-top: 10px;",

    items: [{
        xtype: "container",
        layout: {
            type: "hbox"
        },
        defaults: {
            labelWidth: 60
        },
        items: [{
            xtype: "textfield",
            fieldLabel: "用户名",
            name: "username",
            allowBlank: false,
            flex: 1
        }, {
            xtype: "textfield",
            fieldLabel: "真实姓名",
            name: "realname",
            allowBlank: false,
            flex: 1,
            style: "margin-left: 10px"
        }]
    }, {
        xtype: "container",
        layout: {
            type: "anchor"
        },
        defaults: {
            labelWidth: 60,
            anchor: '100%'
        },
        items: [{
            xtype: "textfield",
            fieldLabel: "密码",
            allowBlank: false,
            name: "password"
        }, {
            xtype: "textfield",
            fieldLabel: "确认密码",
            allowBlank: false,
            name: "repeatPassword"
        }]
    }, {
        xtype: "container",
        layout: {
            type: "hbox"
        },
        defaults: {
            labelWidth: 60
        },
        items: [{
            xtype: "combobox",
            fieldLabel: "用户状态",
            allowBlank: false,
            flex: 1,
            store: MIS.common.DictManager.getDictStore("user.state"),
            name: "state",
            valueField: "value",
            displayField: "name",
            emptyText: "选择一种用户状态..."
        }, {
            xtype: "multiselectbox",
            fieldLabel: "拥有角色",
            flex: 1,
            style: "margin-left: 10px",
            name: "roles",
            store: Ext.create("MIS.store.role.RoleAllStore"),
            valueField: "id",
            displayField: "name"
        }]
    }],

    listeners: {
        beforeRender: function (component) {
            var win = component.up("userdetailswindow");
            if (win.actionMode == "MODIFY") {
                this.items.items.splice(1,1);
            }
        }        
    }
});
