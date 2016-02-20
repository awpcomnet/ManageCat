/**
 * 邮寄清单主单控制器
 */
Ext.define("MIS.controller.shipped.ShippedHeadController", {
	extend: "Ext.app.Controller",
	
	models : [ 
	    "MIS.model.shipped.ShippedHead" 
	],

	stores : [ 
	    "MIS.store.shipped.ShippedHeadStore"
	],
	
	views: [
	    "MIS.view.check.ShippedHeadAdd",
	    "MIS.view.shipped.ShippedView",
	    "MIS.view.shipped.ShippedGrid",
	    "MIS.view.shipped.ShippedSearch",
	    "MIS.view.shipped.ShippedModify"
	    
	],
	
	init: function(){
		this.control({
			"shippedgrid": {
				render: this.onShippedGridRender
			}
		});
	},
	
	onShippedGridRender: function(component, options){
		component.getStore().load();
	}
	
	
	
});