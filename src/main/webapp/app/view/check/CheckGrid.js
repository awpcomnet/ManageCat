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
			    { header: '快递单号', dataIndex: 'trackingNumber', sortable: true, width: 15, align: "center"},
			    { header: '下单时间', dataIndex: 'orderTime', sortable: true, width: 10, align: "center"},
			    { header: '转运公司', dataIndex: 'transferCompany', sortable: true, width: 10, align: "center", renderer: function (value, rowindex, record, column) {
			    	return MIS.common.DictManager.getDictItemName("transferCompany", value);
                }},
			    { header: '下单网站', dataIndex: 'orderAddr', sortable: true, width: 10, align: "center", renderer: function (value, rowindex, record, column) {
			    	return MIS.common.DictManager.getDictItemName("orderAddr", value);
                }},
			    { header: '品牌名称', dataIndex: 'brandName', sortable: true, width: 15, align: "center"},
			    { header: '系列名称', dataIndex: 'seriesName', sortable: true, width: 15, align: "center"},
			    { header: '单品名称', dataIndex: 'singleName', sortable: true, width: 15, align: "center"},
			    { header: '数量', dataIndex: 'num', sortable: true, width: 5, align: "center"},
			    { header: '单价($)', dataIndex: 'unitPrice', sortable: true, width: 10, align: "center"},
			    { header: '总价($)', dataIndex: 'sumPrice', sortable: true, width: 10, align: "center"},
			    { header: '付款人', dataIndex: 'payby', sortable: true, width: 10, align: "center"},
			    { header: '备注', dataIndex: 'remark', sortable: true, width: 10, align: "center"},
			    { header: '订单状态', dataIndex: 'orderStatus', sortable: true, width: 10, align: "center", renderer: function (value, rowindex, record, column) {
			    	return MIS.common.DictManager.getDictItemName("orderStatus", value);
                }}
			    
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
					iconCls: 'icon-arrow-up',
					text: '一键邮寄',
					itemId: 'oneConfirm',
					scope: this,
					handler: this.onPostClick
				}, '-', {
					iconCls: 'icon-file-alt',
					text: '添加子订单',
					itemId: 'subOrderSet',
					scope: this,
					handler: this.onsubOrderSetClick
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
        	height: 380,
        	width: 570,
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
        		},
        		afterrender: function(component, eOpts){
        			
    			}
        	}
        });
    	editWindow.show();
//		Ext.MessageBox.confirm("暂停提示", "确定要添加新的主订单", function(confirmId){
//			if(confirmId == "yes"){
//				Ext.Ajax.request({
//		            url: "/order/add",
//		            params: {
//		            	foreignState: 0,//国外订单状态
//		            	transfer: 0,//转运状态
//		            	affirmState: 0//确认收货状态
//		            	
//		            },
//		            success: function (conn, request, option, eOpts) {
//		                var result = Ext.JSON.decode(conn.responseText, true);
//		                if (result.resultCode != 0) {
//		                    Ext.MessageBox.alert("请求失败", "添加主订单失败 " + result.resultMessage);
//		                } else {
//		                	Ext.MessageBox.alert("请求成功", "添加主订单成功 ");
//		                    Ext.ComponentQuery.query("ordergrid")[0].store.reload();
//		                }
//		            },
//		            failure: function (conn, request, option, eOpts) {
//		                Ext.MessageBox.alert("请求失败", "服务器繁忙, 稍后重试!");
//		            }
//		        });
//			}
//		})
		
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
        	height: 380,
        	width: 580,
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
               },
        		afterrender: function(component, eOpts){
        			var form = component.down("form");
                    var params = Ext.clone(this.extraData);
    				form.getForm().setValues(params);
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
    		Ext.MessageBox.alert("请求失败", "请至少选择一个主订单进行一键确认");
    		return;
    	}
    	
    	var tipText = "";
        if(selectionNum == 1){
        	tipText = "确定要确认该主订单,编号["+selections[0].raw.orderId+"],确认后所有主订单状态将归为最终状态，同时->子订单状态也将归为[入库状态]<-";
        } else {
        	tipText = "确定要确认这些主订单,编号[";
        	for(var num=0; num<selectionNum; num++){
        		tipText += selections[num].raw.orderId;
        		if(num != selectionNum - 1){
        			tipText += ",";
        		}
        	}
        	tipText += "],确认后所有主订单状态将归为最终状态，同时->子订单状态也将归为[入库状态]<-";
        	
        }
        
        var orderIds = [];
        var i = 0;
        for(;i<selectionNum; i++){
        	orderIds[i] = selections[i].raw.orderId;
        }
        
    	Ext.MessageBox.confirm("一键确认提示", tipText, function(confirmId){
			if(confirmId == "yes"){
				Ext.Ajax.request({
		            url: "/order/confirm",
		            params: {
		            	orderIds : orderIds
		            	
		            },
		            success: function (conn, request, option, eOpts) {
		                var result = Ext.JSON.decode(conn.responseText, true);
		                if (result.resultCode != 0) {
		                    Ext.MessageBox.alert("请求失败", "确认主订单失败 " + result.resultMessage);
		                } else {
		                	Ext.MessageBox.alert("请求成功", "确认主订单成功 ");
		                    Ext.ComponentQuery.query("ordergrid")[0].store.reload();
		                }
		            },
		            failure: function (conn, request, option, eOpts) {
		                Ext.MessageBox.alert("请求失败", "服务器繁忙, 稍后重试!");
		            }
		        });
			}
		})
    },
    
    onsubOrderSetClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	if(selectionNum != 1){
    		Ext.MessageBox.alert("请求失败", "请选择一个主订单进行子订单添加");
    		return;
    	}
    	var orderview = Ext.ComponentQuery.query("orderview")[0];
    	orderview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "添加子订单",
        	id: "suborderaddfororderwindow",
        	extraData: selections[0].raw,
        	renderTo: orderview.getEl(),
        	height: 400,
        	width: 575,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "suborderaddfororder"
        	}],
        	listeners: {
        		close: function(){
        			orderview.getEl().unmask();
        		},
        		beforerender: function () {
               	 	var curState = Ext.ComponentQuery.query("suborderaddfororder combobox[name=curState]")[0];
                    var curStateStore = curState.getStore();
                    curStateStore.load();
                    
                    var brandId = Ext.ComponentQuery.query("suborderaddfororder combobox[name=brandId]")[0];
                    var brandIdStore = brandId.getStore();
                    brandIdStore.proxy.extraParams.isUse = 1;
                    brandIdStore.load();
                    
                    var seriesId = Ext.ComponentQuery.query("suborderaddfororder combobox[name=seriesId]")[0];
                    var seriesIdStore = seriesId.getStore();
                    seriesIdStore.proxy.extraParams.isUse = 1;
                    seriesIdStore.proxy.extraParams.ofOrigin = -1;
                    seriesIdStore.load();
                    
                    var singleId = Ext.ComponentQuery.query("suborderaddfororder combobox[name=singleId]")[0];
                    var singleIdStore = singleId.getStore();
                    singleIdStore.proxy.extraParams.isUse = 1;
                    singleIdStore.proxy.extraParams.ofOrigin = -1;
                    singleIdStore.load();
                    
               },
        		afterrender: function(component, eOpts){
        			component.down("textfield[name=superOrderId]").setValue(this.extraData.orderId);
        			component.down("combobox[name=curState]").setValue('0');
        			component.down("numberfield[name=num]").setValue(1);
        			component.down("textfield[name=payby]").setValue(this.extraData.payby);
    			}
        	}
        });
    	editWindow.show();
    },
	
	onExpandSearchClick: function(component){
		var searchwindow = Ext.ComponentQuery.query("orderSearchpanel")[0];
    	if(searchwindow.isHidden()){
    		searchwindow.show();
    	} else {
    		searchwindow.form.reset();
    		searchwindow.hide();
    	}
	}
	
});