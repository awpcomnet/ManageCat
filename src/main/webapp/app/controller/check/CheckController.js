/**
 * 下单清单控制器
 */
Ext.define("MIS.controller.check.CheckController", {
	extend: "Ext.app.Controller",
	
	models : [ 
	    "MIS.model.check.Check" 
	],

	stores : [ 
	    "MIS.store.check.CheckStore"
	],
	
	views: [
	    "MIS.view.check.CheckView",
	    "MIS.view.check.CheckGrid",
	    "MIS.view.check.CheckSearch",
	    "MIS.view.check.CheckAdd",
	    "MIS.view.check.CheckModify"
	    
	],
	
	init: function(){
		this.control({
			"checkgrid": {
				render: this.onCheckGridRender
			}
		});
	},
	
	onCheckGridRender: function(component, options){
		component.getStore().load();
	}
	
	
	
});