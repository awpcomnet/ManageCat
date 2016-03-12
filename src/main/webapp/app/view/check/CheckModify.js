/**
 * 下单清单修改
 */
Ext.define("MIS.view.check.CheckModify", {
	extend: "Ext.form.Panel",
	alias: "widget.checkmodify",
	
//	width: 600,
//	height: 185,
//	closable: true,
	closeAction: "destory",
	showFlag: "",
    defaultType: "textfield",
    defaults: {
        anchor: "100%",
        padding: "10 10"
    },
    layout: {
    	type: 'table',
    	columns:2
    },
	
	
	items: [{
		fieldLabel: "批次号",
        name: "batchNo",
        xtype: "textfield",
        colspan: 2,
        width: 530,
        anchor: "55%"
    }, {
		fieldLabel: "快递单号",
        name: "trackingNumber",
        xtype: "textfield",
        colspan: 1,
        anchor: "55%"
    }, {
		xtype: 'datefield',
		fieldLabel: "下单时间",
		name: "orderTime",
		anchor: "55%",
        format: 'Ymd',
        editable:false
	}, {
		fieldLabel: "转运公司",
        name: "transferCompany",
        xtype: "combobox",
        store: Ext.create("MIS.store.dict.DictQueryStore", {
        	dictcode: "transferCompany"
        }),
        mode: "local",
        displayField: 'name',
        valueField: "value",
        allowBlank: true,
        editable:false
	}, {
		fieldLabel: "下单网站",
        name: "orderAddr",
        xtype: "combobox",
        store: Ext.create("MIS.store.dict.DictQueryStore", {
        	dictcode: "orderAddr"
        }),
        mode: "local",
        displayField: 'name',
        valueField: "value",
        allowBlank: true,
        editable:false
	}, {
		fieldLabel: "品牌名称",
        name: "brandId",
        xtype: "combobox",
        store: Ext.create("MIS.store.brand.BrandStore"),
        listeners: {
            select: function (combobox, record) {
                record = parseInt(combobox.getValue());
            	var seriesId = combobox.up("form").down("combobox[name='seriesId']");
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
		fieldLabel: "系列名称",
        name: "seriesId",
        xtype: "combobox",
        store: Ext.create("MIS.store.series.SeriesStore"),
        listeners: {
            select: function (combobox, record) {
                record = parseInt(combobox.getValue());
            	var singleId = combobox.up("form").down("combobox[name='singleId']");
            	singleId.setValue("");
            	var singleIdStore = singleId.getStore();
            	singleIdStore.proxy.extraParams.ofOrigin = record;
            	singleIdStore.proxy.extraParams.isUse = 1;
            	singleIdStore.reload();
            }
        },
        mode: "local",
        displayField: 'seriesName',
        valueField: "seriesId",
        allowBlank: true,
        editable:false
    }, {
		fieldLabel: "单品名称",
        name: "singleId",
        xtype: "combobox",
        store: Ext.create("MIS.store.singleproduct.SingleproductStore"),
        mode: "local",
        displayField: 'singleName',
        valueField: "singleId",
        allowBlank: true,
        editable:false
    }, {
		fieldLabel: "数量",
        name: "num",
        xtype: "numberfield",
        minValue: 1, 
        allowBlank: true,
        editable:true
    }, {
		fieldLabel: "下单单价",
	    name: "unitPrice",
//	    listeners : {
//	       change : function(field,newValue,oldValue){
//	    	   var transferPrice = Ext.ComponentQuery.query("suborderadd textfield[name=transferPrice]")[0].getValue();
//	    	   var costPrice = Ext.ComponentQuery.query("suborderadd textfield[name=costPrice]")[0];
//	    	   if(newValue == '')
//	    		   newValue = 0;
//	    	   if(transferPrice == '')
//	    		   transferPrice = 0;
//	    	   
//	    	   costPrice.setValue(Number.parseFloat(transferPrice)+Number.parseFloat(newValue));
//	       }
//		},
	    allowBlank: true,
	    editable:true
    }, {
		fieldLabel: "付款人",
        name: "payby",
        xtype: "combobox",
        store: Ext.create("MIS.store.user.UserStore"),
        mode: "local",
        displayField: 'realname',
        valueField: "realname",
        allowBlank: true,
        editable:false
    }, {
		fieldLabel: "备注",
        name: "remark",
        xtype: "textarea",
        colspan: 2,
        width: 530
    }, {
        name: "id",
        hidden: true
    }, {
        name: "orderStatus",
        hidden: true
    }],
	
	buttons: [{
		text: "取消",
		handler: function(component){
			component.up("#checkmodifywindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var form = component.up("checkmodify");
        	var params = form.getForm().getValues();
			
			Ext.Ajax.request({
				url: "/check/modify",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("修改下单清单记录失败, 原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("checkgrid")[0].store.reload();
		                Ext.ComponentQuery.query("checkgrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#checkmodifywindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});