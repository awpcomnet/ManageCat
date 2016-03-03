/**
 * 订单控制器
 */
Ext.define("MIS.controller.order.OrderController", {
	extend: "Ext.app.Controller",
	
	models : [ 
	    "MIS.model.order.Order" 
	],

	stores : [ 
	    "MIS.store.order.OrderStore"
	],
	
	views: [
	    "MIS.view.order.OrderView",
	    "MIS.view.order.OrderSearch",
	    "MIS.view.order.OrderModify",
	    "MIS.view.order.SubOrderAddForOrder",
	    "MIS.view.order.OrderAdd"
	    
	],
	
	init: function(){
		this.control({
			"ordergrid": {
				render: this.onOrderGridRender
			}
		});
	},
	
	onOrderGridRender: function(component, options){
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