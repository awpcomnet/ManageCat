/**
 * 描述: 栏目展示
 * 作者: 
 */
Ext.define("MIS.view.catalog.CatalogView", {
    extend: "Ext.panel.Panel",
    alias: "widget.catalogview",

    layout: {
        type: "border"
    },

    items: [{
        xtype: "catalogtree",
        region: "west"
    }, {
        xtype: "catalogdata",
        region: "center"
    }]
});
