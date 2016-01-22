/**
 * 描述: 用于编辑栏目信息
 * 作者: 
 */
Ext.define("MIS.view.catalog.CatalogEdit", {
    extend: "Ext.panel.Panel",
    alias: "widget.catalogedit",

    layout: {
        type: "fit"
    },

    height: 280,
    split: true,

    items: [{
        xtype: "form",
        layout: "form",
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
                    flex: 1
                }, {
                    xtype: "textfield",
                    fieldLabel: "英文标识",
                    name: "abbr",
                    flex: 1
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
                    flex: 1
                }, {
                    xtype: "textfield",
                    fieldLabel: "排序值",
                    name: "orderNum",
                    flex: 1
                }]
            }, {
                xtype: "container",
                layout: "hbox",
                margin: "0 0 5 0",
                items: [{
                    xtype: "textfield",
                    fieldLabel: "URL类型",
                    name: "urlType",
                    flex: 1
                }, {
                    xtype: "textfield",
                    fieldLabel: "URL地址",
                    name: "url",
                    flex: 1
                }]
            }]
        }]
    }],

    buttons: [{
        text: '恢复修改之前值',
        scope: this,
        handler: this.onResetClick
    }, {
        text: '提交',
        scope: this,
        backgroundColor: "red",
        handler: this.onCompleteClick
    }]
});
