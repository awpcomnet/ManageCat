/**
 * 子订单控制器
 */
Ext.define("MIS.controller.order.SubOrderController", {
	extend: "Ext.app.Controller",
	
	models : [ 
	    "MIS.model.order.SubOrder" 
	],

	stores : [ 
	    "MIS.store.order.SubOrderStore"
	],
	
	views: [
	    "MIS.view.order.subOrderView",
	    "MIS.view.order.SubOrderGrid",
	    "MIS.view.order.SubOrderSearch",
	    "MIS.view.order.SubOrderModify"
	    
	],
	
	init: function(){
		this.control({
			"subordergrid": {
				render: this.onSubOrderGridRender
			}
		});
	},
	
	onSubOrderGridRender: function(component, options){
		component.getStore().load();
	}
	
	
	
});