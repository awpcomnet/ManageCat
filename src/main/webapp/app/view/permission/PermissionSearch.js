/**
 * 描述: 权限数据搜索区域
 * 作者: 
 */
Ext.define("MIS.view.permission.PermissionSearch", {
    extend: "Ext.form.Panel",
    alias: "widget.permissionsearch",

    layout: {
        type: "anchor"
    },

    style: "border-bottom: 4px solid #3892d3",

    showState: false,
    defaults: {
        padding: "5 5 0 5"
    },
    items: [{
        xtype: "container",
        defaults: {
            labelWidth: 80
        },
        layout: {
            type: "hbox"
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
            flex: 1,
            style: "margin-left: 10px;"
        }]
    }, {
        xtype: "container",
        defaults: {
            labelWidth: 80
        },
        layout: {
            type: "hbox"
        },
        items: [{
            xtype: "textfield",
            fieldLabel: "资源地址",
            name: "url",
            flex: 1
        }
//        , {
//            xtype: "textfield",
//            fieldLabel: "权限类型",
//            name: "type",
//            flex: 1,
//            style: "margin-left: 10px;"
//        }
        ]
    }],

    buttons: [{
        text: "重置",
        handler: function () {
            this.up("form").getForm().reset();
        }
    }, {
        text: "搜索",
        handler: function () {
            var form = this.up("form").getForm();
                gridStore = this.up("permissionview").down("permissiongrid").getStore();
            if (form.isValid()) {
                var params = form.getValues();
                Ext.apply(gridStore.proxy.extraParams, params);
                gridStore.reload();
            }
        }
    }]
});
