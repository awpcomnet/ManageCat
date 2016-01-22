/**
 * 描述: 用于栏目数据的展示与编辑
 * 作者: 
 */
Ext.define("MIS.view.catalog.CatalogData", {
    extend: "Ext.panel.Panel",
    alias: "widget.catalogdata",

    anchor : '100%',
    
    layout: {
    	type: "vbox",
    	align: 'stretch'
    },

    items: [{
		id: "catalogsearchpanel",
    	xtype: "catalogSearchpanel",
    	height: 95,
    	hidden: true,
    	style: "border-bottom: 5px solid #3892d3;"
	}, {
        xtype: "cataloggrid",
        region: "center",
        flex: 1
    }]
});
