/**
 * 系列数据显示
 */
Ext.define("MIS.view.series.SeriesGrid", {
	extend: "Ext.grid.Panel",
	alias: "widget.seriesgrid",
	
	requires: [
	     'Ext.form.field.Text',
	     'Ext.toolbar.TextItem',
	     'Ext.toolbar.Paging'
	],
	
	store: "MIS.store.series.SeriesStore",
	
	initComponent: function(){
		//创建多选框
		var checkBox = Ext.create('Ext.selection.CheckboxModel');
		
		Ext.apply(this, {
			frame: false,
			
			forceFit: true,
			scrollOffSet: 0,
			
			selModel: checkBox,
			disableSelection: false, //表示可以选择行
			columnLines: true,
			loadMask: true,
			
			columns: [
			    { header: '系列名称', dataIndex: 'seriesName', sortable: true, width: 20, align: "center"},
			    { header: '系列英文名', dataIndex: 'seriesEname', sortable: true, width: 20, align: "center"},
			    { header: '所属品牌', dataIndex: 'ofOriginEname', sortable: true, width: 20, align: "center"},
			    { header: '是否可用', dataIndex: 'isUse', sortable: true, width: 20, align: "center", renderer: function (value, rowindex, record, column) {
			    	return MIS.common.DictManager.getDictItemName("onOff", value);
                }},
			    { header: '创建时间', dataIndex: 'createDateFormat', sortable: true, width: 25, align: "center"},
			    { header: '修改时间', dataIndex: 'updateDateFormat', sortable: true, width: 25, align: "center"}
			],
		    
			bbar: Ext.create('Ext.PagingToolbar', {
				store: this.store,
				displayInfo: true,
				displayMsg: '显示 第{0}条 - 第{1}条, 总计:{2}条',
				emptyMsg: "暂无数据",
				items: []
			}),
			
			dockedItems: [{
				xtype: 'toolbar',
				items: [{
					iconCls: 'icon-plus',
					text: '新增',
					itemId: 'add',
					scope: this,
					handler: this.onAddClick
				}, {
					iconCls: 'icon-edit',
					text: '修改',
					itemId: 'modify',
					scope: this,
					handler: this.onModifyClick
				}, {
					iconCls: 'icon-remove',
					text: '删除',
					itemId: 'delete',
					scope: this,
					handler: this.onDeleteClick
				}, '-', {
					iconCls: 'icon-file-alt',
					text: '添加单品',
					itemId: 'singleAdd',
					scope: this,
					handler: this.onSingleAddClick
				}, {
					iconCls: 'icon-reply',
					text: '恢复系列修改',
					itemId: 'seriesRecover',
					scope: this,
					handler: this.onRecoverClick
				}, '-', {
					iconCls: 'icon-upload',
					text: '同步系列',
					itemId: 'seriesSync',
					scope: this,
					handler: this.onSyncClick
				}, {
					iconCls: 'icon-download',
					text: '恢复同步',
					itemId: 'seriesRecoverSync',
					scope: this,
					handler: this.onRecoverSyncClick
				}, '->', {
					iconCls: 'icon-refresh',
					text: '刷新',
					scope: this,
					handler: this.onRefreshClick
				}, {
					iconCls: 'icon-double-angle-up',
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
	
	onSelectChange: function(selModel, selections){
//        this.down('#pause').setDisabled(selections.length === 0);
    },
	
	onAddClick: function(grid, rindex, cindex){
    	var seriesview = Ext.ComponentQuery.query("seriesview")[0];
    	seriesview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "添加系列",
        	id: "seriesaddwindow",
        	renderTo: seriesview.getEl(),
        	height: 180,
        	width: 580,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "seriesadd"
        	}],
        	listeners: {
        		close: function(){
        			seriesview.getEl().unmask();
        		},
        		beforerender: function () {
        			var ofOrigin = Ext.ComponentQuery.query("seriesadd combo[name=ofOrigin]")[0];
                    var ofOriginStore = ofOrigin.getStore();
                    ofOriginStore.proxy.extraParams.isUse = 1;
                    ofOriginStore.load();
                    
                    var isUse = Ext.ComponentQuery.query("seriesadd combo[name=isUse]")[0];
                    var isUseStore = isUse.getStore();
                    isUseStore.load();
        		},
        		afterrender: function(component, eOpts){
        			component.down("combo[name=isUse]").setValue("1");
    			}
        	}
        });
    	editWindow.show();
		
	},
	
	onRefreshClick: function(component){
		this.store.reload();
	},
	
	onModifyClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	if(selectionNum != 1){
    		Ext.MessageBox.alert("请求失败", "请选择单个系列进行修改");
    		return;
    	}
    	var seriesview = Ext.ComponentQuery.query("seriesview")[0];
    	seriesview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "修改系列",
        	id: "seriesmodifywindow",
        	extraData: selections[0].raw,
        	renderTo: seriesview.getEl(),
        	height: 180,
        	width: 580,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "seriesmodify"
        	}],
        	listeners: {
        		close: function(){
        			seriesview.getEl().unmask();
        		},
        		beforerender: function () {
        			var ofOrigin = Ext.ComponentQuery.query("seriesmodify combo[name=ofOrigin]")[0];
                    var ofOriginStore = ofOrigin.getStore();
                    ofOriginStore.proxy.extraParams.isUse = 1;
                    ofOriginStore.load();
                    
                    var isUse = Ext.ComponentQuery.query("seriesmodify combo[name=isUse]")[0];
                    var isUseStore = isUse.getStore();
                    isUseStore.load();
        		},
        		afterrender: function(component, eOpts){
        			var form = component.down("form");
                    var params = Ext.clone(this.extraData);
    				form.getForm().setValues(params);
    				component.down("combo[name=ofOrigin]").setValue(Number.parseInt(this.extraData.ofOrigin));
    			}
        	}
        });
    	editWindow.show();
    },
    
    onSingleAddClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	if(selectionNum != 1){
    		Ext.MessageBox.alert("请求失败", "请选择一个系列进行单品添加");
    		return;
    	}
    	var isUse = selections[0].raw.isUse;
    	if(isUse != 1){
    		Ext.MessageBox.alert("请求失败", "系列已失效");
    		return;
    	}
    	
    	var seriesview = Ext.ComponentQuery.query("seriesview")[0];
    	seriesview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "添加单品,所属系列["+selections[0].raw.seriesName+"]",
        	id: "singleaddwindow",
        	extraData: selections[0].raw,
        	renderTo: seriesview.getEl(),
        	height: 250,
        	width: 575,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "singleadd"
        	}],
        	listeners: {
        		close: function(){
        			seriesview.getEl().unmask();
        		},
        		beforerender: function () {
        			var ofOrigin = Ext.ComponentQuery.query("singleadd combo[name=ofOrigin]")[0];
                    var ofOriginStore = ofOrigin.getStore();
                    ofOriginStore.proxy.extraParams.isUse = 1;
                    ofOriginStore.load();
        			
                    var isUse = Ext.ComponentQuery.query("singleadd combo[name=isUse]")[0];
                    var isUseStore = isUse.getStore();
                    isUseStore.load();
               },
        		afterrender: function(component, eOpts){
        			component.down("combo[name=ofOrigin]").setValue(Number.parseInt(this.extraData.seriesId));
        			component.down("combo[name=isUse]").setValue("1");
    			}
        	}
        });
    	editWindow.show();
    },
    
    onRecoverClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
        var selectionNum = selections.length;
        
        if (selectionNum != 1) {
        	Ext.MessageBox.alert("请求失败", "请选择单条记录进行恢复");
            return;
        } 
        
        //删除提示语言
        var tipText = "确定恢复系列信息["+selections[0].raw.seriesName+"]。注意:仅能恢复一次最近修改记录。";
        
        Ext.MessageBox.confirm("恢复提示", tipText, function (confirmId) {
        	if(confirmId == "yes"){
        		Ext.Ajax.request({
                	url: "/Series/recover",
                	params: {
                		seriesId: selections[0].raw.seriesId
                	},
                	success: function(conn, request, option, eOpts){
                		var result = Ext.JSON.decode(conn.responseText, true);
                		if(result.resultCode != 0){
                			Ext.MessageBox.alert("请求失败", "原因：" + result.resultMessage);
                		} else {
                			Ext.ComponentQuery.query("seriesgrid")[0].store.reload();
                		}
                	},
                	failure: function(conn, request, option, eOpts){
                		Ext.MessageBox.alert("请求失败", "服务器繁忙，稍后重试!");
                	}
                	
                });
        	}
        });
    },
    
    onSyncClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
        var selectionNum = selections.length;
        
        if (selectionNum != 1) {
        	Ext.MessageBox.alert("请求失败", "请选择单条记录进行同步");
            return;
        } 
        
        //提示语言
        var tipText = "确定同步系列["+selections[0].raw.seriesName+"]。注意:系列信息将同步到[下单][邮寄][入库][售出]清单中，您将可以使用恢复同步功能进行数据恢复，该功能仅影响各个清单中的[品牌][系列]。";
        
        Ext.MessageBox.confirm("同步提示", tipText, function (confirmId) {
        	if(confirmId == "yes"){
        		Ext.Ajax.request({
                	url: "/Series/sync",
                	params: {
                		seriesId: selections[0].raw.seriesId
                	},
                	success: function(conn, request, option, eOpts){
                		var result = Ext.JSON.decode(conn.responseText, true);
                		if(result.resultCode != 0){
                			Ext.MessageBox.alert("请求失败", "同步系列失败" + result.resultMessage);
                		} else {
                			Ext.MessageBox.alert("同步成功", result.resultMessage);
                			Ext.ComponentQuery.query("seriesgrid")[0].store.reload();
                		}
                	},
                	failure: function(conn, request, option, eOpts){
                		Ext.MessageBox.alert("请求失败", "服务器繁忙，稍后重试!");
                	}
                	
                });
        	}
        });
    },
    
    onRecoverSyncClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
        var selectionNum = selections.length;
        
        if (selectionNum != 1) {
        	Ext.MessageBox.alert("请求失败", "请选择单条记录进行恢复同步");
            return;
        } 
        
        //提示语言
        var tipText = "确定恢复同步系列["+selections[0].raw.seriesName+"]。注意:该功能仅影响各个清单中的[品牌][系列][单品]，数据会恢复至最近一次同步。";
        
        Ext.MessageBox.confirm("恢复同步提示", tipText, function (confirmId) {
        	if(confirmId == "yes"){
        		Ext.Ajax.request({
                	url: "/Series/recoverSync",
                	params: {
                		seriesId: selections[0].raw.seriesId
                	},
                	success: function(conn, request, option, eOpts){
                		var result = Ext.JSON.decode(conn.responseText, true);
                		if(result.resultCode != 0){
                			Ext.MessageBox.alert("请求失败", "原因：" + result.resultMessage);
                		} else {
                			Ext.MessageBox.alert("恢复同步成功", result.resultMessage);
                			Ext.ComponentQuery.query("seriesgrid")[0].store.reload();
                		}
                	},
                	failure: function(conn, request, option, eOpts){
                		Ext.MessageBox.alert("请求失败", "服务器繁忙，稍后重试!");
                	}
                	
                });
        	}
        });
    },
    
    onDeleteClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
        var selectionNum = selections.length;
        
        if (selectionNum != 1) {
        	Ext.MessageBox.alert("请求失败", "请选择单条记录进行删除");
            return;
        } 
        
        //删除提示语言
        var tipText = "确定删除系列["+selections[0].raw.seriesName+"]。注意:将同时删除该系列下的单品信息。";
        
        Ext.MessageBox.confirm("删除提示", tipText, function (confirmId) {
        	if(confirmId == "yes"){
        		Ext.Ajax.request({
                	url: "/Series/delete",
                	params: {
                		seriesId: selections[0].raw.seriesId
                	},
                	success: function(conn, request, option, eOpts){
                		var result = Ext.JSON.decode(conn.responseText, true);
                		if(result.resultCode != 0){
                			Ext.MessageBox.alert("请求失败", "删除系列失败" + result.resultMessage);
                		} else {
                			Ext.ComponentQuery.query("seriesgrid")[0].store.reload();
                		}
                	},
                	failure: function(conn, request, option, eOpts){
                		Ext.MessageBox.alert("请求失败", "服务器繁忙，稍后重试!");
                	}
                	
                });
        	}
        });
    },
	
	onExpandSearchClick: function(component){
		var searchwindow = Ext.ComponentQuery.query("seriesSearchpanel")[0];
    	if(searchwindow.isHidden()){
    		var ofOrigin = Ext.ComponentQuery.query("seriesSearchpanel combo[name=ofOrigin]")[0];
            var ofOriginStore = ofOrigin.getStore();
            ofOriginStore.proxy.extraParams.isUse = 1;
            ofOriginStore.load();
            
    		searchwindow.show();
    	} else {
    		this.store.proxy.extraParams = {};
    		
    		searchwindow.form.reset();
    		searchwindow.hide();
    	}
	}
	
});