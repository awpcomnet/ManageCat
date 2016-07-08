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
	    "MIS.view.check.CheckModify",
	    "MIS.view.check.CheckForceModify"
	    
	],
	
	init: function(){
		this.control({
			"checkgrid": {
				render: this.onCheckGridRender
			}
		});
	},
	
	onCheckGridRender: function(component, options){
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