/**
 * 售出控制器
 */
Ext.define("MIS.controller.selled.SelledController", {
	extend: "Ext.app.Controller",
	
	models : [ 
	    "MIS.model.selled.Selled" 
	],

	stores : [ 
	    "MIS.store.selled.SelledStore"
	],
	
	views: [
	    "MIS.view.storage.SelledAdd",
	    "MIS.view.selled.SelledSearch",
	    "MIS.view.selled.SelledGrid",
	    "MIS.view.selled.SelledView",
	    "MIS.view.selled.SelledModify",
	    "MIS.view.selled.SelledRefund",
	    "MIS.view.storage.DestroyAdd"
	    
	],
	
	init: function(){
		this.control({
			"selledgrid": {
				render: this.onSelledGridRender
			}
		});
	},
	
	onSelledGridRender: function(component, options){
		component.getStore().load();
	}
	
	
	
});