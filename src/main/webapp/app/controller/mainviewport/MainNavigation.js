/**
 * 描述: 功能导航面板的项目处理
 * 作者: 
 */
Ext.define("MIS.controller.mainviewport.MainNavigation", {
    extend: "Ext.app.Controller",

    models: [
        "MIS.model.nav.Root",
        "MIS.model.nav.Item"
    ],

    views: [
        "MIS.view.mainviewport.MainNavigation", 
        "MIS.view.mainviewport.MainNavigationItem"
        //"MIS.view.mainviewport.MainHeader"
    ],

    stores: [
        "Nav"
    ],

    refs: [{
        ref: "mainTab",
        selector: "maintab"
    }],

    init: function () {
        this.control({
            "mainnav": {
                render: this.onNavigationRender
            },
            "mainnav mainnavitem": {
                select: this.onTreePanelSelect,
                itemclick: this.onItemClick
            }
        });
    },

    onNavigationRender: function (abstractcomponent, options) {
        this.getNavStore().load(function (record, op, success) {
            var mainnav = Ext.ComponentQuery.query("mainnav")[0],
                maintab = Ext.ComponentQuery.query("maintab");

            Ext.each(record, function (cata) {
                var menu = Ext.create("MIS.view.mainviewport.MainNavigationItem", {
                    title: cata.raw.text,
                    iconCls: cata.raw.iconCls,
                    cls: "v-nav-one",
                    store: Ext.create("Ext.data.TreeStore", {
                        fields : [{
                            name : 'className',
                            type : 'string'
                        }, {
                            name : 'text',
                            type : 'string'
                        }, {
                            name : 'iconCls',
                            type : 'string'
                        }],

                        proxy: {
                            type: "ajax",
                            url: "/catalog/queryUserCatalog",
                            reader: {
                                type: "json",
                                root: "results"
                            },
                            extraParams: {
                                parentId: "0"
                            }
                        },

                        root: {
                            nodeType: 'async',
                            text: cata.get("text"),
                            expanded: true,
                            id: cata.get("id")
                        },

                        listeners: {
                            beforeexpand: function (node, eOpts) {
                                this.proxy.extraParams.parentId = node.raw.id;
                            }
                        }
                    })
                });

                // Ext.each(root.subCatalogs(), function (its) {
                //     Ext.each(its.data.items, function (item) {
                //         var isLeaf = !item.raw.subCatalogs;
                        
                //         var cltItemElem = menu.getRootNode().appendChild({
                //             text: item.get("name"),
                //             leaf: isLeaf,
                //             iconCls: item.get("icon"),
                //             cls: "v-nav-item",
                //             url: item.get("url")
                //         })
                        
                //         if (!isLeaf) {
                //             Ext.each(item.raw.subCatalogs, function (cltItem) {
                //                 console.log(cltItem);
                                
                //                 cltItemElem.appendChild({
                //                     text: cltItem.name,
                //                     leaf: true,
                //                     iconCls: cltItem.icon,
                //                     cls: "v-nav-item",
                //                     url: cltItem.url
                //                 });
                //             });
                //         }
                //     });
                // });

                mainnav.add(menu);
            });
        });
    },

    onTreePanelSelect: function (selMode, record, index, eOpts) {
        if (!record.isLeaf()) {
            return;
        }

        var maintab = this.getMainTab();
        
        var newtab = maintab.items.findBy(function (tab) {
            return tab.title == record.get("text");
        });

        var urlType = record.raw.urlType || "1",
            url = record.raw.url || "panel";

        var tab = {
            closable: true,
            iconCls: record.get("iconCls"),
            title: record.get("text")
        };

        if (urlType == 1) {
            tab.xtype = url;
        } else {
            tab.autoLoad = url;
        }

        if (!newtab) {
            newtab = maintab.add(tab);
        }

        maintab.setActiveTab(newtab);
    },

    onItemClick: function (selMode, record, index, eOpts) {
        this.onTreePanelSelect(selMode, record, index, eOpts);
    }
});
