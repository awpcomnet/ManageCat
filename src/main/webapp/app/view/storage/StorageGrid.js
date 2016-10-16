/**
 * 入库清单数据显示
 */
Ext.define("MIS.view.storage.StorageGrid", {
	extend: "Ext.grid.Panel",
	alias: "widget.storagegrid",
	
	requires: [
	     'Ext.form.field.Text',
	     'Ext.toolbar.TextItem',
	     'Ext.toolbar.Paging'
	],
	
	store: "MIS.store.storage.StorageStore",
	
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
			    { header: '品牌名称', dataIndex: 'brandEname', sortable: true, width: 15, align: "center"},
			    { header: '系列名称', dataIndex: 'seriesName', sortable: true, width: 10, align: "center", hidden : true},
			    { header: '单品名称', dataIndex: 'singleName', sortable: true, width: 10, align: "center"},
			    { header: '邮寄主单号', dataIndex: 'headTrackingNumber', sortable: true, width: 10, align: "center", hidden : true},
			    { header: '批次号', dataIndex: 'batchNo', sortable: true, width: 10, align: "center", hidden : true},
			    { header: '国外邮寄单号', dataIndex: 'trackingNumber', sortable: true, width: 10, align: "center", hidden : true},
			    { header: '原下单数量', dataIndex: 'num', sortable: true, width: 10, align: "center", hidden : true},
			    { header: '预计售价(￥)', dataIndex: 'planSellPrice', sortable: true, width: 10, align: "center", hidden : true, renderer: function (value, rowindex, record, column) {
			    	return value == '-1' ? '--' : value;
                }},
			    { header: '实际单价(￥)', dataIndex: 'unitRmb', sortable: true, width: 10, align: "center"},
			    { header: '实际单个邮费(￥)', dataIndex: 'unitPostage', sortable: true, width: 10, align: "center"},
			    { header: '实际成本(￥)', dataIndex: 'unitCost', sortable: true, width: 10, align: "center"},
			    { header: '剩余数量', dataIndex: 'residueNum', sortable: true, width: 10, align: "center"},
			    { header: '入库时间', dataIndex: 'storeTime', sortable: true, width: 10, align: "center"},
			    { header: '状态', dataIndex: 'storeStatus', sortable: true, width: 10, align: "center", renderer: function (value, rowindex, record, column) {
			    	return MIS.common.DictManager.getDictItemName("orderStatus", value);
                }},
			    { header: '备注', dataIndex: 'remark', sortable: true, width: 15, align: "center"}
			],
			
			viewConfig:{  
                enableTextSelection:true  
            },
		    
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
					iconCls: 'icon-share-alt',
					text: '售出',
					itemId: 'sell',
					scope: this,
					handler: this.onSellClick
				}, '-', {
					iconCls: 'icon-trash',
					text: '已损坏',
					itemId: 'destroy',
					scope: this,
					handler: this.onDestroyClick
				}, '-', {
					iconCls: 'icon-file-alt',
					text: '可售出查询',
					itemId: 'selling',
					scope: this,
					handler: this.onSellingClick
				}, {
					iconCls: 'icon-file-alt',
					text: '已售罄查询',
					itemId: 'selled',
					scope: this,
					handler: this.onSelledClick
				}, {
					iconCls: 'icon-file-alt',
					text: '全部查询',
					itemId: 'storageall',
					scope: this,
					handler: this.onStorageallClick
				}, '->', {
					iconCls: 'icon-folder-open-alt',
					text: '汇总',
					itemId: 'detail',
					scope: this,
					handler: this.onDetailClick
				},  {
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
	
	onRefreshClick: function(component){
		this.store.reload();
	},
	
	onModifyClick: function(component){
		var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	if(selectionNum != 1){
    		Ext.MessageBox.alert("请求失败", "请选择单条记录进行修改");
    		return;
    	}
    	
    	var storageview = Ext.ComponentQuery.query("storageview")[0];
    	storageview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "修改仓库记录["+selections[0].raw.singleName+"]",
        	id: "storagemodifywindow",
        	extraData: selections[0].raw,
        	renderTo: storageview.getEl(),
        	height: 330,
        	width: 580,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "storagemodify"
        	}],
        	listeners: {
        		close: function(){
        			storageview.getEl().unmask();
        		},
        		beforerender: function () {
        			
        		},
        		afterrender: function(component, eOpts){
        			var form = component.down("form");
                    var params = Ext.clone(this.extraData);
    				form.getForm().setValues(params);
    				
    				component.down("numberfield[name=planSellPrice]").setValue(params.planSellPrice == '-1' ? '':params.planSellPrice);
    			}
        	}
        });
    	editWindow.show();
	},
	
    onDeleteClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
        var selectionNum = selections.length;
        
        if (selectionNum < 1) {
        	Ext.MessageBox.alert("请求失败", "请至少选择一条记录进行删除");
            return;
        } 
        
        var ids = [];
        var i=0;
        for(;i<selectionNum;i++){
        	if(selections[i].raw.storeStatus != 2){
        		var statusText = MIS.common.DictManager.getDictItemName("orderStatus", selections[i].raw.storeStatus);
            	Ext.MessageBox.alert("请求失败", "["+selections[i].raw.singleName+"]订单状态不为[已入库],当前状态["+statusText+"]");
                return;
            }
        	
        	ids[i] = selections[i].raw.id;
        }
        

        //删除提示语言
        var tipText = "确定删除仓库清单,删除之后记录将会恢复至[已邮寄]状态。";
        
        Ext.MessageBox.confirm("删除提示", tipText, function (confirmId) {
        	if(confirmId == "yes"){
        		Ext.Ajax.request({
                	url: "/store/delete",
                	params: {
                		ids: ids
                	},
                	success: function(conn, request, option, eOpts){
                		var result = Ext.JSON.decode(conn.responseText, true);
                		if(result.resultCode != 0){
                			Ext.MessageBox.alert("请求失败", "删除仓库清单失败" + result.resultMessage);
                		} else {
                			Ext.ComponentQuery.query("storagegrid")[0].store.reload();
                		}
                	},
                	failure: function(conn, request, option, eOpts){
                		Ext.MessageBox.alert("请求失败", "服务器繁忙，稍后重试!");
                	}
                	
                });
        	}
        });
    },
    
    onSellClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	if(selectionNum != 1){
    		Ext.MessageBox.alert("请求失败", "请选择一条下单记录售出");
    		return;
    	}
    	
        var storageview = Ext.ComponentQuery.query("storageview")[0];
        storageview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "["+selections[0].raw.singleName+"]售出窗口",
        	id: "selledaddwindow",
        	extraData: selections[0].raw,
        	renderTo: storageview.getEl(),
        	height: 380,
        	width: 580,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "selledadd"
        	}],
        	listeners: {
        		close: function(){
        			storageview.getEl().unmask();
        		},
        		beforerender: function () {
        			
//        			var shippedsgrid = Ext.ComponentQuery.query("storagegrid")[0];
//        			var shippedsgridStore = shippedsgrid.getStore();
//        			shippedsgridStore.proxy.extraParams.headId = selections[0].raw.id;
//        			shippedsgridStore.load();
        		},
        		afterrender: function(component, eOpts){
        			component.down("textfield[name=unitCost]").setValue(selections[0].raw.unitCost);
        			component.down("textfield[name=residueNum]").setValue(selections[0].raw.residueNum);
        			component.down("numberfield[name=sellNum]").setValue(1);//售出默认1
        			component.down("numberfield[name=sellNum]").setMaxValue(selections[0].raw.residueNum);
        			component.down("textarea[name=remark]").setValue(selections[0].raw.remark);
        			component.down("textfield[name=storeId]").setValue(selections[0].raw.id);
        			component.down("numberfield[name=rate]").setValue(15);
        			//查询推荐售价
        			Ext.Ajax.request({
                    	url: "/selled/RecommendPrice",
                    	params: {
                    		storeId:  selections[0].raw.id,
                    		singleId: selections[0].raw.singleId
                    	},
                    	success: function(conn, request, option, eOpts){
                    		var result = Ext.JSON.decode(conn.responseText, true);
                    		if(result.resultCode == 0){
                    			component.down("numberfield[name=sellingPrice]").setValue(result.results[0].lastPrice);
                    			component.down("numberfield[name=sellingPriceReal]").setValue(result.results[0].lastPrice);
                    		}
                    	}
                    	
                    });
    			}
        	}
        });
    	editWindow.show();
    },
    
    onSellingClick: function(component){
    	this.store.proxy.extraParams.includeStatus = '2|4|6';
    	this.store.reload();
    },
    
    onSelledClick: function(component){
    	this.store.proxy.extraParams.includeStatus = '3';
    	this.store.reload();
    },
    
    onStorageallClick: function(component){
    	this.store.proxy.extraParams.includeStatus = '';
    	this.store.reload();
    },
    
    onDestroyClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	if(selectionNum != 1){
    		Ext.MessageBox.alert("请求失败", "请选择一条记录标记损坏");
    		return;
    	}
    	
        var storageview = Ext.ComponentQuery.query("storageview")[0];
        storageview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "["+selections[0].raw.singleName+"]已损坏窗口",
        	id: "destroywindow",
        	extraData: selections[0].raw,
        	renderTo: storageview.getEl(),
        	height: 260,
        	width: 580,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "destroyadd"
        	}],
        	listeners: {
        		close: function(){
        			storageview.getEl().unmask();
        		},
        		beforerender: function () {
//        			var shippedsgrid = Ext.ComponentQuery.query("storagegrid")[0];
//        			var shippedsgridStore = shippedsgrid.getStore();
//        			shippedsgridStore.proxy.extraParams.headId = selections[0].raw.id;
//        			shippedsgridStore.load();
        		},
        		afterrender: function(component, eOpts){
        			component.down("textfield[name=unitCost]").setValue(selections[0].raw.unitCost);
        			component.down("textfield[name=residueNum]").setValue(selections[0].raw.residueNum);
        			component.down("numberfield[name=sellNum]").setValue(1);
        			component.down("numberfield[name=sellNum]").setMaxValue(selections[0].raw.residueNum)
        			component.down("textarea[name=remark]").setValue(selections[0].raw.remark);
        			component.down("textfield[name=storeId]").setValue(selections[0].raw.id);
    			}
        	}
        });
    	editWindow.show();
    },
    
    onDetailClick: function(component){
        var storageview = Ext.ComponentQuery.query("storageview")[0];
        storageview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "仓库汇总信息",
        	//id: "shippedswindow",
        	extraData: "",
        	renderTo: storageview.getEl(),
        	height: 500,
        	width: 800,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "storagedetailgrid"
        	}],
        	listeners: {
        		close: function(){
        			storageview.getEl().unmask();
        		},
        		beforerender: function () {
        			var storagedetailgrid = Ext.ComponentQuery.query("storagedetailgrid")[0];
        			var storagedetailgridStore = storagedetailgrid.getStore();
        			storagedetailgridStore.load();
        		},
        		afterrender: function(component, eOpts){
        			
    			}
        	}
        });
    	editWindow.show();
    },
    
	onExpandSearchClick: function(component){
		var searchwindow = Ext.ComponentQuery.query("storageSearchpanel")[0];
    	if(searchwindow.isHidden()){
    		searchwindow.show();
    	} else {
    		this.store.proxy.extraParams = {};
    		this.store.proxy.extraParams.includeStatus = "2|4|6";
    		
    		
    		searchwindow.form.reset();
    		searchwindow.hide();
    	}
	}
	
});