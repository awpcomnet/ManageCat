/**
 * 描述: 展示栏目数据
 * 作者: 
 */
Ext.define("MIS.view.catalog.CatalogGrid", {
    extend: "Ext.grid.Panel",
    alias: "widget.cataloggrid",

    title: "系统栏目",
    store: "MIS.store.catalog.CatalogInfoStore",

    initComponent: function () {
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

            columns: [{
                header: "编号",
                dataIndex: "id"
            }, {
                header: "名称",
                dataIndex: "text"
            }, {
                header: "代码",
                dataIndex: "abbr"
            }, {
                header: "栏目类型",
                dataIndex: "urlType"
            }, {
                header: "栏目地址",
                dataIndex: "url"
            }, {
                header: "描述",
                dataIndex: "description"
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
                }, {
                    iconCls: ' icon-double-angle-up',
                    text: '搜索',
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
        console.log(this.store);
        this.store.reload();
    },

    onSelectChange: function(selModel, selections){
        this.down('#delete').setDisabled(selections.length === 0);
        this.down("#modify").setDisabled(selections.length === 0);
    },

    onDeleteClick: function(){
        var selections = this.getView().getSelectionModel().getSelection();
        var selectionNum = selections.length;
        var ids = [];
        if (selectionNum <= 0) {
            return;
        }
        
        //删除提示语言
        var tipText = "";
        if(selectionNum == 1){
        	tipText = "确定删除栏目["+selections[0].raw.id+"-"+selections[0].raw.text+"]";
        } else {
        	tipText = "确定删除栏目[";
        	for(var num=0; num<selectionNum; num++){
        		tipText += selections[num].raw.id+"-"+selections[num].raw.text;
        		if(num != selectionNum - 1){
        			tipText += ",";
        		}
        	}
        	tipText += "]";
        	
        }
        
        var i = 0;
        for(;i<selectionNum; i++){
        	ids[i] = selections[i].raw.id;
        }
        
        Ext.MessageBox.confirm("删除提示", tipText, function (confirmId) {
        	if (confirmId == "yes") {
        		Ext.Ajax.request({
                	url: "/catalog/deletes",
                	params: {
                		id: ids
                	},
                	success: function(conn, request, option, eOpts){
                		var result = Ext.JSON.decode(conn.responseText, true);
                		if(result.resultCode != 0){
                			Ext.MessageBox.alert("请求失败", "删除节点失败"+result.resultMessage);
                		} else {
                			Ext.ComponentQuery.query("catalogtree")[0].store.reload();
                			Ext.ComponentQuery.query("cataloggrid")[0].store.reload();
                		}
                	},
                	failure: function(conn, request, option, eOpts){
                		Ext.MessageBox.alert("请求失败", "服务器繁忙，稍后重试!");
                	}
                });
        	}
        });
    },

    onAddClick: function(component){
    	var catalogView = component.up("catalogview");
    	catalogView.getEl().mask();
    	
        var addWindow = Ext.create("MIS.view.catalog.CatalogAdd", {
        	title: "新增 栏目[" + this.selectedText + "] 的子栏目",
        	parentId: this.selectedId,
        	opType: "A",
        	closeAction: "destroy",
        	listeners: {
        		close: function(){
        			catalogView.getEl().unmask();
        		}
        	}
        		
        });
        
        addWindow.show();
    },
    
    onModifyClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	if(selectionNum != 1){
    		Ext.MessageBox.alert("请求失败", "请选择单个栏目进行修改");
    		return;
    	}
    	
    	var catalogview = Ext.ComponentQuery.query("catalogview")[0];
    	catalogview.getEl().mask();
    	
    	var title = "编辑 ["+selections[0].raw.text+"] 栏目";
    	var editWindow = Ext.create("MIS.view.catalog.CatalogModify", {
    		title: title,
    		extraData: selections[0].raw,
    		id: "catalogmodifywindow",
    		opType: "A",
    		
    		listeners: {
    			close: function () {
    				catalogview.getEl().unmask();
                },
    			afterrender: function(component, eOpts){
    				component.down("textfield[name=name]").setValue(this.extraData.text);
    				component.down("textfield[name=abbr]").setValue(this.extraData.abbr);
    				component.down("textfield[name=description]").setValue(this.extraData.description);
    				component.down("checkbox[name=state]").setValue(this.extraData.state);
    				component.down("textfield[name=icon]").setValue(this.extraData.iconCls);
    				component.down("numberfield[name=orderNum]").setValue(this.extraData.orderNum);
    				component.down("textfield[name=urlType]").setValue(this.extraData.urlType);
    				component.down("textfield[name=url]").setValue(this.extraData.url);
    			}
    		}
    	});
    	editWindow.show();
    },
    
    onExpandSearchClick: function(component){
    	var searchwindow = Ext.ComponentQuery.query("#catalogsearchpanel")[0];
    	
    	if (searchwindow.isHidden()) {
    		searchwindow.show();
    	} else {
    		searchwindow.form.reset();
    		searchwindow.hide();
    	}

    }
});
