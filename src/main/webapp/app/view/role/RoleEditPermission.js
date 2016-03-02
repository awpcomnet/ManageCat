/**
 * 描述: 角色权限数据修改界面
 * 作者: 
 */
Ext.define("MIS.view.role.RoleEditPermission", {
    extend: "Ext.window.Window",
    alias: "widget.roleeditpermission",

    layout: {
        type: "fit"
    },

    roleRecord: null,

    closeAction: "destroy",

    initComponent: function () {
        var items = this.items = [];
        items.push({
            xtype: "roleeditpermissionpanel",
            role: this.roleRecord
        });

        this.callParent();
    },

    buttons: [{
        text: "取消",
        handler: function (component) {
            component.up("roleeditpermission").close();
        }
    }, {
        text: "确定",
        handler: function (component) {
            var treepanel  = component.up("roleeditpermission").down("roleeditpermissionpanel"),
                selections = treepanel.getView().getChecked(),
                roleRecord = component.up("roleeditpermission").roleRecord;

            var rolePermissionRelation = [];
            Ext.Array.each(selections, function (node) {
                rolePermissionRelation.push({
                    roleId: roleRecord.raw.id,
                    permissionId: node.raw.pid,
                    permissionType: node.raw.type
                });
            });
            Ext.Ajax.request({
                url: "/role/updatepermission",
                params: {
                    roleId: roleRecord.raw.id,
                    permissionString: Ext.JSON.encode(rolePermissionRelation)
                },
                success: function (response) {
                    var result = Ext.decode(response.responseText);
                    if (result && result.resultCode == 0) {
                        component.up("roleeditpermission").close();
                    } else {
                        Ext.MessageBox.alert("错误提示", "编辑权限发生异常");
                    }
                },
                failure: function (response) {
                    Ext.MessageBox.alert("错误提示", "通信发生错误");
                }
            });
        }
    }]
});
