/**
 * 描述：邮寄清单界面
 * 作者：王航
 */
Ext.define("MIS.view.shipped.ShippedView", {
	extend: "Ext.panel.Panel",
	alias: "widget.shippedview",
	
	requires: [
	  "MIS.view.shipped.ShippedGrid"         
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
        	id: "shippedsearchpanel",
        	xtype: "shippedSearchpanel",
        	height: 100,
        	hidden: true,
        	style: "border-bottom: 5px solid #3892d3;"
        }, {
    		xtype: "shippedgrid",
    		flex: 1
    	}]
	}]
	
	
});