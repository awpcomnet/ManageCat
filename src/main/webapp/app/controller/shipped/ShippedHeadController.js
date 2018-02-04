/**
 * 邮寄清单主单控制器
 */
Ext.define("MIS.controller.shipped.ShippedHeadController", {
	extend: "Ext.app.Controller",
	
	models : [ 
	    "MIS.model.shipped.ShippedHead" 
	],

	stores : [ 
	    "MIS.store.shipped.ShippedHeadStore",
	    "MIS.store.shipped.ShippedStore"
	],
	
	views: [
	    "MIS.view.check.ShippedHeadAdd",
	    "MIS.view.shipped.ShippedView",
	    "MIS.view.shipped.ShippedGrid",
	    "MIS.view.shipped.ShippedSearch",
	    "MIS.view.shipped.ShippedModify",
	    
	    "MIS.view.shipped.ShippedsGrid",
	    "MIS.view.shipped.ShippedsModify",
	    "MIS.view.shipped.StorageAddContinue",
	    "MIS.view.shipped.DivisionOrder"
	    
	],
	
	init: function(){
		this.control({
			"shippedgrid": {
				render: this.onShippedGridRender
			}
		});
	},
	
	onShippedGridRender: function(component, options){
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