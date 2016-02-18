/**
 * 售出/召回
 */
Ext.define("MIS.view.order.SellAndBack", {
	extend: "Ext.form.Panel",
	alias: "widget.sellandback",
	
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
		fieldLabel: "子订单编号",
	    name: "suborderId",
	    readOnly:true,
	    allowBlank: true
    }, {
		fieldLabel: "数量",
        name: "num",
        xtype: "numberfield",
        minValue: 1, 
        allowBlank: true,
        editable:true
    }, {
		fieldLabel: "类型",
        name: "flag",
        xtype: "combobox",
        store: Ext.create("MIS.store.dict.DictQueryStore", {
        	dictcode: "sellandback"
        }),
        mode: "local",
        displayField: 'name',
        valueField: "value",
        allowBlank: true,
        editable:false
	}, {
		fieldLabel: "售价(单个)",
	    name: "sellingPrice",
	    xtype: "numberfield",
	    minValue: 0.01, 
	    allowBlank: true,
	    editable:true
    }, {
		fieldLabel: "备注",
        name: "remark",
        xtype: "textarea",
        colspan: 2,
        width: 530
    }],
	
	buttons: [{
		text: "取消",
		handler: function(component){
			component.up("#sellandbackwindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var subOrderModify = component.up("sellandback");
			var extraData = component.up("sellandback").extraData;
			
			var subOrderId = subOrderModify.down("textfield[name=suborderId]").getValue().trim(),
				num = subOrderModify.down("numberfield[name=num]").getValue(),
				flag = subOrderModify.down("combobox[name=flag]").getValue(),
				remark = subOrderModify.down("textarea[name=remark]").getValue(),
				sellingPrice = subOrderModify.down("numberfield[name=sellingPrice]").getValue();
				
			
			
			
			var params = {
					subOrderId: subOrderId,
					num: num,
					flag: flag,
					remark: remark,
					sellingPrice:sellingPrice
					
	        };
			
			Ext.Ajax.request({
				url: "/subOrder/sellandback",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("售出/召回子订单失败, 原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("subordergrid")[0].store.reload();
		                Ext.ComponentQuery.query("subordergrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#sellandbackwindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});