/**
 * 主订单数据显示
 */
Ext.define("MIS.view.order.SubOrderGrid", {
	extend: "Ext.grid.Panel",
	alias: "widget.subordergrid",
	
	requires: [
	     'Ext.form.field.Text',
	     'Ext.toolbar.TextItem',
	     'Ext.toolbar.Paging'
	],
	
	store: "MIS.store.order.SubOrderStore",
	
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
			    { header: '所属主订单编号', dataIndex: 'superOrderId', sortable: true, width: 10, align: "center"},
			    { header: '品牌名称', dataIndex: 'brandName', sortable: true, width: 15, align: "center"},
			    { header: '系列名称', dataIndex: 'seriesName', sortable: true, width: 15, align: "center"},
			    { header: '单品名称', dataIndex: 'singleName', sortable: true, width: 15, align: "center"},
			    { header: '数量', dataIndex: 'num', sortable: true, width: 7, align: "center"},
			    
			    { header: '下单单价', dataIndex: 'orderPrice', sortable: true, width: 10, align: "center"},
			    { header: '平均运费', dataIndex: 'transferPrice', sortable: true, width: 10, align: "center"},
			    { header: '单个成本', dataIndex: 'costPrice', sortable: true, width: 10, align: "center"},
			    { header: '单个售价', dataIndex: 'sellingPrice', sortable: true, width: 10, align: "center"},
			    
//			    { header: '下单总价', dataIndex: 'sumOrderPrice', sortable: true, width: 15, align: "center"},
//			    { header: '运费总价', dataIndex: 'sumTransferPrice', sortable: true, width: 15, align: "center"},
//			    { header: '成本总价', dataIndex: 'sumCostPrice', sortable: true, width: 15, align: "center"},
//			    { header: '销售总价', dataIndex: 'sumSellingPrice', sortable: true, width: 15, align: "center"},
			    { header: '当前状态', dataIndex: 'curState', sortable: true, width: 15, align: "center", renderer: function (value, rowindex, record, column) {
			    	return MIS.common.DictManager.getDictItemName("foreignState", value);
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
					iconCls: 'icon-arrow-up',
					text: '一键确认收货',
					itemId: 'oneConfirm',
					scope: this,
					handler: this.onConfirmClick
				}, '-', {
					iconCls: 'icon-file-alt',
					text: '操作子订单',
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
//    	var selections = this.getView().getSelectionModel().getSelection();
//    	var selectionNum = selections.length;
//    	if(selectionNum != 1){
//    		Ext.MessageBox.alert("请求失败", "请选择单个主订单进行修改");
//    		return;
//    	}
//    	var orderview = Ext.ComponentQuery.query("orderview")[0];
//    	orderview.getEl().mask();
//    	
//    	var editWindow = Ext.create("Ext.window.Window", {
//        	title: "修改主订单",
//        	id: "ordermodifywindow",
//        	extraData: selections[0].raw,
//        	renderTo: orderview.getEl(),
//        	height: 200,
//        	width: 580,
//        	layout: "fit",
//        	closeAction: "destroy",
//        	items: [{
//        		xtype: "ordermodify"
//        	}],
//        	listeners: {
//        		close: function(){
//        			orderview.getEl().unmask();
//        		},
//        		beforerender: function () {
//               	 	var foreignState = Ext.ComponentQuery.query("ordermodify combobox[name=foreignState]")[0];
//                    var foreignStateStore = foreignState.getStore();
//                    foreignStateStore.load();
//                    
//                    var transfer = Ext.ComponentQuery.query("ordermodify combobox[name=transfer]")[0];
//                    var transferStore = transfer.getStore();
//                    transferStore.load();
//                    
//                    var affirmState = Ext.ComponentQuery.query("ordermodify combobox[name=affirmState]")[0];
//                    var affirmStateStore = affirmState.getStore();
//                    affirmStateStore.load();
//               },
//        		afterrender: function(component, eOpts){
//        			var form = component.down("form");
//                    var params = Ext.clone(this.extraData);
//    				form.getForm().setValues(params);
//    			}
//        	}
//        });
//    	editWindow.show();
    },
    
    onDeleteClick: function(component){
//    	var selections = this.getView().getSelectionModel().getSelection();
//        var selectionNum = selections.length;
//        var orderIds = [];
//        
//        if (selectionNum <= 0) {
//            return;
//        } 
//
//        //删除提示语言
//        var tipText = "";
//        if(selectionNum == 1){
//        	tipText = "确定删除主订单,编号["+selections[0].raw.orderId+"],将同时删除该订单下的->所有子订单<-";
//        } else {
//        	tipText = "确定删除主订单,编号[";
//        	for(var num=0; num<selectionNum; num++){
//        		tipText += selections[num].raw.orderId;
//        		if(num != selectionNum - 1){
//        			tipText += ",";
//        		}
//        	}
//        	tipText += "],将同时删除该订单下的->所有子订单<-";
//        	
//        }
//        
//        var i = 0;
//        for(;i<selectionNum; i++){
//        	orderIds[i] = selections[i].raw.orderId;
//        }
//        
//        Ext.MessageBox.confirm("删除提示", tipText, function (confirmId) {
//        	if(confirmId == "yes"){
//        		Ext.Ajax.request({
//                	url: "/order/delete",
//                	params: {
//                		id: orderIds
//                	},
//                	success: function(conn, request, option, eOpts){
//                		var result = Ext.JSON.decode(conn.responseText, true);
//                		if(result.resultCode != 0){
//                			Ext.MessageBox.alert("请求失败", "删除公告失败" + result.resultMessage);
//                		} else {
//                			Ext.ComponentQuery.query("noticegrid")[0].store.reload();
//                		}
//                	},
//                	failure: function(conn, request, option, eOpts){
//                		Ext.MessageBox.alert("请求失败", "服务器繁忙，稍后重试!");
//                	}
//                	
//                });
//        	}
//        });
    },
    
    onConfirmClick: function(component){
//    	var selections = this.getView().getSelectionModel().getSelection();
//    	var selectionNum = selections.length;
//    	if(selectionNum < 1){
//    		Ext.MessageBox.alert("请求失败", "请至少选择一个主订单进行一键确认");
//    		return;
//    	}
//    	
//    	var tipText = "";
//        if(selectionNum == 1){
//        	tipText = "确定要确认该主订单,编号["+selections[0].raw.orderId+"],确认后所有主订单状态将归为最终状态，同时->子订单状态也将归为[入库状态]<-";
//        } else {
//        	tipText = "确定要确认这些主订单,编号[";
//        	for(var num=0; num<selectionNum; num++){
//        		tipText += selections[num].raw.orderId;
//        		if(num != selectionNum - 1){
//        			tipText += ",";
//        		}
//        	}
//        	tipText += "],确认后所有主订单状态将归为最终状态，同时->子订单状态也将归为[入库状态]<-";
//        	
//        }
//        
//        var orderIds = [];
//        var i = 0;
//        for(;i<selectionNum; i++){
//        	orderIds[i] = selections[i].raw.orderId;
//        }
//        
//    	Ext.MessageBox.confirm("一键确认提示", tipText, function(confirmId){
//			if(confirmId == "yes"){
//				Ext.Ajax.request({
//		            url: "/order/confirm",
//		            params: {
//		            	orderIds : orderIds
//		            	
//		            },
//		            success: function (conn, request, option, eOpts) {
//		                var result = Ext.JSON.decode(conn.responseText, true);
//		                if (result.resultCode != 0) {
//		                    Ext.MessageBox.alert("请求失败", "确认主订单失败 " + result.resultMessage);
//		                } else {
//		                	Ext.MessageBox.alert("请求成功", "确认主订单成功 ");
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
    
    onsubOrderSetClick: function(component){
    	alert("操作子订单设计中");
    },
	
	onExpandSearchClick: function(component){
		var searchwindow = Ext.ComponentQuery.query("suborderSearchpanel")[0];
    	if(searchwindow.isHidden()){
    		searchwindow.show();
    	} else {
    		searchwindow.form.reset();
    		searchwindow.hide();
    	}
	}
	
});