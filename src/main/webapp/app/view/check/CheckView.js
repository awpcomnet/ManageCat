/**
 * 描述：下单清单界面
 * 作者：王航
 */
Ext.define("MIS.view.check.CheckView", {
	extend: "Ext.panel.Panel",
	alias: "widget.checkview",
	
	requires: [
	  "MIS.view.check.CheckGrid"         
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
        	id: "checksearchpanel",
        	xtype: "checkSearchpanel",
        	height: 100,
        	hidden: true,
        	style: "border-bottom: 5px solid #3892d3;"
        }, {
    		xtype: "checkgrid",
    		flex: 1
    	}]
	}]
	
	
});