/**
 * 描述: 用于展示用户数据的DataGrid
 * 作者: 
 */
Ext.define("MIS.view.user.UserGrid", {
	extend: "Ext.grid.Panel",
	alias: "widget.usergrid",

    requires: [
        'Ext.form.field.Text',
        'Ext.toolbar.TextItem',
        'Ext.toolbar.Paging'
    ],

    id: "id_usergrid",

    layout: {
        type: "fit"
    },

    store: Ext.create("MIS.store.user.UserStore"),

    status: {
        searchState: "true"
    },

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
                { header: '编号', dataIndex: 'userId', sortable: true, hideable: false, width: 30, align: "center"},
                { header: '用户名', dataIndex: 'username', sortable: true},
                { header: '真实姓名', dataIndex: 'realname', sortable: true},
                { header: '角色', dataIndex: 'role', sortable: false},
                { 
                    header: '状态', dataIndex: 'state', sortable: false,
                    renderer: function (value, rowindex, record, column) {
                        return MIS.common.DictManager.getDictItemName("user.state", value);
                    }
                }

//                { header: '最后登录日期', dataIndex: 'lastLoginDate', sortable: true},
//                { header: '创建日期', dataIndex: 'createDate', sortable: true}
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
                    iconCls: 'icon-key',
                    text: '重置用户密码',
                    disabled: false,
                    itemId: 'resetPassword',
                    scope: this,
                    handler: this.onResetPasswordClick
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

    onRefreshClick: function () {
        this.store.reload();
    },

    onSelectChange: function(selModel, selections){
        this.down('#delete').setDisabled(selections.length === 0);
        this.down("#modify").setDisabled(selections.length === 0);
    },

    onDeleteClick: function() {
        var me = this;
        var selections = this.getView().getSelectionModel().getSelection();
        var selectionNum = selections.length;

        if (selectionNum != 1) {
        	Ext.MessageBox.alert("错误信息", "请选择单个用户删除");
            return;
        } 

//        var delList = [];
//        var delNameList = [];
//        for (var i = selections.length - 1; i >= 0; i--) {
//            var sel = selections[i];
//            delList.push(sel.raw.userId);
//            delNameList.push(sel.raw.username);
//        }

        Ext.MessageBox.alert(
            "确认信息",
//            "确认删除[" + delNameList.join(", ") + "]等用户信息",
            "确认删除[" + selections[0].raw.username + "]等用户信息",
            function (btn) {
                if (btn == "ok") {
                    Ext.Ajax.request({
                        url: "/user/delete",
                        params: {
                            id: selections[0].raw.userId
                        },
                        success: function (response) {
                        	debugger;
                            var result = Ext.JSON.decode(response.responseText);
                            if (result.resultCode == 0) {
                                me.getStore().reload();
                            } else {
                                Ext.MessageBox.alert("错误信息", !result ? "删除出错, 稍后重试" : result.resultMessage);
                            }
                        },
                        failure: function (response) {
                            Ext.MessageBox.alert("错误信息", "Http通信失败");
                        }
                    });
                }
            }
        );
    },

    /**
     * 增加用户按钮点击事件处理
     */
    onAddClick: function() {
        var view = this.up("userview");
        view.getEl().mask();

        var addUserWindow = Ext.create("MIS.view.user.UserDetailsWindow", {
            renderTo: view.getEl(),
            actionMode: "ADD",
            listeners: {
                close: function () {
                    view.getEl().unmask();
                }
            }
        });
        addUserWindow.show();
    },

    /**
     * 增加用户修改按钮的点击事件
     */
    onModifyClick: function () {
        var selection = this.getView().getSelectionModel().getSelection(),
            selectionNum = selection.length;

        if (selectionNum != 1) {
            Ext.MessageBox.alert("错误提示", "请选择一个用户进行修改");
            return;
        }

        var view = this.up("userview");
        view.getEl().mask();

        var record = selection[0];
        console.log(record);
        var modifyUserWindow = Ext.create("MIS.view.user.UserDetailsWindow", {
            renderTo: view.getEl(),
            actionMode: "MODIFY",
            listeners: {
                close: function () {
                    view.getEl().unmask();
                }
            },
            modifyModeDetails: record
        });
        modifyUserWindow.show();
    },  

    onExpandSearchClick: function (component) {
        var searchPanel = component.up("userview").down("usersearch"),
            searchShow = searchPanel.hidden;

        if (searchShow) {
            searchPanel.show();
            component.setText("关闭搜索");
        } else {
            searchPanel.hide();
            component.setText("展开搜索");
        }
    }
});
