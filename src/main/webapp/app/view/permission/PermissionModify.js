/**
 * 描述: 用户修改界面
 * 作者: 
 */
Ext.define("MIS.view.permission.PermissionModify", {
    extend: "Ext.window.Window",
    alias: "widget.permissionmodify",

    layout: {
        type: "fit"
    },

    tempData: {},

    initComponent: function () {
        this.callParent();
        this.setTitle("修改权限");
    },

    items: [{
        xtype: "permissiondetail"
    }],

    buttons: [{
        text: "取消",
        handler: function (component) {
            component.up("window").close();
        }
    }, {
        text: "确定",
        handler: function (component) {
            var window = component.up("permissionmodify"),
                record = window.tempData.record,
                form = window.down("permissiondetail").getForm();
            
            if (form.isValid()) {
                var params = form.getValues();
                params.id = record.raw.id;
                Ext.Ajax.request({
                    url: "/permission/update",
                    params: params,
                    success: function (response) {
                        var result = Ext.JSON.decode(response.responseText);
                        if (result && result.resultCode == 0) {
                            var grid = Ext.ComponentQuery.query("permissiongrid")[0];
                            grid.getStore().reload();

                            window.close();
                        } else {
                            Ext.MessageBox.alert("错误提示", "更新权限数据出错");
                        }
                    },
                    failure: function (response) {
                        Ext.MessageBox.alert("错误提示", "Http通信出错");
                    }
                });
            } 
        }
    }],

    listeners: {
        close: function (component) {
            var view = Ext.ComponentQuery.query("permissionview")[0];
            view.getEl().unmask();
        }, 
        beforerender: function (component) {
            var me = this;

            component.down("textfield[name=name]").setValue(me.tempData.record.raw.name);
            component.down("textfield[name=code]").setValue(me.tempData.record.raw.code);
//            component.down("textfield[name=type]").setValue(me.tempData.record.raw.type);
            component.down("textfield[name=url]").setValue(me.tempData.record.raw.url);
        },
        afterrender: function (component) {
            // component.down("combobox").getStore().reload();
        }
    }
});
