/**
 * 入库清单控制器
 */
Ext.define("MIS.controller.storage.StorageController", {
	extend: "Ext.app.Controller",
	
	models : [ 
	    "MIS.model.storage.Storage" 
	],

	stores : [ 
	    "MIS.store.storage.StorageStore"
	],
	
	views: [
	    "MIS.view.storage.StorageView",
	    "MIS.view.storage.StorageGrid",
	    "MIS.view.storage.StorageSearch",
	    "MIS.view.shipped.StorageAdd",
	    "MIS.view.storage.StorageModify"
	],
	
	init: function(){
		this.control({
			"storagegrid": {
				render: this.onStorageGridRender
			}
		});
	},
	
	onStorageGridRender: function(component, options){
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