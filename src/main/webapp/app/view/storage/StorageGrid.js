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
			    { header: '品牌名称', dataIndex: 'brandName', sortable: true, width: 15, align: "center"},
			    { header: '系列名称', dataIndex: 'seriesName', sortable: true, width: 10, align: "center"},
			    { header: '单品名称', dataIndex: 'singleName', sortable: true, width: 10, align: "center"},
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
    	alert("售出开发中...");
    },
    
    onSellingClick: function(component){
    	this.store.proxy.extraParams.includeStatus = '2|4';
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