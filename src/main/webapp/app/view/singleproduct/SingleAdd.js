/**
 * 描述: 增加单品界面
 * 作者: 王航
 */
Ext.define("MIS.view.singleproduct.SingleAdd", {
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
		fieldLabel: "所属品牌(非必选)",
        name: "brandId",
        xtype: "combobox",
        store: Ext.create("MIS.store.brand.BrandStore"),
        listeners: {
            select: function (combobox, record) {
                record = parseInt(combobox.getValue());
            	var seriesId = combobox.up("form").down("combobox[name=ofOrigin]");
            	seriesId.setValue("");
            	var seriesIdStore = seriesId.getStore();
            	seriesIdStore.proxy.extraParams.ofOrigin = record;
            	seriesIdStore.proxy.extraParams.isUse = 1;
            	seriesIdStore.reload();
            }
        },
        mode: "local",
        displayField: 'brandName',
        valueField: "brandId",
        allowBlank: true,
        editable:false
	}, {
		fieldLabel: "所属系列",
        name: "ofOrigin",
        xtype: "combobox",
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
        xtype: "combobox",
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
        xtype: "combobox",
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
                        Ext.ComponentQuery.query("singlegrid")[0].store.reload();
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
