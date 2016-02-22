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
	    "MIS.view.shipped.StorageAdd"
	],
	
	init: function(){
		this.control({
			"storagegrid": {
				render: this.onStorageGridRender
			}
		});
	},
	
	onStorageGridRender: function(component, options){
		component.getStore().load();
	}
	
});