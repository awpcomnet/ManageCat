/**
 * 描述：系列界面
 * 作者：王航
 */
Ext.define("MIS.view.series.SeriesView", {
	extend: "Ext.panel.Panel",
	alias: "widget.seriesview",
	
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
        	id: "seriessearchpanel",
        	xtype: "seriesSearchpanel",
        	height: 100,
        	hidden: true,
        	style: "border-bottom: 5px solid #3892d3;"
        }, {
    		xtype: "seriesgrid",
    		flex: 1
    	}]
	}]
	
	
});