/**
 * 描述：批次号界面
 * 作者：王航
 */
Ext.define("MIS.view.batch.BatchView", {
	extend: "Ext.panel.Panel",
	alias: "widget.batchview",
	
	requires: [
	  "MIS.view.batch.BatchGrid"         
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
    		xtype: "batchgrid",
    		flex: 1
    	}]
	}]
	
	
});