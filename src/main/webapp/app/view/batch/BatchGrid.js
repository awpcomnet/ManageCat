/**
 * 批次号数据显示
 */
Ext.define("MIS.view.batch.BatchGrid", {
	extend: "Ext.grid.Panel",
	alias: "widget.batchgrid",
	
	requires: [
	     'Ext.form.field.Text',
	     'Ext.toolbar.TextItem',
	     'Ext.toolbar.Paging'
	],
	
	store: "MIS.store.batch.BatchStore",
	
	initComponent: function(){
		//创建多选框
		//var checkBox = Ext.create('Ext.selection.CheckboxModel');
		
		Ext.apply(this, {
			frame: false,
			
			forceFit: true,
			scrollOffSet: 0,
			
			//selModel: checkBox,
			disableSelection: false, //表示可以选择行
			columnLines: true,
			loadMask: true,
			
			columns: [
			    { header: '批次号', dataIndex: 'batchNo', sortable: true, width: 10, align: "center"},
			    { header: '拥有的下单清单数', dataIndex: 'orderNum', sortable: true, width: 15, align: "center"},
			    { header: '总金额($)', dataIndex: 'sumPrice', sortable: true, width: 15, align: "center"},
			    { header: '创建时间', dataIndex: 'createDateFormat', sortable: true, width: 15, align: "center"}
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
				items: [ '->', {
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
	}
	
});