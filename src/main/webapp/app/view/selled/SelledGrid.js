/**
 * 售出清单数据显示
 */
Ext.define("MIS.view.selled.SelledGrid", {
	extend: "Ext.grid.Panel",
	alias: "widget.selledgrid",
	
	requires: [
	     'Ext.form.field.Text',
	     'Ext.toolbar.TextItem',
	     'Ext.toolbar.Paging'
	],
	
	store: "MIS.store.selled.SelledStore",
	
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
			    { header: '品牌名称', dataIndex: 'brandName', sortable: true, width: 10, align: "center"},
			    { header: '系列名称', dataIndex: 'seriesName', sortable: true, width: 10, align: "center"},
			    { header: '单品名称', dataIndex: 'singleName', sortable: true, width: 10, align: "center"},
			    { header: '数量', dataIndex: 'sellNum', sortable: true, width: 5, align: "center"},
			    { header: '补损金额(￥)', dataIndex: 'refund', sortable: true, width: 10, align: "center", editor:{ 
                	decimalPrecision: 2,
                	xtype: 'numberfield'
                }},
			    { header: '售价(￥)', dataIndex: 'sellingPrice', sortable: true, width: 10, align: "center", editor:{ 
                	decimalPrecision: 2,
                	xtype: 'numberfield'
                }},
			    { header: '售出总金额(￥)', dataIndex: 'sumPrice', sortable: true, width: 10, align: "center"},
			    { header: '售出时间', dataIndex: 'sellTime', sortable: true, width: 10, align: "center"},
			    { header: '状态', dataIndex: 'selledStatus', sortable: true, width: 10, align: "center", renderer: function (value, rowindex, record, column) {
			    	return MIS.common.DictManager.getDictItemName("orderStatus", value);
                }},
			    { header: '备注', dataIndex: 'remark', sortable: true, width: 15, align: "center"}
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
				}, {
					iconCls: 'icon-medkit',
					text: '补损',
					itemId: 'compensation',
					scope: this,
					handler: this.onCompensationClick
				}, '-', {
					iconCls: 'icon-file-alt',
					text: '已售出',
					itemId: 'selled',
					scope: this,
					handler: this.onSelledClick
				}, {
					iconCls: 'icon-file-alt',
					text: '已售出(补损)',
					itemId: 'selledCompensation',
					scope: this,
					handler: this.onSelledCompensationClick
				}, {
					iconCls: 'icon-file-alt',
					text: '已损坏',
					itemId: 'damage',
					scope: this,
					handler: this.onDamageClick
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
    	
    	var selledview = Ext.ComponentQuery.query("selledview")[0];
    	selledview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "修改售出记录["+selections[0].raw.singleName+"]",
        	id: "selledmodifywindow",
        	extraData: selections[0].raw,
        	renderTo: selledview.getEl(),
        	height: 200,
        	width: 580,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "selledmodify"
        	}],
        	listeners: {
        		close: function(){
        			selledview.getEl().unmask();
        		},
        		beforerender: function () {
        			
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
        	Ext.MessageBox.alert("请求失败", "请选择一条记录进行删除");
            return;
        } 
        
        //删除提示语言
        var tipText = "确定删除售出记录,删除之后记录将会恢复到入库记录中。（注：当前售出金额，补损金额等信息将丢失）";
        
        Ext.MessageBox.confirm("删除提示", tipText, function (confirmId) {
        	if(confirmId == "yes"){
        		Ext.Ajax.request({
                	url: "/selled/delete",
                	params: {
                		id: selections[0].raw.id
                	},
                	success: function(conn, request, option, eOpts){
                		var result = Ext.JSON.decode(conn.responseText, true);
                		if(result.resultCode != 0){
                			Ext.MessageBox.alert("请求失败", "删除售出记录失败" + result.resultMessage);
                		} else {
                			Ext.ComponentQuery.query("selledgrid")[0].store.reload();
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
        	height: 315,
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
        			component.down("numberfield[name=sellNum]").setValue(selections[0].raw.residueNum);
        			component.down("numberfield[name=sellNum]").setMaxValue(selections[0].raw.residueNum)
        			component.down("textarea[name=remark]").setValue(selections[0].raw.remark);
        			component.down("textfield[name=storeId]").setValue(selections[0].raw.id);
        			component.down("numberfield[name=rate]").setValue(15);
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
    
	onExpandSearchClick: function(component){
		var searchwindow = Ext.ComponentQuery.query("storageSearchpanel")[0];
    	if(searchwindow.isHidden()){
    		searchwindow.show();
    	} else {
    		searchwindow.form.reset();
    		searchwindow.hide();
    	}
	}
	
});