/**
 * 子订单数据显示
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
			    { header: '所属主订单', dataIndex: 'superOrderId', sortable: true, width: 10, align: "center"},
			    { header: '品牌名称', dataIndex: 'brandName', sortable: true, width: 16, align: "center"},
			    { header: '系列名称', dataIndex: 'seriesName', sortable: true, width: 16, align: "center"},
			    { header: '单品名称', dataIndex: 'singleName', sortable: true, width: 17, align: "center"},
			    { header: '数量', dataIndex: 'num', sortable: true, width: 7, align: "center"},
			    { header: '售出数量', dataIndex: 'sellNum', sortable: true, width: 7, align: "center"},
			    
			    //{ header: '下单单价', dataIndex: 'orderPrice', sortable: true, width: 10, align: "center"},
			    //{ header: '平均运费', dataIndex: 'transferPrice', sortable: true, width: 10, align: "center"},
			    { header: '单个成本', dataIndex: 'costPrice', sortable: true, width: 10, align: "center"},
			    { header: '单个售价', dataIndex: 'sellingPrice', sortable: true, width: 12, align: "center"},
			    
//			    { header: '下单总价', dataIndex: 'sumOrderPrice', sortable: true, width: 15, align: "center"},
//			    { header: '运费总价', dataIndex: 'sumTransferPrice', sortable: true, width: 15, align: "center"},
//			    { header: '成本总价', dataIndex: 'sumCostPrice', sortable: true, width: 15, align: "center"},
//			    { header: '销售总价', dataIndex: 'sumSellingPrice', sortable: true, width: 15, align: "center"},
			    { header: '当前状态', dataIndex: 'curState', sortable: true, width: 13, align: "center", renderer: function (value, rowindex, record, column) {
			    	return MIS.common.DictManager.getDictItemName("subOrderState", value);
                }},
			    //{ header: '创建时间', dataIndex: 'createDateFormat', sortable: true, width: 25, align: "center"},
			    { header: '修改时间', dataIndex: 'updateDateFormat', sortable: true, width: 22, align: "center"}
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
					iconCls: 'icon-eye-open',
					text: '订单详情',
					itemId: 'orderDetail',
					scope: this,
					handler: this.onOrderDetailClick
				}, '-', {
					iconCls: 'icon-arrow-up',
					text: '订单拆分',
					itemId: 'orderSplit',
					scope: this,
					handler: this.onOrderSplitClick
				}, {
					iconCls: 'icon-arrow-down',
					text: '订单合并',
					itemId: 'orderMerge',
					scope: this,
					handler: this.onOrderMergeClick
				}, '-', {
					iconCls: 'icon-twitter',
					text: '售出/召回',
					itemId: 'sellAndBack',
					scope: this,
					handler: this.onSellAndBackClick
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
		var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	if(selectionNum != 1){
    		Ext.MessageBox.alert("请求失败", "请选择一个子订单进行添加");
    		return;
    	}
    	var suborderview = Ext.ComponentQuery.query("suborderview")[0];
    	suborderview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "添加子订单",
        	id: "suborderaddwindow",
        	extraData: selections[0].raw,
        	renderTo: suborderview.getEl(),
        	height: 350,
        	width: 575,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "suborderadd"
        	}],
        	listeners: {
        		close: function(){
        			suborderview.getEl().unmask();
        		},
        		beforerender: function () {
               	 	var curState = Ext.ComponentQuery.query("suborderadd combobox[name=curState]")[0];
                    var curStateStore = curState.getStore();
                    curStateStore.load();
                    
                    var brandId = Ext.ComponentQuery.query("suborderadd combobox[name=brandId]")[0];
                    var brandIdStore = brandId.getStore();
                    brandIdStore.proxy.extraParams.isUse = 1;
                    brandIdStore.load();
                    
                    var seriesId = Ext.ComponentQuery.query("suborderadd combobox[name=seriesId]")[0];
                    var seriesIdStore = seriesId.getStore();
                    seriesIdStore.proxy.extraParams.isUse = 1;
                    seriesIdStore.proxy.extraParams.ofOrigin = -1;
                    seriesIdStore.load();
                    
                    var singleId = Ext.ComponentQuery.query("suborderadd combobox[name=singleId]")[0];
                    var singleIdStore = singleId.getStore();
                    singleIdStore.proxy.extraParams.isUse = 1;
                    singleIdStore.proxy.extraParams.ofOrigin = -1;
                    singleIdStore.load();
                    
               },
        		afterrender: function(component, eOpts){
        			component.down("textfield[name=superOrderId]").setValue(this.extraData.superOrderId);
        			component.down("combobox[name=curState]").setValue('0');
        			component.down("numberfield[name=num]").setValue(1);
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
    		Ext.MessageBox.alert("请求失败", "请选择单个子订单进行修改");
    		return;
    	}
    	var suborderview = Ext.ComponentQuery.query("suborderview")[0];
    	suborderview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "修改子订单",
        	id: "subordermodifywindow",
        	extraData: selections[0].raw,
        	renderTo: suborderview.getEl(),
        	height: 350,
        	width: 575,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "subordermodify"
        	}],
        	listeners: {
        		close: function(){
        			suborderview.getEl().unmask();
        		},
        		beforerender: function () {
               	 	var curState = Ext.ComponentQuery.query("subordermodify combobox[name=curState]")[0];
                    var curStateStore = curState.getStore();
                    curStateStore.load();
                    
                    var brandId = Ext.ComponentQuery.query("subordermodify combobox[name=brandId]")[0];
                    var brandIdStore = brandId.getStore();
                    brandIdStore.proxy.extraParams.isUse = 1;
                    brandIdStore.load();
                    
                    var seriesId = Ext.ComponentQuery.query("subordermodify combobox[name=seriesId]")[0];
                    var seriesIdStore = seriesId.getStore();
                    seriesIdStore.proxy.extraParams.isUse = 1;
                    seriesIdStore.proxy.extraParams.ofOrigin = this.extraData.brandId;
                    seriesIdStore.load();
                    
                    var singleId = Ext.ComponentQuery.query("subordermodify combobox[name=singleId]")[0];
                    var singleIdStore = singleId.getStore();
                    singleIdStore.proxy.extraParams.isUse = 1;
                    singleIdStore.proxy.extraParams.ofOrigin = this.extraData.seriesId;
                    singleIdStore.load();
                    
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
        var subOrderIds = [];
        
        if (selectionNum <= 0) {
        	Ext.MessageBox.alert("请求失败", "请选择子订单进行删除");
            return;
        } 

        //删除提示语言
        var tipText = "";
        if(selectionNum == 1){
        	tipText = "确定删除子订单,编号["+selections[0].raw.suborderId+"],所属主订单单号["+selections[0].raw.superOrderId+"]";
        } else {
        	tipText = "确定删除子订单,编号[";
        	for(var num=0; num<selectionNum; num++){
        		tipText += selections[num].raw.suborderId;
        		if(num != selectionNum - 1){
        			tipText += ",";
        		}
        	}
        	tipText += "]";
        	
        }
        
        var i = 0;
        for(;i<selectionNum; i++){
        	subOrderIds[i] = selections[i].raw.suborderId;
        }
        
        Ext.MessageBox.confirm("删除提示", tipText, function (confirmId) {
        	if(confirmId == "yes"){
        		Ext.Ajax.request({
                	url: "/subOrder/delete",
                	params: {
                		subOrderIds: subOrderIds
                	},
                	success: function(conn, request, option, eOpts){
                		var result = Ext.JSON.decode(conn.responseText, true);
                		if(result.resultCode != 0){
                			Ext.MessageBox.alert("请求失败", "删除子订单失败" + result.resultMessage);
                		} else {
                			Ext.ComponentQuery.query("subordergrid")[0].store.reload();
                		}
                	},
                	failure: function(conn, request, option, eOpts){
                		Ext.MessageBox.alert("请求失败", "服务器繁忙，稍后重试!");
                	}
                	
                });
        	}
        });
    },
    
    onOrderDetailClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	if(selectionNum != 1){
    		Ext.MessageBox.alert("请求失败", "请选择单个子订单进行查看");
    		return;
    	}
    	var suborderview = Ext.ComponentQuery.query("suborderview")[0];
    	suborderview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "子订单详情",
        	id: "suborderdetailwindow",
        	extraData: selections[0].raw,
        	renderTo: suborderview.getEl(),
        	height: 380,
        	width: 840,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "suborderdetail"
        	}],
        	listeners: {
        		close: function(){
        			suborderview.getEl().unmask();
        		},
        		beforerender: function () {
               	 	var curState = Ext.ComponentQuery.query("suborderdetail combobox[name=curState]")[0];
                    var curStateStore = curState.getStore();
                    curStateStore.load();
                    
                    var brandId = Ext.ComponentQuery.query("suborderdetail combobox[name=brandId]")[0];
                    var brandIdStore = brandId.getStore();
                    brandIdStore.proxy.extraParams.isUse = 1;
                    brandIdStore.load();
                    
                    var seriesId = Ext.ComponentQuery.query("suborderdetail combobox[name=seriesId]")[0];
                    var seriesIdStore = seriesId.getStore();
                    seriesIdStore.proxy.extraParams.isUse = 1;
                    seriesIdStore.proxy.extraParams.ofOrigin = this.extraData.brandId;
                    seriesIdStore.load();
                    
                    var singleId = Ext.ComponentQuery.query("suborderdetail combobox[name=singleId]")[0];
                    var singleIdStore = singleId.getStore();
                    singleIdStore.proxy.extraParams.isUse = 1;
                    singleIdStore.proxy.extraParams.ofOrigin = this.extraData.seriesId;
                    singleIdStore.load();
                    
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
    
    onOrderSplitClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	if(selectionNum != 1){
    		Ext.MessageBox.alert("请求失败", "请选择单个子订单进行修改");
    		return;
    	}
    	var suborderview = Ext.ComponentQuery.query("suborderview")[0];
    	suborderview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "拆分子订单",
        	id: "subordersplitwindow",
        	extraData: selections[0].raw,
        	renderTo: suborderview.getEl(),
        	height: 350,
        	width: 575,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "subordersplit"
        	}],
        	listeners: {
        		close: function(){
        			suborderview.getEl().unmask();
        		},
        		beforerender: function () {
                    var brandId = Ext.ComponentQuery.query("subordersplit combobox[name=brandId]")[0];
                    var brandIdStore = brandId.getStore();
                    brandIdStore.proxy.extraParams.isUse = 1;
                    brandIdStore.load();
                    
                    var seriesId = Ext.ComponentQuery.query("subordersplit combobox[name=seriesId]")[0];
                    var seriesIdStore = seriesId.getStore();
                    seriesIdStore.proxy.extraParams.isUse = 1;
                    seriesIdStore.proxy.extraParams.ofOrigin = this.extraData.brandId;
                    seriesIdStore.load();
                    
                    var singleId = Ext.ComponentQuery.query("subordersplit combobox[name=singleId]")[0];
                    var singleIdStore = singleId.getStore();
                    singleIdStore.proxy.extraParams.isUse = 1;
                    singleIdStore.proxy.extraParams.ofOrigin = this.extraData.seriesId;
                    singleIdStore.load();
                    
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
    
    onOrderMergeClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	var subOrderIds = [];
    	
    	if(selectionNum <= 1){
    		Ext.MessageBox.alert("请求失败", "请至少选择两个子订单进行合并");
    		return;
    	}
    	var suborderview = Ext.ComponentQuery.query("suborderview")[0];
    	suborderview.getEl().mask();
    	
    	var i = 0;
        for(;i<selectionNum; i++){
        	subOrderIds[i] = selections[i].raw.suborderId;
        }
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "合并子订单",
        	id: "subordermergewindow",
        	extraData: selections[0].raw,
        	renderTo: suborderview.getEl(),
        	height: 350,
        	width: 590,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "subordermerge"
        	}],
        	listeners: {
        		close: function(){
        			suborderview.getEl().unmask();
        		},
        		beforerender: function () {
        			var curState = Ext.ComponentQuery.query("subordermerge combobox[name=curState]")[0];
                    var curStateStore = curState.getStore();
                    curStateStore.load();
                    
                    var brandId = Ext.ComponentQuery.query("subordermerge combobox[name=brandId]")[0];
                    var brandIdStore = brandId.getStore();
                    brandIdStore.proxy.extraParams.isUse = 1;
                    brandIdStore.load();
                    
                    var seriesId = Ext.ComponentQuery.query("subordermerge combobox[name=seriesId]")[0];
                    var seriesIdStore = seriesId.getStore();
                    seriesIdStore.proxy.extraParams.isUse = 1;
                    seriesIdStore.proxy.extraParams.ofOrigin = this.extraData.brandId;
                    seriesIdStore.load();
                    
                    var singleId = Ext.ComponentQuery.query("subordermerge combobox[name=singleId]")[0];
                    var singleIdStore = singleId.getStore();
                    singleIdStore.proxy.extraParams.isUse = 1;
                    singleIdStore.proxy.extraParams.ofOrigin = this.extraData.seriesId;
                    singleIdStore.load();
                    
               },
        		afterrender: function(component, eOpts){
        			var form = component.down("form");
                    var params = Ext.clone(this.extraData);
    				form.getForm().setValues(params);
    				component.down("textfield[name=subOrderIds]").setValue(subOrderIds);
    			}
        	}
        });
    	editWindow.show();
    },
    
    onSellAndBackClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	if(selectionNum != 1){
    		Ext.MessageBox.alert("请求失败", "请选择单个子订单进行售出/召回");
    		return;
    	}
    	var suborderview = Ext.ComponentQuery.query("suborderview")[0];
    	suborderview.getEl().mask();
    	
    	var maxNum = Number.parseInt(selections[0].raw.num) - Number.parseInt(selections[0].raw.sellNum);
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "售出/召回",
        	id: "sellandbackwindow",
        	extraData: selections[0].raw,
        	renderTo: suborderview.getEl(),
        	height: 150,
        	width: 575,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "sellandback"
        	}],
        	listeners: {
        		close: function(){
        			suborderview.getEl().unmask();
        		},
        		beforerender: function () {
               	 	var flag = Ext.ComponentQuery.query("sellandback combobox[name=flag]")[0];
                    var flagStore = flag.getStore();
                    flagStore.load();
                    
                    var flag = Ext.ComponentQuery.query("sellandback numberfield[name=num]")[0];
                    flag.maxValue = maxNum;
               },
        		afterrender: function(component, eOpts){
        			var form = component.down("form");
                    var params = Ext.clone(this.extraData);
    				form.getForm().setValues(params);
    				
    				component.down("numberfield[name=num]").setValue(maxNum);
    				component.down("combobox[name=flag]").setValue("1");
    			}
        	}
        });
    	editWindow.show();
    },
    
	onExpandSearchClick: function(component){
		var searchwindow = Ext.ComponentQuery.query("subOrderSearchpanel")[0];
    	if(searchwindow.isHidden()){
    		var curState = Ext.ComponentQuery.query("subOrderSearchpanel combobox[name=curState]")[0];
            var curStateStore = curState.getStore();
            curStateStore.load();
            
            var brandId = Ext.ComponentQuery.query("subOrderSearchpanel combobox[name=brandId]")[0];
            var brandIdStore = brandId.getStore();
            brandIdStore.proxy.extraParams.isUse = 1;
            brandIdStore.load();
            
            var seriesId = Ext.ComponentQuery.query("subOrderSearchpanel combobox[name=seriesId]")[0];
            var seriesIdStore = seriesId.getStore();
            seriesIdStore.proxy.extraParams.isUse = 1;
            seriesIdStore.proxy.extraParams.ofOrigin = -1;
            seriesIdStore.load();
            
            var singleId = Ext.ComponentQuery.query("subOrderSearchpanel combobox[name=singleId]")[0];
            var singleIdStore = singleId.getStore();
            singleIdStore.proxy.extraParams.isUse = 1;
            singleIdStore.proxy.extraParams.ofOrigin = -1;
            singleIdStore.load();
    		searchwindow.show();
    	} else {
    		searchwindow.form.reset();
    		searchwindow.hide();
    	}
	}
	
});