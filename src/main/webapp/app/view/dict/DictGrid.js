/**
 * 描述: 字典数据Grid
 * 作者: 
 */
Ext.define("MIS.view.dict.DictGrid", {
    extend: "Ext.grid.Panel",
    alias: "widget.dictgrid",

    store: "MIS.store.dict.DictStore",

    dictType: "0",

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
                header: "名称",
                dataIndex: "name"
            }, {
                header: "代码",
                dataIndex: "code"
            }],

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
                }, {
                    iconCls: 'icon-refresh',
                    text: '刷新',
                    scope: this,
                    handler: this.onRefreshClick
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
        	tipText = "确定删除字典["+selections[0].raw.id+"-"+selections[0].raw.name+"]";
        } else {
        	tipText = "确定删除字典[";
        	for(var num=0; num<selectionNum; num++){
        		tipText += selections[num].raw.id+"-"+selections[num].raw.name;
        		if(num != selectionNum - 1){
        			tipText += ",";
        		}
        	}
        	tipText += "]";
        	
        }
        
        var i=0;
        for(; i<selectionNum; i++){
        	ids[i] = selections[i].raw.id;
        }
        
        Ext.MessageBox.confirm("删除提示", tipText, function (confirmId) {
        	if(confirmId == "yes"){
        		Ext.Ajax.request({
                	url: "/dict/delete",
                	params: {
                		id:ids
                	},
                	success: function(conn, request, option, eOpts){
                		var result = Ext.JSON.decode(conn.responseText, true);
                		if(result.resultCode != 0){
                			Ext.MessageBox.alert("请求失败", "删除字典失败"+ result.resultMessage);
                		} else {
                			Ext.ComponentQuery.query("dictgrid")[0].store.reload();
                			Ext.ComponentQuery.query("dictitemgrid")[0].store.reload();
                		}
                	},
                	failure: function(conn, request, option, eOpts){
                		Ext.MessageBox.alert("请求失败", "服务器繁忙，稍后重试!");
                	}
                });
        	}
        	
        });
        
    },

    onAddClick: function(component, eOpts){
        var dictview = component.up("dictview");
        dictview.getEl().mask();

        var addWindow = Ext.create("Ext.window.Window", {
            title: "新增字典",
            id: "dictaddwindow",
            renderTo: dictview.getEl(),
            height: 200,
            width: 400,
            layout: "fit",
            closeAction: "destroy",
            items: [{
                xtype: "dictadd"
            }],
            listeners: {
                close: function () {
                    dictview.getEl().unmask();
                }
            }
        });
        addWindow.show();
    },
    
    onModifyClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	if(selectionNum != 1){
    		Ext.MessageBox.alert("请求失败", "请选择单个字典进行修改");
    		return;
    	}
    	
    	var dictview = component.up("dictview");
    	dictview.getEl().mask();
    	
    	var modifyWindow = Ext.create("Ext.window.Window", {
    		title: "修改["+selections[0].raw.name+"]字典",
    		id: "dictmodifywindow",
    		renderTo: dictview.getEl(),
    		height: 200,
    		width: 400,
    		extraData: selections[0].raw,
    		layout: "fit",
    		closeAction: "destroy",
    		items: [{
    			xtype: "dictmodify"
    		}],
    		listeners: {
    			close: function(){
    				dictview.getEl().unmask();
    			},
    			afterrender: function(component, eOpts){
    				component.down("textfield[name=name]").setValue(this.extraData.name);
    				component.down("textfield[name=code]").setValue(this.extraData.code);
    				component.down("textfield[name=description]").setValue(this.extraData.description);
    			}
    		}
    	});
    	modifyWindow.show();
    }
});
