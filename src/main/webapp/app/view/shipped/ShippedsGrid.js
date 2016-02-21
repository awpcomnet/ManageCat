/**
 * 邮寄清单子单数据显示
 */
Ext.define("MIS.view.shipped.ShippedsGrid", {
	extend: "Ext.grid.Panel",
	alias: "widget.shippedsgrid",
	
	requires: [
	     'Ext.form.field.Text',
	     'Ext.toolbar.TextItem',
	     'Ext.toolbar.Paging'
	],
	
	store: "MIS.store.shipped.ShippedStore",
	
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
			    { header: '转运公司', dataIndex: 'transferCompany', sortable: true, width: 10, align: "center", renderer: function (value, rowindex, record, column) {
			    	return MIS.common.DictManager.getDictItemName("transferCompany", value);
                }},
                { header: '品牌', dataIndex: 'brandName', sortable: true, width: 10, align: "center"},
                { header: '系列', dataIndex: 'seriesName', sortable: true, width: 10, align: "center"},
                { header: '单品', dataIndex: 'singleName', sortable: true, width: 10, align: "center"},
                { header: '数量', dataIndex: 'num', sortable: true, width: 10, align: "center"},
                { header: '下单单价($)', dataIndex: 'unitPrice', sortable: true, width: 10, align: "center"},
                { header: '总价($)', dataIndex: 'sumPrice', sortable: true, width: 10, align: "center"},
                { header: '付款人', dataIndex: 'payby', sortable: true, width: 10, align: "center"},
                { header: '预计单价(￥)', dataIndex: 'planRmb', sortable: true, width: 10, align: "center"},
                { header: '预计邮费(￥)', dataIndex: 'planPostage', sortable: true, width: 10, align: "center"},
                { header: '预计成本(￥)', dataIndex: 'planCost', sortable: true, width: 10, align: "center"},
                { header: '备注', dataIndex: 'remark', sortable: true, width: 10, align: "center"},
			    { header: '邮寄状态', dataIndex: 'shippedStatus', sortable: true, width: 10, align: "center", renderer: function (value, rowindex, record, column) {
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
					iconCls: 'icon-edit',
					text: '修改',
					itemId: 'shippedmodify',
					scope: this,
					handler: this.onModifyClick
				}, {
					iconCls: 'icon-remove',
					text: '删除',
					itemId: 'shippeddelete',
					scope: this,
					handler: this.onDeleteClick
				}, '-', {
					iconCls: 'icon-truck',
					text: '单条入库',
					itemId: 'shippedstore',
					scope: this,
					handler: this.onStoreClick
				}, {
					iconCls: 'icon-truck',
					text: '批量入库',
					itemId: 'shippedstoremore',
					scope: this,
					handler: this.onStoreMoreClick
				}, '->', {
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
    	
    	var shippedsgridview = Ext.ComponentQuery.query("shippedsgrid")[0];
    	shippedsgridview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "修改邮寄清单子单",
        	id: "shippedsmodifywindow",
        	extraData: selections[0].raw,
        	renderTo: shippedsgridview.getEl(),
        	height: 330,
        	width: 580,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "shippedsmodify"
        	}],
        	listeners: {
        		close: function(){
        			shippedsgridview.getEl().unmask();
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
        
        if (selectionNum < 1) {
        	Ext.MessageBox.alert("请求失败", "请选择至少一条记录进行删除");
            return;
        } 

        var ids = [];
        var i = 0;
        for(;i<selectionNum; i++){
        	ids[i] = selections[i].raw.id;
        }
        //删除提示语言
        var tipText = "";
        if(selectionNum == 1){
        	tipText = "确定删除邮寄清单,快递单号["+selections[0].raw.trackingNumber+"],同时会恢复<下单清单>相应记录的状态到[已下单]。注：邮寄清单子单记录全部删除完毕，邮寄清单主单将自动删除。";
        } else {
        	tipText = "确定删除邮寄清单,["+selectionNum+"]个,同时会恢复<下单清单>相应记录的状态到[已下单]。注：邮寄清单子单记录全部删除完毕，邮寄清单主单将自动删除。";
        }
        
        
        Ext.MessageBox.confirm("删除提示", tipText, function (confirmId) {
        	if(confirmId == "yes"){
        		Ext.Ajax.request({
                	url: "/shipped/delete",
                	params: {
                		ids: ids
                	},
                	success: function(conn, request, option, eOpts){
                		var result = Ext.JSON.decode(conn.responseText, true);
                		if(result.resultCode != 0){
                			Ext.MessageBox.alert("请求失败", "删除邮寄清单失败" + result.resultMessage);
                		} else {
                			Ext.ComponentQuery.query("shippedsgrid")[0].store.reload();
                			Ext.ComponentQuery.query("shippedgrid")[0].store.reload();
                		}
                	},
                	failure: function(conn, request, option, eOpts){
                		Ext.MessageBox.alert("请求失败", "服务器繁忙，稍后重试!");
                	}
                	
                });
        	}
        });
    },
    
    onStoreClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	if(selectionNum != 1){
    		Ext.MessageBox.alert("请求失败", "请选择单条记录入库");
    		return;
    	}
    	
    	var shippedsgridview = Ext.ComponentQuery.query("shippedsgrid")[0];
    	shippedsgridview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "邮寄清单子单入库",
        	id: "shippedstorewindow",
        	extraData: selections[0].raw,
        	renderTo: shippedsgridview.getEl(),
        	height: 330,
        	width: 580,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "shippedstore"
        	}],
        	listeners: {
        		close: function(){
        			shippedsgridview.getEl().unmask();
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
    
    onStoreMoreClick: function(component){
    	
    }
    
});