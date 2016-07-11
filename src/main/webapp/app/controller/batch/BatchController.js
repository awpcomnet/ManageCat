/**
 * 品牌控制器
 */
Ext.define("MIS.controller.batch.BatchController", {
	extend: "Ext.app.Controller",
	
	models : [ 
	    "MIS.model.batch.Batch" 
	],

	stores : [ 
	    "MIS.store.batch.BatchStore",
	    "MIS.store.batch.BatchAssignStore"
	],
	
	views: [
	    "MIS.view.batch.BatchView",
	    "MIS.view.batch.BatchGrid"
	],
	
	init: function(){
		this.control({
			"batchgrid": {
				render: this.onBatchGridRender
			}
		});
	},
	
	onBatchGridRender: function(component, options){
		component.getStore().load({
			callback: function(records, operation, success){
				var result = Ext.JSON.decode(operation.response.responseText);
				if(result.resultCode != 0){
					 Ext.MessageBox.alert("错误提示", "错误原因：" + result.resultMessage);
				}
	        }
		});
	}
	
	
	
});