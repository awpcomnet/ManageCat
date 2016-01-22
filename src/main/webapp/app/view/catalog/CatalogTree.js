/**
 * 描述: 栏目树形视图
 * 作者: 
 */
Ext.define("MIS.view.catalog.CatalogTree", {
    extend: "Ext.tree.Panel",
    alias: "widget.catalogtree",

    store: "MIS.store.catalog.CatalogStore",
    
    id: "catalogstructure",
    title: "栏目结构",
    width: 280,
    split: true,

    listeners: {
        "itemcontextmenu": function (component, record, item, index, event, eOpts) {
            var menu = null;

            event.preventDefault();
            event.stopEvent();

            var deleteCatalogById = function (id) {

            };
            console.log(record.raw);
            if (!record.raw.leaf) {
                menu = Ext.create("Ext.menu.Menu", {
                    floating: true,
                    items: [{
                        text: "刷新",
                        handler: function () {
                            component.up("catalogtree").getStore().reload();
                        }
                    }, {
                        text: "新建子节点",
                        handler: function () {
                        	var catalogview = Ext.ComponentQuery.query("catalogview")[0];
                        	catalogview.getEl().mask();
                        	
                            var title = "新增 栏目[" + record.raw.text + "] 的子栏目",
                                parentId = record.raw.id;

                            var opWindow = Ext.create("MIS.view.catalog.CatalogAdd", {
                                title: title,
                                parentId: parentId,
                                opType: "A",
                                listeners: {
                                	close: function(){
                                		catalogview.getEl().unmask();
                                	}
                                }
                            });
                            opWindow.show();
                        }
                    }]
                })
                menu.showAt(event.getXY());
            } else {
                menu = Ext.create("Ext.menu.Menu", {
                    floating: true,
                    items: [{
                        text: "刷新",
                        handler: function () {
                            component.up("catalogtree").getStore().reload();
                        }
                    }, {
                        text: "新建子节点",
                        handler: function () {
                        	var catalogview = Ext.ComponentQuery.query("catalogview")[0];
                        	catalogview.getEl().mask();
                        	
                            var title = "新增 栏目[" + record.raw.text + "] 的子栏目",
                                parentId = record.raw.id;

                            var opWindow = Ext.create("MIS.view.catalog.CatalogAdd", {
                                title: title,
                                parentId: parentId,
                                opType: "A",
                                listeners: {
                                	close: function () {
                        				catalogview.getEl().unmask();
                                    }
                                }
                            });
                            opWindow.show();
                        }
                    }, {
                        text: "编辑节点",
                        handler: function (component) {
                        	var catalogview = Ext.ComponentQuery.query("catalogview")[0];
                        	catalogview.getEl().mask();
                        	
                        	var title = "编辑 ["+record.raw.text+"] 栏目";
                        	var editWindow = Ext.create("MIS.view.catalog.CatalogModify", {
                        		title: title,
                        		extraData: record.raw,
                        		id: "catalogmodifywindow",
                        		opType: "A",
                        		
                        		listeners: {
                        			close: function () {
                        				catalogview.getEl().unmask();
                                    },
                        			afterrender: function(component, eOpts){
                        				component.down("textfield[name=name]").setValue(this.extraData.text);
                        				component.down("textfield[name=abbr]").setValue(this.extraData.abbr);
                        				component.down("textfield[name=description]").setValue(this.extraData.description);
                        				component.down("checkbox[name=state]").setValue(this.extraData.state);
                        				component.down("textfield[name=icon]").setValue(this.extraData.iconCls);
                        				component.down("textfield[name=orderNum]").setValue(this.extraData.orderNum);
                        				component.down("textfield[name=urlType]").setValue(this.extraData.urlType);
                        				component.down("textfield[name=url]").setValue(this.extraData.url);
                        			}
                        		}
                        	});
                        	editWindow.show();
                        	
                        }
                    }, {
                        text: "删除节点",
                        handler: function () {
                            var catalogId = record.raw.id,
                                name = record.raw.text;
                            console.log(record);
                            Ext.MessageBox.confirm("删除提示", "确定删除栏目["+ catalogId +" - " + name + "]", function (confirmId) {
                                if (confirmId == "yes") {
                                    Ext.Ajax.request({
                                        url: "/catalog/delete",
                                        params: {
                                            id: catalogId
                                        },
                                        success: function () {
                                            Ext.ComponentQuery.query("catalogtree")[0].getStore().reload();
                                            Ext.ComponentQuery.query("cataloggrid")[0].store.reload();
                                        },
                                        fail: function () {
                                            Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                                        }
                                    });
                                }
                            });
                        }
                    }]  
                });
                menu.showAt(event.getXY());
            }
        }
    }
});
