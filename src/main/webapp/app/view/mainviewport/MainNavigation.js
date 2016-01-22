/**
 * 功能: 功能导航面板
 * 作者: 
 */
Ext.define("MIS.view.mainviewport.MainNavigation", {
    extend: "Ext.panel.Panel",
    alias: "widget.mainnav",

	controllers: ["MIS.controller.mainviewport.MainNavigation"],

    title: "功能导航",

    width: 260,
    split: true,
    collapsible: true,

    layout: {
    	type: "accordion"
    }
});
