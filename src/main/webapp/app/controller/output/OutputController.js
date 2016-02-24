/**
 * 导出控制器
 */
Ext.define("MIS.controller.output.OutputController", {
	extend: "Ext.app.Controller",
	
	models : [ 
	],

	stores : [ 
	],
	
	views: [
	    "MIS.view.output.ProfitView",
	    "MIS.view.output.ProfitTable"
	    
	],
	
	init: function(){
//		this.control({
//			"selledgrid": {
//				render: this.onSelledGridRender
//			}
//		});
	},
	
//	onSelledGridRender: function(component, options){
//		component.getStore().load();
//	}
	
	
	
});