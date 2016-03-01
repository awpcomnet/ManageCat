/**
 * 描述: 角色数据修改
 * 作者: 
 */
Ext.define("MIS.view.role.RoleModify", {
    extend: "Ext.window.Window",
    alias: "widget.rolemodify",

    layout: {
        type: "fit"
    },

    modifyRecord: null,

    items: [{
        xtype: "roledetail"
    }],

    initComponent: function () {
        this.callParent();
        
        var record = this.modifyRecord;
        this.down("textfield[name=name]").setValue(record.raw.name);
        this.down("textfield[name=code]").setValue(record.raw.code);
    },

    buttons: [{
        text: "取消",
        handler: function (component) {
            component.up("rolemodify").close();
        }
    }, {
        text: "确定",
        handler: function (component) {
            var form = component.up("rolemodify").down("roledetail").getForm();
            if (form.isValid()) {
                var params = form.getValues();
                params.id = component.up("rolemodify").modifyRecord.raw.id;
                Ext.Ajax.request({
                    url: "/role/update",
                    params: params,
                    success: function (response) {
                        var result = Ext.JSON.decode(response.responseText);
                        if (result && result.resultCode == 0)  {
                            var grid = Ext.ComponentQuery.query("rolegrid")[0];
                            grid.getStore().reload();

                            component.up("rolemodify").close();
                        } else {
                            Ext.MessageBox.alert("错误提示", !result ? "修改用户数据出错" : result.resultMessage);
                        }
                    },
                    failure: function (response) {
                        Ext.MessageBox.alert("错误提示", "修改用户数据出错");
                    }
                });
            }
        }
    }]
});
