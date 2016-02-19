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
	    "MIS.view.check.ShippedHeadAdd"
	    
	],
	
	init: function(){
		this.control({
			"shippedheadgrid": {
				render: this.onShippedHeadGridRender
			}
		});
	},
	
	onShippedHeadGridRender: function(component, options){
		component.getStore().load();
	}
	
	
	
});