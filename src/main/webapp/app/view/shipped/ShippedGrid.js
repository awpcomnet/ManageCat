/**
 * 邮寄清单数据显示
 */
Ext.define("MIS.view.shipped.ShippedGrid", {
	extend: "Ext.grid.Panel",
	alias: "widget.shippedgrid",
	
	requires: [
	     'Ext.form.field.Text',
	     'Ext.toolbar.TextItem',
	     'Ext.toolbar.Paging'
	],
	
	store: "MIS.store.shipped.ShippedHeadStore",
	
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
			    { header: '提交邮寄时间', dataIndex: 'submitTime', sortable: true, width: 10, align: "center"},
			    { header: '转运公司', dataIndex: 'transferCompany', sortable: true, width: 10, align: "center", renderer: function (value, rowindex, record, column) {
			    	return MIS.common.DictManager.getDictItemName("transferCompany", value);
                }},
			    { header: '邮费总价(￥)', dataIndex: 'postage', sortable: true, width: 10, align: "center"},
			    { header: '内含邮寄清单数量', dataIndex: 'shippedNum', sortable: true, width: 10, align: "center"},
			    { header: '内含入库清单数量', dataIndex: 'storeNum', sortable: true, width: 10, align: "center"}
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
				}, {
					iconCls: 'icon-exchange',
					text: '排序方式',
					itemId: 'shippedGridByChange',
					scope: this,
					handler: this.onShippedGridByChange
				}, '-', {
					iconCls: 'icon-folder-open-alt',
					text: '详情',
					itemId: 'detail',
					scope: this,
					handler: this.onDetailClick
				}, '-', {
					iconCls: 'icon-file-alt',
					text: '未入库(或部分未入库)',
					itemId: 'partA',
					scope: this,
					handler: this.onQueryPartAClick
				}, {
					iconCls: 'icon-file-alt',
					text: '已入库(全部)',
					itemId: 'partB',
					scope: this,
					handler: this.onQueryPartBClick
				}, '-', {
					iconCls: 'icon-file-alt',
					text: '所有邮寄清单记录',
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
    	
    	var shippedview = Ext.ComponentQuery.query("shippedview")[0];
    	shippedview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "修改邮寄清单",
        	id: "shippedmodifywindow",
        	extraData: selections[0].raw,
        	renderTo: shippedview.getEl(),
        	height: 170,
        	width: 580,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "shippedmodify"
        	}],
        	listeners: {
        		close: function(){
        			shippedview.getEl().unmask();
        		},
        		beforerender: function () {
               	 	var transferCompany = Ext.ComponentQuery.query("shippedmodify combobox[name=transferCompany]")[0];
                    var transferCompanyStore = transferCompany.getStore();
                    transferCompanyStore.load();
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

        //删除提示语言
        var tipText = "确定删除邮寄清单,快递单号["+selections[0].raw.trackingNumber+"],同时会恢复<下单清单>相应记录的状态到[已下单]。注：邮寄清单中存在[已入库]状态清单时，删除失败。";
        
        Ext.MessageBox.confirm("删除提示", tipText, function (confirmId) {
        	if(confirmId == "yes"){
        		Ext.Ajax.request({
                	url: "/shippedHead/delete",
                	params: {
                		id: selections[0].raw.id
                	},
                	success: function(conn, request, option, eOpts){
                		var result = Ext.JSON.decode(conn.responseText, true);
                		if(result.resultCode != 0){
                			Ext.MessageBox.alert("请求失败", "删除邮寄清单失败" + result.resultMessage);
                		} else {
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
    
    onShippedGridByChange: function(component){
    	var shippedHeadStore = this.getStore();
        var orderBy = shippedHeadStore.proxy.extraParams.orderBy;
        
        if(orderBy == null || orderBy == ''){
        	shippedHeadStore.proxy.extraParams.orderBy = '1';
        } else {
        	shippedHeadStore.proxy.extraParams.orderBy = '';
        }
        shippedHeadStore.load();
    },
    
    onDetailClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	if(selectionNum != 1){
    		Ext.MessageBox.alert("请求失败", "请选择一条下单记录进行查看");
    		return;
    	}
    	
        var shippedview = Ext.ComponentQuery.query("shippedview")[0];
        shippedview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "邮寄清单详情,快递单号["+selections[0].raw.trackingNumber+"]",
        	//id: "shippedswindow",
        	extraData: selections[0].raw,
        	renderTo: shippedview.getEl(),
        	height: 450,
        	width: 1000,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "shippedsgrid"
        	}],
        	listeners: {
        		close: function(){
        			shippedview.getEl().unmask();
        		},
        		beforerender: function () {
        			var shippedsgrid = Ext.ComponentQuery.query("shippedsgrid")[0];
        			var shippedsgridStore = shippedsgrid.getStore();
        			shippedsgridStore.proxy.extraParams.headId = selections[0].raw.id;
        			shippedsgridStore.load();
        		},
        		afterrender: function(component, eOpts){
        			
    			}
        	}
        });
    	editWindow.show();
    },
    
    onQueryAllClick: function(component){
    	this.store.proxy.extraParams.flag = '';
    	this.store.reload();
    },
    
    onQueryPartAClick: function(component){
    	this.store.proxy.extraParams.flag = '1';
    	this.store.reload();
    },
    
    onQueryPartBClick: function(component){
    	this.store.proxy.extraParams.flag = '2';
    	this.store.reload();
    },
    
	onExpandSearchClick: function(component){
		var searchwindow = Ext.ComponentQuery.query("shippedSearchpanel")[0];
    	if(searchwindow.isHidden()){
    		searchwindow.show();
    	} else {
    		this.store.proxy.extraParams = {};
    		
    		searchwindow.form.reset();
    		searchwindow.hide();
    	}
	}
	
});