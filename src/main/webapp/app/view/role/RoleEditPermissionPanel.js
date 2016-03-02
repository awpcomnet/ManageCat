/**
 * 描述: 权限展示面板
 * 作者: 
 */
Ext.define("MIS.view.role.RoleEditPermissionPanel", {
    extend: "Ext.tree.Panel",
    alias: "widget.roleeditpermissionpanel",

    role: null,

    initComponent: function () {
        var me = this;
        Ext.apply(this, {
            store: Ext.create("MIS.store.permission.PermissionTreeStore", {
                root: {
                    text: "权限根",
                    expanded: true,
                    pid: 0,
                    checked: true,
                    type: 0
                },

                proxy: {
                    url: "/permission/tree",
                    type: "ajax",
                    reader: {
                        type: "json",
                        root: "results"
                    },
                    extraParams: {
                        id: me.role.raw.id
                    }
                }
            })
        });
        this.callParent();
    },

    listeners: {
        checkchange: function (node, checked, eOpts) {
            checked ? this.checkNode(node) : this.deCheckNode(node);
        }
    },

    checkNode: function (node) {
        if (!node.data.leaf) {
            node.cascadeBy(function (n) {
                n.set("checked", true);
            });
        }
        node.bubble(function (n) {
            n.set("checked", true);
        });
    },

    deCheckNode: function (node) {
        if (!node.data.leaf) {
            node.cascadeBy(function (n) {
                n.set("checked", false);
            });
        }
    }
});
