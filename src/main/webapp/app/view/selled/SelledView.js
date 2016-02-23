/**
 * 描述：售出清单界面
 * 作者：王航
 */
Ext.define("MIS.view.selled.SelledView", {
	extend: "Ext.panel.Panel",
	alias: "widget.selledview",
	
	requires: [
	  "MIS.view.selled.SelledGrid"         
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
        	id: "selledsearchpanel",
        	xtype: "selledSearchpanel",
        	height: 100,
        	hidden: true,
        	style: "border-bottom: 5px solid #3892d3;"
        }, {
    		xtype: "selledgrid",
    		flex: 1
    	}]
	}]
	
	
});