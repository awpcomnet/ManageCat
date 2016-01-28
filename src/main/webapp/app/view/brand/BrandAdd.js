/**
 * 描述: 增加品牌界面
 * 作者: 王航
 */
Ext.define("MIS.view.brand.BrandAdd", {
    extend: "Ext.form.Panel",
    alias: "widget.brandadd",

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
		fieldLabel: "品牌名称",
	    name: "brandName",
	    allowBlank: true
    }, {
		fieldLabel: "品牌英文名",
	    name: "brandEname",
	    allowBlank: true
    }, {
		fieldLabel: "所属国家",
        name: "ofOrigin",
        xtype: "combo",
        store: Ext.create("MIS.store.dict.DictQueryStore", {
        	dictcode: "country"
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
            component.up("#brandaddwindow").close();
        }
    }, {
        text: "新增",
        handler: function (component) {
        	var form = component.up("brandadd");
        	var params = form.getForm().getValues();
            Ext.Ajax.request({
                url: "/brand/add",
                params: params,
                success: function (conn, request, option, eOpts) {
                    var result = Ext.JSON.decode(conn.responseText, true);
                    if (result.resultCode != 0) {
                        Ext.MessageBox.alert("请求失败", "添加品牌失败 " + result.resultMessage);
                    } else {
                        Ext.ComponentQuery.query("brandgrid")[0].store.reload();
                        component.up("#brandaddwindow").close();
                    }
                },
                failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("请求失败", "服务器繁忙, 稍后重试!");
                }
            });
        }
    }]
});
