/**
 * 描述：入库清单界面
 * 作者：王航
 */
Ext.define("MIS.view.storage.StorageView", {
	extend: "Ext.panel.Panel",
	alias: "widget.storageview",
	
	requires: [
	  "MIS.view.storage.StorageGrid"         
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
        	id: "storagesearchpanel",
        	xtype: "storageSearchpanel",
        	height: 100,
        	hidden: true,
        	style: "border-bottom: 5px solid #3892d3;"
        }, {
    		xtype: "storagegrid",
    		flex: 1
    	}]
	}]
	
	
});