/**
 * 描述: 权限增加按钮
 * 作者: 
 */
Ext.define("MIS.view.permission.PermissionAdd", {
    extend: "Ext.window.Window",
    alias: "widget.permissionadd",

    layout: {
        type: "fit"
    },

    tempData: {},

    initComponent: function () {
        this.setTitle("新增权限");
        this.callParent(); 
    },

    items: [{
        xtype: "permissiondetail"
    }],

    buttons: [{
        text: "取消",
        handler: function (component) {
            var addWindow = component.up("permissionadd");
            addWindow.close();
        }
    }, {
        text: "确定",
        handler: function (component) {
            var form = component.up("window").down("permissiondetail").getForm(),
                window = component.up("window");

            if (form.isValid()) {
                var params = form.getValues();
                params.catalogId = window.tempData.catalogId;
                Ext.Ajax.request({
                    url: "/permission/add",
                    params: params,
                    success: function (response) {
                        var result = Ext.JSON.decode(response.responseText);
                        if (result && result.resultCode == 0) {
                            var grid = Ext.ComponentQuery.query("permissiongrid")[0];
                            grid.getStore().reload();

                            var addWindow = component.up("permissionadd");
                            addWindow.close();
                        } else {
                            Ext.MessageBox.alert("错误提示", !result ? "新增权限错误" : result.resultMessage);
                        }
                    },
                    failure: function (response) {
                        Ext.MessageBox.alert("错误提示", "Http通信错误");
                    }
                });
            }
        }
    }],


    listeners: {
        close: function (component) {
            var view = Ext.ComponentQuery.query("permissionview")[0];
            view.getEl().unmask();
        }
    }
});
