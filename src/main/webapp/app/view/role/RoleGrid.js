/**
 * 描述: 角色信息表格
 * 作者: 
 */
Ext.define("MIS.view.role.RoleGrid", {
    extend: "Ext.grid.Panel",
    alias: "widget.rolegrid",

    store: "MIS.store.role.RoleStore",

    initComponent: function () {
        //创建多选框  
        var checkBox = Ext.create('Ext.selection.CheckboxModel');   
        var me = this;

        Ext.apply(this, {
            frame: false,
        
            forceFit: true,
            scrollOffset: 0,

            selModel:checkBox,  
            disableSelection: false,//表示可以选择行  
            columnLines: true,
            loadMask: true,   

            columns: [{
                header: "编号",
                dataIndex: "id"
            }, {
                header: "名称",
                dataIndex: "name"
            }, {
                header: "代码",
                dataIndex: "code"
            }, {
                header: "权限",
                dataIndex: "permissionIds",
                renderer: function (value, rowindex, record, column) {
                    return "<div>" +
                        "<div class=\"permissionBtn\">" +
                            //"<img src=\"/resources/component/permission/info.png\"></img>" +
                            "<a name='pe' href=\"javascript:void(0);\">编辑权限</a>" +
                        "</div>" +
                        "</div>";
                },
                sortable: false,
                menuDisabled: true
            }],

            bbar: Ext.create('Ext.PagingToolbar', {
                store: this.store,
                displayInfo: true,
                displayMsg: '显示 第{0}条 - 第{1}条, 总计:{2}条',
                emptyMsg: "暂无数据",
                items:[]
            }),

            dockedItems: [{
                xtype: 'toolbar',
                items: [{
                    iconCls: 'icon-plus',
                    text: '新增',
                    scope: this,
                    handler: this.onAddClick
                }, {
                    iconCls: 'icon-edit',
                    text: '修改',
                    disabled: true,
                    itemId: 'modify',
                    scope: this,
                    handler: this.onModifyClick
                }, {
                    iconCls: 'icon-remove',
                    text: '删除',
                    disabled: true,
                    itemId: 'delete',
                    scope: this,
                    handler: this.onDeleteClick
                }, "->", {
                    iconCls: 'icon-refresh',
                    text: '刷新',
                    scope: this,
                    handler: this.onRefreshClick
                }]
            }],

            listeners: {
                cellclick: function (component, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                    if(e.getTarget().name == 'pe') {
                        me.handle(component, cellIndex, rowIndex, record);
                    }
                }
            }
        });
        this.callParent();
        this.getSelectionModel().on('selectionchange', this.onSelectChange, this);
    },

    onRefreshClick: function () {
        this.store.reload();
    },

    onSelectChange: function(selModel, selections){
        this.down('#delete').setDisabled(selections.length === 0);
        this.down("#modify").setDisabled(selections.length === 0);
    },

    onDeleteClick: function(){
        var selections = this.getView().getSelectionModel().getSelection();
    },

    onAddClick: function(component) {
        var me = this,
            view = me.up("roleview");

        view.getEl().mask();
        var addWindow = Ext.create("MIS.view.role.RoleAdd", {
            title: "增加角色",
            renderTo: me.up("roleview").getEl(),
            listeners: {
                close: function () {
                    view.getEl().unmask();
                }
            }
        });

        addWindow.show();
    },

    onModifyClick: function (component) {
        var selections = this.getView().getSelectionModel().getSelection(),
            selectionNum = selections.length;

        if (selectionNum != 1) {
            Ext.MessageBox.alert("提示", "请选择一条记录进行编辑");
            return;
        };

        var record = selections[0],
            view = this.up("roleview")

        view.mask();
        var modifyWindow = Ext.create("MIS.view.role.RoleModify", {
            title: "修改角色",
            renderTo: view.getEl(),
            listeners: {
                close: function () {
                    view.getEl().unmask();
                }
            },
            modifyRecord: record
        });

        modifyWindow.show();
    },

    onEditPermission: function (component) {

    },

    onDeleteClick: function (component) {
        var selections = this.getView().getSelectionModel().getSelection(),
            selectionNum = selections.length;

        if (selectionNum == 0) {
            return;
        }

        var ids = [], names = [];
        for (var i = selections.length-1; i >= 0; i--) {
            var record = selections[i];
            ids.push(record.raw.id);
            names.push(record.raw.name);
        }

        Ext.MessageBox.alert("提示", "确定删除["+ names.join(",") +"]等角色", function (btn) {
            if (btn == "ok") {
                Ext.Ajax.request({
                    url: "/role/delete",
                    params: {
                        ids: ids.join(",")
                    },
                    success: function (response) {
                        var result = Ext.JSON.decode(response.responseText);
                        if (result && result.resultCode == 0) {
                            var grid = Ext.ComponentQuery.query("rolegrid")[0];
                            grid.getStore().reload();

                        } else {
                            Ext.MessageBox.alert("错误提示", "删除角色失败, 请稍后重试");
                        }   
                    },
                    failure: function (response) {
                        Ext.MessageBox.alert("错误提示", "删除角色失败");
                    }
                });
            }
        });
    },

    /**
     * 权限编辑事件
     */
    handle: function (component, x, y, record) {
        var view = component.up("roleview");

        view.getEl().mask();
        var treewindow = Ext.create("MIS.view.role.RoleEditPermission", {
            width: 800,
            height: 500,
            title: "角色["+ record.raw.name +"]的权限",
            renderTo: view.getEl(),
            listeners: {
                close: function () {
                    view.getEl().unmask();
                }
            },
            roleRecord: record,
            closeAction: "destroy"
        });
        treewindow.show();
    }
});
