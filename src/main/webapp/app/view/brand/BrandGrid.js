/**
 * 品牌数据显示
 */
Ext.define("MIS.view.brand.BrandGrid", {
	extend: "Ext.grid.Panel",
	alias: "widget.brandgrid",
	
	requires: [
	     'Ext.form.field.Text',
	     'Ext.toolbar.TextItem',
	     'Ext.toolbar.Paging'
	],
	
	store: "MIS.store.brand.BrandStore",
	
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
			    { header: '品牌名称', dataIndex: 'brandName', sortable: true, width: 20, align: "center"},
			    { header: '品牌英文名', dataIndex: 'brandEname', sortable: true, width: 20, align: "center"},
			    { header: '所属国家', dataIndex: 'ofOrigin', sortable: true, width: 20, align: "center", renderer: function (value, rowindex, record, column) {
                    return MIS.common.DictManager.getDictItemName("country", value);
                }},
			    { header: '是否可用', dataIndex: 'isUse', sortable: true, width: 20, align: "center", renderer: function (value, rowindex, record, column) {
			    	return MIS.common.DictManager.getDictItemName("onOff", value);
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
				}, '-', {
					iconCls: 'icon-file-alt',
					text: '添加系列',
					itemId: 'seriesAdd',
					scope: this,
					handler: this.onSeriesAddClick
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
    	var brandview = Ext.ComponentQuery.query("brandview")[0];
    	brandview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "添加品牌",
        	id: "brandaddwindow",
        	renderTo: brandview.getEl(),
        	height: 180,
        	width: 580,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "brandadd"
        	}],
        	listeners: {
        		close: function(){
        			brandview.getEl().unmask();
        		},
        		beforerender: function () {
        			
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
    		Ext.MessageBox.alert("请求失败", "请选择单个品牌进行修改");
    		return;
    	}
    	var brandview = Ext.ComponentQuery.query("brandview")[0];
    	brandview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "修改主订单",
        	id: "brandmodifywindow",
        	extraData: selections[0].raw,
        	renderTo: brandview.getEl(),
        	height: 180,
        	width: 580,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "brandmodify"
        	}],
        	listeners: {
        		close: function(){
        			brandview.getEl().unmask();
        		},
        		beforerender: function () {
        			var ofOrigin = Ext.ComponentQuery.query("brandmodify combo[name=ofOrigin]")[0];
                    var ofOriginStore = ofOrigin.getStore();
                    ofOriginStore.load();
                    
                    var isUse = Ext.ComponentQuery.query("brandmodify combo[name=isUse]")[0];
                    var isUseStore = isUse.getStore();
                    isUseStore.load();
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
    
    onSeriesAddClick: function(component){
    	var selections = this.getView().getSelectionModel().getSelection();
    	var selectionNum = selections.length;
    	if(selectionNum != 1){
    		Ext.MessageBox.alert("请求失败", "请选择一个品牌进行系列添加");
    		return;
    	}
    	var brandview = Ext.ComponentQuery.query("brandview")[0];
    	brandview.getEl().mask();
    	
    	var editWindow = Ext.create("Ext.window.Window", {
        	title: "添加系列,所属品牌["+selections[0].raw.brandName+"]",
        	id: "seriesaddwindow",
        	extraData: selections[0].raw,
        	renderTo: brandview.getEl(),
        	height: 180,
        	width: 575,
        	layout: "fit",
        	closeAction: "destroy",
        	items: [{
        		xtype: "seriesadd"
        	}],
        	listeners: {
        		close: function(){
        			brandview.getEl().unmask();
        		},
        		beforerender: function () {
               	 	var isUse = Ext.ComponentQuery.query("seriesadd combo[name=isUse]")[0];
                    var isUseStore = isUse.getStore();
                    isUseStore.load();
               },
        		afterrender: function(component, eOpts){
        			component.down("textfield[name=ofOrigin]").setValue(this.extraData.brandId);
        			component.down("combo[name=isUse]").setValue("1");
    			}
        	}
        });
    	editWindow.show();
    },
	
	onExpandSearchClick: function(component){
		var searchwindow = Ext.ComponentQuery.query("brandSearchpanel")[0];
    	if(searchwindow.isHidden()){
    		searchwindow.show();
    	} else {
    		searchwindow.form.reset();
    		searchwindow.hide();
    	}
	}
	
});