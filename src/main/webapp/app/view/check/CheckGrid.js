/**
 * 下单清单数据显示
 */
Ext.define("MIS.view.check.CheckGrid", {
	extend: "Ext.grid.Panel",
	alias: "widget.checkgrid",
	
	requires: [
	     'Ext.form.field.Text',
	     'Ext.toolbar.TextItem',
	     'Ext.toolbar.Paging'
	],
	
	store: "MIS.store.check.CheckStore",
	
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
			    { header: '批次号', dataIndex: 'batchNo', sortable: true, width: 10, align: "center"},
			    { header: '快递单号', dataIndex: 'trackingNumber', sortable: true, width: 15, align: "center"},
			    { header: '下单时间', dataIndex: 'orderTime', sortable: true, width: 10, align: "center"},
			    { header: '转运公司', dataIndex: 'transferCompany', sortable: true, width: 10, align: "center", renderer: function (value, rowindex, record, column) {
			    	return MIS.common.DictManager.getDictItemName("transferCompany", value);
                }},
			    { header: '下单网站', dataIndex: 'orderAddr', sortable: true, width: 10, align: "center", renderer: function (value, rowindex, record, column) {
			    	return MIS.common.DictManager.getDictItemName("orderAddr", value);
                }},
			    { header: '品牌名称', dataIndex: 'brandEname', sortable: true, width: 15, align: "center"},
			    { header: '系列名称', dataIndex: 'seriesName', sortable: true, width: 15, align: "center", hidden : true},
			    { header: '汇率', dataIndex: 'rate', sortable: true, width: 15, align: "center", hidden : true, renderer: function (value, rowindex, record, column) {
			    	return value == '' ? '--' : value;
                }},
			    { header: '币种', dataIndex: 'currency', sortable: true, width: 15, align: "center", hidden : true, renderer: function (value, rowindex, record, column) {
			    	return MIS.common.DictManager.getDictItemName("currency", value);
                }},
			    { header: '单品名称', dataIndex: 'singleName', sortable: true, width: 15, align: "center"},
			    { header: '规格', dataIndex: 'specification', sortable: true, width: 6, align: "center", renderer: function (value, rowindex, record, column) {
			    	return value == ' ' ? '--' : value;
                }},
			    { header: '数量', dataIndex: 'num', sortable: true, width: 5, align: "center"},
			    { header: '单价($)', dataIndex: 'unitPrice', sortable: true, width: 10, align: "center"},
			    { header: '总价($)', dataIndex: 'sumPrice', sortable: true, width: 10, align: "center"},
			    { header: '付款人', dataIndex: 'payby', sortable: true, width: 10, align: "center"},
			    { header: '备注', dataIndex: 'remark', sortable: true, width: 10, align: "center"},
			    { header: '订单状态', dataIndex: 'orderStatus', sortable: true, width: 10, align: "center", renderer: function (value, rowindex, record, column) {
			    	return MIS.common.DictManager.getDictItemName("orderStatus", value);
                }}
			    
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
					iconCls: 'icon-magic',
					text: '强制修改',
					itemId: 'modifyForce',
					scope: this,
					handler: this.onModifyForceClick
				}, {
					iconCls: 'icon-remove',
					text: '删除',
					itemId: 'delete',
					scope: this,
					handler: this.onDeleteClick
				}, '-', {
					iconCls: 'icon-arrow-up',
					text: '一键邮寄',
					itemId: 'onPost',
					scope: this,
					handler: this.onPostClick
				}, '-', {
					iconCls: 'icon-file-alt',
					text: '已下单清单',
					itemId: 'partA',
					scope: this,
					handler: this.onQueryPartAClick
				}, {
					iconCls: 'icon-file-alt',
					text: '已邮寄清单',
					itemId: 'partB',
					scope: this,
					handler: this.onQueryPartBClick
				}, {
					iconCls: 'icon-file-alt',
					text: '已入库清单',
					itemId: 'partC',
					scope: this,
					handler: this.onQueryPartCClick
				}, {
					iconCls: 'icon-file-alt',
					text: '已售出清单',
					itemId: 'partD',
					scope: this,
					handler: this.onQueryPartDClick
				}, '-', {
					iconCls: 'icon-file-alt',
					text: '所有清单记录',
					itemId: 'all',
					scope: this,
					handler: this.onQueryAllClick
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
		var checkview = Ext.ComponentQuery.query("checkview")[0];
		checkview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "添加下单清单",
        	id: "checkaddwindow",
        	renderTo: checkview.getEl(),
        	height: 330,
        	width: 835,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "checkadd"
        	}],
        	listeners: {
        		close: function(){
        			checkview.getEl().unmask();
        		},
        		beforerender: function () {
        			var payby = Ext.ComponentQuery.query("checkadd combobox[name=payby]")[0];
                    var paybyStore = payby.getStore();
                    paybyStore.load();
                    
                    var currency = Ext.ComponentQuery.query("checkadd combobox[name=currency]")[0];
                    var currencyStore = currency.getStore();
                    currencyStore.load();
        		},
        		afterrender: function(component, eOpts){
        			
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
    		Ext.MessageBox.alert("请求失败", "请选择单条记录进行修改");
    		return;
    	}
    	
    	//只有状态为已下单才可修改订单信息
    	if(selections[0].raw.orderStatus != "0"){
    		Ext.MessageBox.alert("请求失败", "订单状态错误！【已下单】才可修改");
    		return;
    	}
    	
    	var checkview = Ext.ComponentQuery.query("checkview")[0];
    	checkview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "修改下单清单",
        	id: "checkmodifywindow",
        	extraData: selections[0].raw,
        	renderTo: checkview.getEl(),
        	height: 330,
        	width: 835,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "checkmodify"
        	}],
        	listeners: {
        		close: function(){
        			checkview.getEl().unmask();
        		},
        		beforerender: function () {
               	 	var transferCompany = Ext.ComponentQuery.query("checkmodify combobox[name=transferCompany]")[0];
                    var transferCompanyStore = transferCompany.getStore();
                    transferCompanyStore.load();
                    
                    var orderAddr = Ext.ComponentQuery.query("checkmodify combobox[name=orderAddr]")[0];
                    var orderAddrStore = orderAddr.getStore();
                    orderAddrStore.load();
                    
                    var brandId = Ext.ComponentQuery.query("checkmodify combobox[name=brandId]")[0];
                    var brandIdStore = brandId.getStore();
                    brandIdStore.load();
                    
                    var seriesId = Ext.ComponentQuery.query("checkmodify combobox[name=seriesId]")[0];
                    var seriesIdStore = seriesId.getStore();
                    seriesIdStore.load();
                    
                    var singleId = Ext.ComponentQuery.query("checkmodify combobox[name=singleId]")[0];
                    var singleIdStore = singleId.getStore();
                    singleIdStore.load();
                    
                    var payby = Ext.ComponentQuery.query("checkmodify combobox[name=payby]")[0];
                    var paybyStore = payby.getStore();
                    paybyStore.load();
                    
                    var currency = Ext.ComponentQuery.query("checkmodify combobox[name=currency]")[0];
                    var currencyStore = currency.getStore();
                    currencyStore.load();
               },
        		afterrender: function(component, eOpts){
        			var form = component.down("form");
                    var params = Ext.clone(this.extraData);
    				form.getForm().setValues(params);
    				component.down("combobox[name=seriesId]").setValue(Number.parseInt(this.extraData.seriesId));
    				component.down("combobox[name=singleId]").setValue(Number.parseInt(this.extraData.singleId));
    				component.down("combobox[name=currency]").setValue(this.extraData.currency+'');
    			}
        	}
        });
    	editWindow.show();
    },
    
    onModifyForceClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	if(selectionNum != 1){
    		Ext.MessageBox.alert("请求失败", "请选择单条记录进行修改");
    		return;
    	}
    	
    	var checkview = Ext.ComponentQuery.query("checkview")[0];
    	checkview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "强制修改下单清单",
        	id: "checkforcemodifywindow",
        	extraData: selections[0].raw,
        	renderTo: checkview.getEl(),
        	height: 330,
        	width: 835,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "checkforcemodify"
        	}],
        	listeners: {
        		close: function(){
        			checkview.getEl().unmask();
        		},
        		beforerender: function () {
               	 	var transferCompany = Ext.ComponentQuery.query("checkforcemodify combobox[name=transferCompany]")[0];
                    var transferCompanyStore = transferCompany.getStore();
                    transferCompanyStore.load();
                    
                    var orderAddr = Ext.ComponentQuery.query("checkforcemodify combobox[name=orderAddr]")[0];
                    var orderAddrStore = orderAddr.getStore();
                    orderAddrStore.load();
                    
                    var brandId = Ext.ComponentQuery.query("checkforcemodify combobox[name=brandId]")[0];
                    var brandIdStore = brandId.getStore();
                    brandIdStore.load();
                    
                    var seriesId = Ext.ComponentQuery.query("checkforcemodify combobox[name=seriesId]")[0];
                    var seriesIdStore = seriesId.getStore();
                    seriesIdStore.load();
                    
                    var singleId = Ext.ComponentQuery.query("checkforcemodify combobox[name=singleId]")[0];
                    var singleIdStore = singleId.getStore();
                    singleIdStore.load();
                    
                    var payby = Ext.ComponentQuery.query("checkforcemodify combobox[name=payby]")[0];
                    var paybyStore = payby.getStore();
                    paybyStore.load();
                    
                    var currency = Ext.ComponentQuery.query("checkforcemodify combobox[name=currency]")[0];
                    var currencyStore = currency.getStore();
                    currencyStore.load();
               },
        		afterrender: function(component, eOpts){
        			var form = component.down("form");
                    var params = Ext.clone(this.extraData);
    				form.getForm().setValues(params);
    				component.down("combobox[name=seriesId]").setValue(Number.parseInt(this.extraData.seriesId));
    				component.down("combobox[name=singleId]").setValue(Number.parseInt(this.extraData.singleId));
    				component.down("combobox[name=currency]").setValue(this.extraData.currency+'');
    			}
        	}
        });
    	editWindow.show();
    },
    
    onDeleteClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
        var selectionNum = selections.length;
        
        if (selectionNum != 1) {
        	Ext.MessageBox.alert("请求失败", "请选择单条记录进行删除");
            return;
        } 

        orderAddrName = MIS.common.DictManager.getDictItemName("orderAddr", selections[0].raw.orderAddr);
        
        //删除提示语言
        var tipText = "确定删除下单清单,下单网站["+orderAddrName+"],快递单号["+selections[0].raw.trackingNumber+"],同时会删除<邮寄清单><入库清单><销售清单>中的相应记录";
        
        Ext.MessageBox.confirm("删除提示", tipText, function (confirmId) {
        	if(confirmId == "yes"){
        		Ext.Ajax.request({
                	url: "/check/delete",
                	params: {
                		id: selections[0].raw.id
                	},
                	success: function(conn, request, option, eOpts){
                		var result = Ext.JSON.decode(conn.responseText, true);
                		if(result.resultCode != 0){
                			Ext.MessageBox.alert("请求失败", "删除清单失败" + result.resultMessage);
                		} else {
                			Ext.ComponentQuery.query("checkgrid")[0].store.reload();
                		}
                	},
                	failure: function(conn, request, option, eOpts){
                		Ext.MessageBox.alert("请求失败", "服务器繁忙，稍后重试!");
                	}
                	
                });
        	}
        });
    },
    
    onPostClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	if(selectionNum < 1){
    		Ext.MessageBox.alert("请求失败", "请至少选择一条下单记录进行一键邮寄");
    		return;
    	}
    	
        var checkIds = [];
        var i = 0;
        for(;i<selectionNum; i++){
        	checkIds[i] = selections[i].raw.id;
        	
        	if(selections[i].raw.orderStatus != 0){
        		Ext.MessageBox.alert("请求失败", "下单清单，快递单号["+selections[i].raw.trackingNumber+"]，单品名称["+selections[i].raw.singleName+"]，状态不为[已下单]");
        		return;
        	}
        	
        	if(selections[i].raw.trackingNumber == ''){
        		Ext.MessageBox.alert("请求失败", "下单清单，单品名称["+selections[i].raw.singleName+"]，快递单号不能为空");
        		return;
        	}
        }
        
        var checkview = Ext.ComponentQuery.query("checkview")[0];
    	checkview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "添加邮寄清单",
        	id: "shippedheadaddwindow",
        	extraData: selections[0].raw,
        	renderTo: checkview.getEl(),
        	height: 180,
        	width: 580,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "shippedheadadd"
        	}],
        	listeners: {
        		close: function(){
        			checkview.getEl().unmask();
        		},
        		beforerender: function () {
               	 	var transferCompany = Ext.ComponentQuery.query("shippedheadadd combobox[name=transferCompany]")[0];
                    var transferCompanyStore = transferCompany.getStore();
                    transferCompanyStore.load();
               },
        		afterrender: function(component, eOpts){
        			var form = component.down("form");
                    var params = Ext.clone(this.extraData);
    				form.getForm().setValues(params);
    				component.down("textfield[name=checkIds]").setValue(checkIds);
    				component.down("textfield[name=trackingNumber]").setValue('');
    			}
        	}
        });
    	editWindow.show();
    },
    
    onQueryAllClick: function(component){
    	this.store.proxy.extraParams.orderStatus = '';
    	this.store.reload();
    },
    
    onQueryPartAClick: function(component){
    	this.store.proxy.extraParams.orderStatus = '0';
    	this.store.reload();
    },
    
    onQueryPartBClick: function(component){
    	this.store.proxy.extraParams.orderStatus = '1';
    	this.store.reload();
    },
    
    onQueryPartCClick: function(component){
    	this.store.proxy.extraParams.orderStatus = '2';
    	this.store.reload();
    },
    
    onQueryPartDClick: function(component){
    	this.store.proxy.extraParams.orderStatus = '3';
    	this.store.reload();
    },
	
	onExpandSearchClick: function(component){
		var searchwindow = Ext.ComponentQuery.query("checkSearchpanel")[0];
    	if(searchwindow.isHidden()){
    		searchwindow.show();
    	} else {
    		this.store.proxy.extraParams = {};
    		this.store.proxy.extraParams.orderStatus= "0";
    		
    		searchwindow.form.reset();
    		searchwindow.hide();
    	}
	}
	
});