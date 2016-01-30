/**
 * 单品控制器
 */
Ext.define("MIS.controller.singleproduct.SingleController", {
	extend: "Ext.app.Controller",
	
	models : [ 
	    "MIS.model.singleproduct.Singleproduct" 
	],

	stores : [ 
	    "MIS.store.singleproduct.SingleproductStore"
	],
	
	views: [
	    "MIS.view.singleproduct.SingleView",
	    "MIS.view.singleproduct.SingleGrid",
	    "MIS.view.singleproduct.SingleAdd",
	    "MIS.view.singleproduct.SingleModify",
	    "MIS.view.singleproduct.SingleSearch"
	    
	],
	
	init: function(){
		this.control({
			"singlegrid": {
				render: this.onSingleGridRender
			}
		});
	},
	
	onSingleGridRender: function(component, options){
		component.getStore().load();
	}
	
	
	
});