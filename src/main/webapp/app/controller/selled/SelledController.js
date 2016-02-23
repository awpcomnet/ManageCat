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
	    "MIS.view.storage.SelledAdd"
	    
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