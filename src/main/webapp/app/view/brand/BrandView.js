/**
 * 描述：品牌界面
 * 作者：王航
 */
Ext.define("MIS.view.brand.BrandView", {
	extend: "Ext.panel.Panel",
	alias: "widget.brandview",
	
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
        	id: "brandsearchpanel",
        	xtype: "brandSearchpanel",
        	height: 100,
        	hidden: true,
        	style: "border-bottom: 5px solid #3892d3;"
        }, {
    		xtype: "brandgrid",
    		flex: 1
    	}]
	}]
	
	
});