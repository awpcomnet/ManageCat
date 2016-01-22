/**
 * 描述: 中央内容展示区
 * 作者: 
 */
Ext.define("MIS.view.mainviewport.MainTabPanel", {
    extend: "Ext.tab.Panel",
    alias: "widget.maintab",

    requires: [
        'Ext.tip.QuickTipManager',
        'Ext.tab.Panel',
        'Ext.ux.TabScrollerMenu',
        'Ext.ux.TabReorderer',
        'Ext.ux.TabCloseMenu'
    ],

    activeTab: 0,
    enableTabScroll: true,

    plugins: [
        Ext.create('Ext.ux.TabReorderer')
    ],

    items: [{
        title: '欢迎',
        closable: false,
        iconCls: "icon-home",
        xtype: "panel",
        reorderable: false,
        layout: {
        	type: "fit"
        },
        html: "<iframe src=\"/druid\" width=\"100%\" height=\"100%\"></iframe>"
    }]
});
