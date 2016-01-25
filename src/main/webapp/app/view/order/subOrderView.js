/**
 * 描述：子订单界面
 * 作者：王航
 */
Ext.define("MIS.view.order.subOrderView", {
	extend: "Ext.panel.Panel",
	alias: "widget.suborderview",
	
	requires: [
	  "MIS.view.order.SubOrderGrid"         
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
        	id: "subordersearchpanel",
        	xtype: "subOrderSearchpanel",
        	height: 100,
        	hidden: true,
        	style: "border-bottom: 5px solid #3892d3;"
        }, {
    		xtype: "subordergrid",
    		flex: 1
    	}]
	}]
	
	
});