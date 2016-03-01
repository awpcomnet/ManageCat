/**
 * 描述: 用户新增
 * 作者: 
 */
Ext.define("MIS.view.role.RoleAdd", {
    extend: "Ext.window.Window",
    alias: "widget.roleadd",

    layout: {
        type: "fit"
    },

    items: [{
        xtype: "roledetail"
    }],

    buttons: [{
        text: "取消",
        handler: function (component) {
            component.up("roleadd").close();
        }
    }, {
        text: "确定",
        handler: function (component) {
            var form = component.up("roleadd").down("roledetail").getForm(),
                window = component.up("roleadd");
            if (form.isValid()) {
                var params = form.getValues();
                Ext.Ajax.request({
                    url: "/role/add",
                    params: params,
                    success: function (response) {
                        var result = Ext.JSON.decode(response.responseText);
                        if (result && result.resultCode == 0) {
                            var grid = Ext.ComponentQuery.query("rolegrid")[0];
                            grid.getStore().reload();

                            window.close();
                        } else {
                            Ext.MessageBox.alert("错误提示", !result ? "新增角色数据失败, 请稍后重试" : result.resultMessage);
                        }
                    },
                    failure: function (response) {
                        Ext.MessageBox.alert("错误提示", "发生错误");
                    }
                });
            }
        }
    }]
});
