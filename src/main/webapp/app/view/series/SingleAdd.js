/**
 * 描述: 增加单品界面
 * 作者: 王航
 */
Ext.define("MIS.view.series.SingleAdd", {
    extend: "Ext.form.Panel",
    alias: "widget.singleadd",

    defaultType: "textfield",
    defaults: {
        anchor: "100%",
        padding: "15 10"
    },
    layout: {
    	type: 'table',
    	columns:2
    },

    items: [{
		fieldLabel: "所属系列",
        name: "ofOrigin",
        xtype: "combo",
        store: Ext.create("MIS.store.series.SeriesStore"),
        mode: "local",
        displayField: 'seriesName',
        valueField: "seriesId",
        allowBlank: true,
        editable:false
	}, {
		fieldLabel: "单品名称",
	    name: "singleName",
	    allowBlank: true
    }, {
		fieldLabel: "单品英文名",
	    name: "singleEname",
	    allowBlank: true
    }, {
		fieldLabel: "规格",
        name: "capacity",
        allowBlank: true
	}, {
		fieldLabel: "单位",
        name: "unit",
        xtype: "combo",
        store: Ext.create("MIS.store.dict.DictQueryStore", {
        	dictcode: "unitDict"
        }),
        mode: "local",
        displayField: 'name',
        valueField: "value",
        allowBlank: true,
        editable:false
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
	}],

    buttons: [{
        text: "取消",
        handler: function (component) {
            component.up("#singleaddwindow").close();
        }
    }, {
        text: "新增",
        handler: function (component) {
        	var form = component.up("singleadd");
        	var params = form.getForm().getValues();
            Ext.Ajax.request({
                url: "/singleproduct/add",
                params: params,
                success: function (conn, request, option, eOpts) {
                    var result = Ext.JSON.decode(conn.responseText, true);
                    if (result.resultCode != 0) {
                        Ext.MessageBox.alert("请求失败", "添加单品失败 " + result.resultMessage);
                    } else {
                        Ext.ComponentQuery.query("seriesgrid")[0].store.reload();
                        component.up("#singleaddwindow").close();
                    }
                },
                failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("请求失败", "服务器繁忙, 稍后重试!");
                }
            });
        }
    }]
});
