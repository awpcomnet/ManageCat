/**
 * 单品数据显示
 */
Ext.define("MIS.view.singleproduct.SingleGrid", {
	extend: "Ext.grid.Panel",
	alias: "widget.singlegrid",
	
	requires: [
	     'Ext.form.field.Text',
	     'Ext.toolbar.TextItem',
	     'Ext.toolbar.Paging'
	],
	
	store: "MIS.store.singleproduct.SingleproductStore",
	
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
			    { header: '单品名称', dataIndex: 'singleName', sortable: true, width: 20, align: "center"},
			    { header: '单品英文名', dataIndex: 'singleEname', sortable: true, width: 20, align: "center"},
			    { header: '所属系列', dataIndex: 'ofOriginName', sortable: true, width: 15, align: "center"},
			    { header: '所属品牌', dataIndex: 'brandName', sortable: true, width: 15, align: "center"},
			    { header: '规格', dataIndex: 'capacity', sortable: true, width: 15, align: "center"},
			    { header: '单位', dataIndex: 'unit', sortable: true, width: 10, align: "center", renderer: function (value, rowindex, record, column) {
			    	return MIS.common.DictManager.getDictItemName("unitDict", value);
                }},
			    { header: '是否可用', dataIndex: 'isUse', sortable: true, width: 10, align: "center", renderer: function (value, rowindex, record, column) {
			    	return MIS.common.DictManager.getDictItemName("onOff", value);
                }},
			    //{ header: '创建时间', dataIndex: 'createDateFormat', sortable: true, width: 25, align: "center"},
			    { header: '修改时间', dataIndex: 'updateDateFormat', sortable: true, width: 20, align: "center"}
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
    	var singleview = Ext.ComponentQuery.query("singleview")[0];
    	singleview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "添加单品",
        	id: "singleaddwindow",
        	renderTo: singleview.getEl(),
        	height: 280,
        	width: 580,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "singleadd"
        	}],
        	listeners: {
        		close: function(){
        			singleview.getEl().unmask();
        		},
        		beforerender: function () {
        			var brandId = Ext.ComponentQuery.query("singleadd combobox[name=brandId]")[0];
                    var brandIdStore = brandId.getStore();
                    brandIdStore.proxy.extraParams.isUse = 1;
                    brandIdStore.load();
        			
        			var ofOrigin = Ext.ComponentQuery.query("singleadd combobox[name=ofOrigin]")[0];
                    var ofOriginStore = ofOrigin.getStore();
                    ofOriginStore.proxy.extraParams.isUse = 1;
                    ofOriginStore.load();
        			
                    var isUse = Ext.ComponentQuery.query("singleadd combobox[name=isUse]")[0];
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
    		Ext.MessageBox.alert("请求失败", "请选择单个单品进行修改");
    		return;
    	}
    	var singleview = Ext.ComponentQuery.query("singleview")[0];
    	singleview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "修改单品",
        	id: "singlemodifywindow",
        	extraData: selections[0].raw,
        	renderTo: singleview.getEl(),
        	height: 280,
        	width: 580,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "singlemodify"
        	}],
        	listeners: {
        		close: function(){
        			singleview.getEl().unmask();
        		},
        		beforerender: function () {
        			var brandId = Ext.ComponentQuery.query("singlemodify combo[name=brandId]")[0];
                    var brandIdStore = brandId.getStore();
                    brandIdStore.proxy.extraParams.isUse = 1;
                    brandIdStore.load();
        			
        			var ofOrigin = Ext.ComponentQuery.query("singlemodify combo[name=ofOrigin]")[0];
                    var ofOriginStore = ofOrigin.getStore();
                    ofOriginStore.proxy.extraParams.isUse = 1;
                    ofOriginStore.load();
                    
                    var isUse = Ext.ComponentQuery.query("singlemodify combo[name=isUse]")[0];
                    var isUseStore = isUse.getStore();
                    isUseStore.load();
        		},
        		afterrender: function(component, eOpts){
        			var form = component.down("form");
                    var params = Ext.clone(this.extraData);
    				form.getForm().setValues(params);
    				component.down("combo[name=ofOrigin]").setValue(Number.parseInt(this.extraData.ofOrigin));
    				component.down("combo[name=brandId]").setValue(Number.parseInt(this.extraData.brandId));
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
        var tipText = "确定删除单品["+selections[0].raw.singleName+"]。";
        
        Ext.MessageBox.confirm("删除提示", tipText, function (confirmId) {
        	if(confirmId == "yes"){
        		Ext.Ajax.request({
                	url: "/singleproduct/delete",
                	params: {
                		singleId: selections[0].raw.singleId
                	},
                	success: function(conn, request, option, eOpts){
                		var result = Ext.JSON.decode(conn.responseText, true);
                		if(result.resultCode != 0){
                			Ext.MessageBox.alert("请求失败", "删除单品失败" + result.resultMessage);
                		} else {
                			Ext.ComponentQuery.query("singlegrid")[0].store.reload();
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
		var searchwindow = Ext.ComponentQuery.query("singleSearchpanel")[0];
    	if(searchwindow.isHidden()){
    		var ofOrigin = Ext.ComponentQuery.query("singleSearchpanel combo[name=ofOrigin]")[0];
            var ofOriginStore = ofOrigin.getStore();
            ofOriginStore.proxy.extraParams.isUse = 1;
            ofOriginStore.load();
            
    		searchwindow.show();
    	} else {
    		searchwindow.form.reset();
    		searchwindow.hide();
    	}
	}
	
});