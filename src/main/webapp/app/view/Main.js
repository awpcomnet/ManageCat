Ext.define('MIS.view.Main', {
    extend: 'Ext.container.Container',
    requires:[
        'Ext.tab.Panel',
        'Ext.layout.container.Border'
    ],
    
    xtype: 'app-main',

    requires: [
               //"MIS.view.mainviewport.MainHeader",
               "MIS.view.mainviewport.MainNavigation",
               "MIS.view.mainviewport.MainTabPanel",
               "MIS.view.mainviewport.MainFooter"
           ],
           
           layout: {
               type: "border"
           },
           
           items: [{
               xtype: "mainheader",
               region: "north",
               cls: "v-header overflow-vis"
           }, {
               title: "功能导航",
               xtype: "mainnav",
               region: "west",
               cls: "v-navigation"
           },  {
               xtype: "maintab",
               region: "center",
               cls: "v-center"
           }, {
               xtype: "mainfooter",
               region: "south",
               cls: "v-footer"
           }]
});