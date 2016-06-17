/**
 * 描述: 修改单品界面
 * 作者: 王航
 */
Ext.define("MIS.view.singleproduct.SingleModify", {
    extend: "Ext.form.Panel",
    alias: "widget.singlemodify",

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
        xtype: "combo",
        store: Ext.create("MIS.store.brand.BrandAllStore"),
        listeners: {
            select: function (combobox, record) {
                record = parseInt(combobox.getValue());
            	var seriesId = combobox.up("form").down("combo[name=ofOrigin]");
            	seriesId.setValue("");
            	var seriesIdStore = seriesId.getStore();
            	seriesIdStore.proxy.extraParams.ofOrigin = record;
            	seriesIdStore.proxy.extraParams.isUse = 1;
            	seriesIdStore.reload();
            },
            
            change : function(field,newValue,oldValue){
                // 找到store
                var brandIdStore = Ext.ComponentQuery.query('singlemodify')[0].down("combobox[name=brandId]").getStore();

                //对store 进行过滤
                brandIdStore.filterBy(function(record){
                    var name = record.raw.brandName,
                        code = record.raw.brandId;
                    //如果输入框为空，直接放回所有记录
                    if(newValue == '' || newValue == null)
                        return true;

                    if(name.indexOf(newValue) >= 0){
                        return true;
                    }
                    return false;
                });
            }
        },
        mode: "local",
        displayField: 'brandName',
        valueField: "brandId",
        allowBlank: true,
        editable:true
	}, {
		fieldLabel: "所属系列",
        name: "ofOrigin",
        xtype: "combo",
        store: Ext.create("MIS.store.series.SeriesAllStore"),
        listeners: {
            change : function(field,newValue,oldValue){
                // 找到store
                var ofOriginStore = Ext.ComponentQuery.query('singlemodify')[0].down("combobox[name=ofOrigin]").getStore();

                //对store 进行过滤
                ofOriginStore.filterBy(function(record){
                    var name = record.raw.seriesName,
                        code = record.raw.seriesId;
                    //如果输入框为空，直接放回所有记录
                    if(newValue == '' || newValue == null)
                        return true;

                    if(name.indexOf(newValue) >= 0){
                        return true;
                    }
                    return false;
                });
            }
        },
        mode: "local",
        displayField: 'seriesName',
        valueField: "seriesId",
        allowBlank: true,
        editable:true
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
	}, {
	    name: "singleId",
	    readOnly: true,
	    hidden: true
    }],

    buttons: [{
        text: "取消",
        handler: function (component) {
            component.up("#singlemodifywindow").close();
        }
    }, {
        text: "确认",
        handler: function (component) {
        	var form = component.up("singlemodify");
        	var params = form.getForm().getValues();
            Ext.Ajax.request({
                url: "/singleproduct/update",
                params: params,
                success: function (conn, request, option, eOpts) {
                    var result = Ext.JSON.decode(conn.responseText, true);
                    if (result.resultCode != 0) {
                        Ext.MessageBox.alert("请求失败", "修改单品失败 " + result.resultMessage);
                    } else {
                        Ext.ComponentQuery.query("singlegrid")[0].store.reload();
                        component.up("#singlemodifywindow").close();
                    }
                },
                failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("请求失败", "服务器繁忙, 稍后重试!");
                }
            });
        }
    }]
});
