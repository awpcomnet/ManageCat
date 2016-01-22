/**
 * 描述: 增加栏目
 * 作者: 
 */
Ext.define("MIS.view.catalog.CatalogAdd", {
    extend: "Ext.window.Window",
    alias: "widget.catalogadd",

    width: 600,
    height: 300,
    closable: true,
    closeAction: "destroy",

    layout: {
        type: "fit"
    },

    renderTo: Ext.ComponentQuery.query("catalogview")[0],

    items: [{
        xtype: "form",
        layout: "form",
        padding: "5",
        defaultType: "textfield",
        fieldDefaults: {
            labelAlign: 'right',
            labelWidth: 80,
            msgTarget: 'qtip'
        },
        items: [{
            xtype: "fieldset",
            title: "栏目基本信息",
            layout: "anchor",
            margin: "0 5 0 0",
            defaults: {
                anchor: "100%"
            },
            items: [{
                xtype: "container",
                layout: "hbox",
                margin: '0 0 5 0',
                items: [{
                    xtype: "textfield",
                    fieldLabel: "栏目名称",
                    name: "name",
                    flex: 0.5
                }, {
                    xtype: "textfield",
                    fieldLabel: "英文标识",
                    name: "abbr",
                    flex: 0.5
                }]
            }, {
                xtype: "textfield",
                fieldLabel: "描述",
                name: "description"
            }]
        }, {
            xtype: "fieldset",
            title: "栏目属性",
            layout: "anchor",
            margin: "0 5 0 0",
            defaults: {
                anchor: "100%"
            },
            items: [{
                xtype: "container",
                layout: "hbox",
                margin: "0 0 5 0",
                items: [{
                    xtype: "checkbox",
                    fieldLabel: "是否启用",
                    name: "state"
                }]
            }, {
                xtype: "container",
                layout: "hbox",
                margin: "0 0 5 0",
                items: [{
                    xtype: "textfield",
                    fieldLabel: "图标样式",
                    name: "icon",
                    flex: 0.5
                }, {
                    xtype: "numberfield",
                    fieldLabel: "排序值",
                    name: "orderNum",
                    flex: 0.5
                }]
            }, {
                xtype: "container",
                layout: "hbox",
                margin: "0 0 5 0",
                items: [{
                    xtype: "textfield",
                    fieldLabel: "URL类型",
                    name: "urlType",
                    flex: 0.5
                }, {
                    xtype: "textfield",
                    fieldLabel: "URL地址",
                    name: "url",
                    flex: 0.5
                }]
            }]
        }]
    }],

    buttons: [{
        text: "关闭",
        handler: function (component) {
            component.up("catalogadd").close();
        }
    }, {
        text: '新增',
        handler: function (component) {
            var catalogAdd = component.up("catalogadd");

            var parentId = catalogAdd.parentId,
                name = catalogAdd.down("textfield[name=name]").getValue(),
                abbr = catalogAdd.down("textfield[name=abbr]").getValue(),
                url = catalogAdd.down("textfield[name=url]").getValue(),
                urlType = catalogAdd.down("textfield[name=urlType]").getValue(),
                description = catalogAdd.down("textfield[name=description]").getValue(),
                orderNum = catalogAdd.down("numberfield[name=orderNum]").getValue(),
                state = catalogAdd.down("checkbox[name=state]").getValue(),
                icon = catalogAdd.down("textfield[name=icon]").getValue();
            
            var params = {
                parentId: parentId,
                name: name,
                abbr: abbr,
                urlType: urlType,
                url: url,
                description: description,
                order: orderNum || 0,
                state: state,
                icon: icon
            };

            Ext.Ajax.request({
                url: "/catalog/add",
                params: params,
                success: function (conn, request, option, eOpts) {
                    var result = Ext.JSON.decode(conn.responseText, true);
                    if (result.resultCode != 0) {
                        Ext.MessageBox.alert("请求增加栏目失败, 稍后重试!");
                    }
                    Ext.ComponentQuery.query("catalogtree")[0].getStore().reload();
                    Ext.ComponentQuery.query("cataloggrid")[0].getStore().reload();
                    component.up("catalogadd").close();
                },
                failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
            });
        }
    }]
});
