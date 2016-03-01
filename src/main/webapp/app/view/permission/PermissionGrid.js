/**
 * 描述: 权限数据展示表格
 * 作者: 
 */
Ext.define("MIS.view.permission.PermissionGrid", {
    extend: "Ext.grid.Panel",
    alias: "widget.permissiongrid",

    layout: {
        type: "fit"
    },

    store: "MIS.store.permission.PermissionStore",

    catalogId: 0,
    catalogRecord: null,

    initComponent: function(){
        //创建多选框  
        var checkBox = Ext.create('Ext.selection.CheckboxModel');   

        Ext.apply(this, {
            frame: false,
        
            forceFit: true,
            scrollOffset: 0,

            selModel:checkBox,  
            disableSelection: false,//表示可以选择行  
            columnLines: true,
            loadMask: true,   

            columns: [
                { header: '编号', dataIndex: 'id', sortable: true, hideable: false, width: 30, align: "center"},
                { header: '权限名称', dataIndex: 'name', sortable: true},
                { header: '权限代码', dataIndex: 'code', sortable: true},
                { header: '类型', dataIndex: 'type', sortable: false},
                { header: '关联资源地址', dataIndex: 'url', sortable: false},
                { 
                    header: '所属栏目ID', dataIndex: 'catalogId', sortable: false
                }
            ],

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
                }, {
                    iconCls: ' icon-double-angle-down',
                    text: '展开搜索',
                    itemId: 'expand-search',
                    scope: this,
                    handler: this.onExpandSearchClick
                }]
            }]
        });
        this.callParent();
        this.getSelectionModel().on('selectionchange', this.onSelectChange, this);
    },

    /**
     * 选择发生变化事件处理器
     */
    onSelectChange: function(selModel, selections){
        this.down('#delete').setDisabled(selections.length === 0);
        this.down("#modify").setDisabled(selections.length === 0);
    },

    /**
     * 刷新数据面板
     */
    onRefreshClick: function (component) {
        var me = this;
        me.getStore().reload();
    },
    
    /**
     * 隐藏 展开 搜索区域
     */
    onExpandSearchClick: function (component) {
        var me = this,
            search = me.up("permissionview").down("permissionsearch");

        if (search.showState) {
            search.showState = false;
            search.hide();
            component.setText("展开搜索");

            me.getStore().proxy.extraParams.name = "";
            me.getStore().proxy.extraParams.type = "";
            me.getStore().proxy.extraParams.code = "";
            me.getStore().proxy.extraParams.url  = "";
        } else {
            search.showState = true;
            search.show();
            component.setText("隐藏搜索");
        }
    },

    /**
     * 新增按钮事件处理器
     */
    onAddClick: function (component) {
        var me = this,
            view = me.up("permissionview");

        if (me.catalogId == 0 || !me.catalogRecord.data.leaf) {
            Ext.MessageBox.alert("提示", "权限只能增加至实际业务栏目!");
            return;
        }

        view.getEl().mask();
        var addWindow = Ext.create("MIS.view.permission.PermissionAdd", {
            renderTo: view.getEl(),
            tempData: {
                catalogId: me.catalogId
            }
        });

        addWindow.show();
    },

    /**
     * 修改按钮点击事件处理器
     */
    onModifyClick: function (component) {
        var selections = this.getView().getSelectionModel().getSelection(),
            selectionNum = selections.length;

        if (selectionNum != 1) {
            Ext.MessageBox.alert("提示", "请选择一条记录进行编辑");
            return;
        }

        var view = component.up("permissionview");
        view.getEl().mask();

        var record = selections[0],
            window = Ext.create("MIS.view.permission.PermissionModify", {
                renderTo: view.getEl(),
                tempData: {
                    record: record
                }
            });

        window.show();
    },

    /**
     * 
     */
    onDeleteClick: function () {
        var selections = this.getView().getSelectionModel().getSelection(),
            selectionNum = selections.length,
            me = this;

        if (selectionNum == 0) {
            return;
        }

        var ids = [];
        var names = [];
        for (var i = selectionNum - 1; i >= 0; i--) {
            var record = selections[i];
            ids.push(record.raw.id);
            names.push(record.raw.name);
        }

        Ext.MessageBox.alert("提示", "确定删除 ["+ names.join(",") +"] 等记录", function (btn) {
            if (btn == "ok") {
                Ext.Ajax.request({
                    url: "/permission/delete",
                    params: {
                        ids: ids.join(",")
                    },
                    success: function (response) {
                        var result = Ext.JSON.decode(response.responseText);
                        if (result && result.resultCode == 0) {
                            me.getStore().reload();

                        } else {
                            Ext.MessageBox.alert("错误提示", "删除数据错误");
                        }
                    }, 
                    failure: function (response) {
                        Ext.MessageBox.alert("错误提示", "Http通信出错");
                    }
                }); 
            }
        });
    }
});
