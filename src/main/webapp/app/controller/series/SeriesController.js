/**
 * 系列控制器
 */
Ext.define("MIS.controller.series.SeriesController", {
	extend: "Ext.app.Controller",
	
	models : [ 
	    "MIS.model.series.Series" 
	],

	stores : [ 
	    "MIS.store.series.SeriesStore"
	],
	
	views: [
	    "MIS.view.series.SeriesView",
	    "MIS.view.series.SeriesGrid",
	    "MIS.view.series.SeriesSearch",
	    "MIS.view.series.SeriesAdd",
	    "MIS.view.series.SeriesModify",
	    "MIS.view.series.SingleAdd"
	    
	],
	
	init: function(){
		this.control({
			"seriesgrid": {
				render: this.onSeriesGridRender
			}
		});
	},
	
	onSeriesGridRender: function(component, options){
		component.getStore().load();
	}
	
	
	
});