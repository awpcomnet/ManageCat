/**
 * 描述：主订单界面
 * 作者：王航
 */
Ext.define("MIS.view.order.OrderView", {
	extend: "Ext.panel.Panel",
	alias: "widget.orderview",
	
	requires: [
	  "MIS.view.order.OrderGrid"         
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
        	id: "ordersearchpanel",
        	xtype: "orderSearchpanel",
        	height: 100,
        	hidden: true,
        	style: "border-bottom: 5px solid #3892d3;"
        }, {
    		xtype: "ordergrid",
    		flex: 1
    	}]
	}]
	
	
});