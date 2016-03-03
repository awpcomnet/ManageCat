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
	    "MIS.view.order.SubOrderModify",
	    "MIS.view.order.SubOrderAdd",
	    "MIS.view.order.SubOrderSplit",
	    "MIS.view.order.SubOrderMerge",
	    "MIS.view.order.SubOrderDetail",
	    "MIS.view.order.SellAndBack"
	    
	],
	
	init: function(){
		this.control({
			"subordergrid": {
				render: this.onSubOrderGridRender
			}
		});
	},
	
	onSubOrderGridRender: function(component, options){
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