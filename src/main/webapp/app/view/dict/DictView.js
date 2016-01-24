/**
 * 描述: 字典管理视图
 * 作者: 
 */
Ext.define("MIS.view.dict.DictView", {
    extend: "Ext.panel.Panel",
    alias: "widget.dictview",

    anchor : '100%',

    layout: {
        type: "hbox",
        pack: 'start', 
        align: 'stretch'
    },

    items: [{
        xtype: "dicttree",
        width: 200,
        style: "border-right: 1px solid #3892d3"
    }, {
        xtype: "dictgrid",
        width: 290,
        style: "border-right: 1px solid #3892d3"
    }, {
        xtype: "container",
        flex: 1,
        layout: {
        	type: "vbox",
        	align: 'stretch'
        },
        items: [{
        	id: "dictsearchpanel",
        	xtype: "dictSearchpanel",
        	height: 95,
        	hidden: true,
        	style: "border-bottom: 5px solid #3892d3;"
        }, {
        	xtype: "dictitemgrid",
        	flex: 1
        }]
    }]
});
