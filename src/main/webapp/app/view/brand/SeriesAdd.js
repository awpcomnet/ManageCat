/**
 * 描述: 增加系列界面
 * 作者: 王航
 */
Ext.define("MIS.view.brand.SeriesAdd", {
    extend: "Ext.form.Panel",
    alias: "widget.seriesadd",

    defaultType: "textfield",
    defaults: {
        anchor: "100%",
        padding: "5 5"
    },
    layout: {
    	type: 'table',
    	columns:2
    },

    items: [{
		fieldLabel: "系列名称",
	    name: "seriesName",
	    allowBlank: true
    }, {
		fieldLabel: "系列英文名",
	    name: "seriesEname",
	    allowBlank: true
    }, {
		fieldLabel: "是否启用",
        name: "isUse",
        xtype: "combo",
        store: Ext.create("MIS.store.dict.DictQueryStore", {
        	dictcode: "onOff"
        }),
        mode: "local",
        displayField: 'name',
        valueField: "value",
        allowBlank: true,
        editable:false
	}, {
	    name: "ofOrigin",
	    readOnly: true,
	    hidden: true
    }],

    buttons: [{
        text: "取消",
        handler: function (component) {
            component.up("#seriesaddwindow").close();
        }
    }, {
        text: "新增",
        handler: function (component) {
        	var form = component.up("seriesadd");
        	var params = form.getForm().getValues();
            Ext.Ajax.request({
                url: "/Series/add",
                params: params,
                success: function (conn, request, option, eOpts) {
                    var result = Ext.JSON.decode(conn.responseText, true);
                    if (result.resultCode != 0) {
                        Ext.MessageBox.alert("请求失败", "添加系列失败 " + result.resultMessage);
                    } else {
                        Ext.ComponentQuery.query("brandgrid")[0].store.reload();
                        component.up("#seriesaddwindow").close();
                    }
                },
                failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("请求失败", "服务器繁忙, 稍后重试!");
                }
            });
        }
    }]
});
