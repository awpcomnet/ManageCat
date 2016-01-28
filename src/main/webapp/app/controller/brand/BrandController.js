/**
 * 品牌控制器
 */
Ext.define("MIS.controller.brand.BrandController", {
	extend: "Ext.app.Controller",
	
	models : [ 
	    "MIS.model.brand.Brand" 
	],

	stores : [ 
	    "MIS.store.brand.BrandStore"
	],
	
	views: [
	    "MIS.view.brand.BrandView",
	    "MIS.view.brand.BrandGrid",
	    "MIS.view.brand.BrandSearch",
	    "MIS.view.brand.BrandAdd",
	    "MIS.view.brand.BrandModify",
	    "MIS.view.brand.SeriesAdd"
	    
	],
	
	init: function(){
		this.control({
			"brandgrid": {
				render: this.onBrandGridRender
			}
		});
	},
	
	onBrandGridRender: function(component, options){
		component.getStore().load();
	}
	
	
	
});