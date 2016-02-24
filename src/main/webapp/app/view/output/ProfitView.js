/**
 * 描述：导出界面
 * 作者：王航
 */
Ext.define("MIS.view.output.ProfitView", {
	extend: "Ext.panel.Panel",
	alias: "widget.profitview",
	
	requires: [
	],
	
	layout: {
		type: 'fit'
	},
	
	items: [{
		xtype: "container",
        flex: 1,
        layout: {
        	type: "vbox",
        	align: 'stretch'
        },
        items: [{
    		xtype: "profittable",
    		flex: 1
    	}]
	}]
	
	
});