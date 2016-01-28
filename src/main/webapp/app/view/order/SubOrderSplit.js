/**
 * 子订单拆分
 */
Ext.define("MIS.view.order.SubOrderSplit", {
	extend: "Ext.form.Panel",
	alias: "widget.subordersplit",
	
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
		fieldLabel: "主订单编号",
	    name: "superOrderId",
	    readOnly: true,
	    allowBlank: true
    }, {
		fieldLabel: "子订单编号",
	    name: "suborderId",
	    readOnly:true,
	    allowBlank: true
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
        readOnly:true,
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
        readOnly:true,
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
        readOnly:true,
        editable:false
    }, {
		fieldLabel: "原订单剩余数量",
        name: "num",
        xtype: "numberfield",
        minValue: 1, 
        allowBlank: true,
        editable:true
    }],
	
	buttons: [{
		text: "取消",
		handler: function(component){
			component.up("#subordersplitwindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var subOrderSplit = component.up("subordersplit");
			var extraData = component.up("subordersplit").extraData;
			
			var suborderId = subOrderSplit.down("textfield[name=suborderId]").getValue().trim(),
				num = subOrderSplit.down("numberfield[name=num]").getValue();
			
			
			
			var params = {
					subOrderId: suborderId,
					num: num
	        };
			
			Ext.Ajax.request({
				url: "/subOrder/split",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("拆分子订单失败, 原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("subordergrid")[0].store.reload();
		                Ext.ComponentQuery.query("subordergrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#subordersplitwindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});