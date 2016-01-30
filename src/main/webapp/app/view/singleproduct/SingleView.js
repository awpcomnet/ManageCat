/**
 * 描述：单品界面
 * 作者：王航
 */
Ext.define("MIS.view.singleproduct.SingleView", {
	extend: "Ext.panel.Panel",
	alias: "widget.singleview",
	
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
        	id: "singlesearchpanel",
        	xtype: "singleSearchpanel",
        	height: 100,
        	hidden: true,
        	style: "border-bottom: 5px solid #3892d3;"
        }, {
    		xtype: "singlegrid",
    		flex: 1
    	}]
	}]
	
	
});