/**
 * 主订单修改
 */
Ext.define("MIS.view.order.OrderModify", {
	extend: "Ext.form.Panel",
	alias: "widget.ordermodify",
	
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
	    name: "orderId",
	    disabled:true,
	    allowBlank: true
    }, {
		fieldLabel: "国外订单状态",
        name: "foreignState",
        xtype: "combobox",
        store: Ext.create("MIS.store.dict.DictQueryStore", {
        	dictcode: "foreignState"
        }),
        mode: "local",
        displayField: 'name',
        valueField: "value",
        allowBlank: true,
        editable:false
	}, {
		fieldLabel: "转运状态",
        name: "transfer",
        xtype: "combobox",
        store: Ext.create("MIS.store.dict.DictQueryStore", {
        	dictcode: "transfer"
        }),
        mode: "local",
        displayField: 'name',
        valueField: "value",
        allowBlank: true,
        editable:false
	}, {
		fieldLabel: "确认收货状态",
        name: "affirmState",
        xtype: "combobox",
        store: Ext.create("MIS.store.dict.DictQueryStore", {
        	dictcode: "affirmState"
        }),
        mode: "local",
        displayField: 'name',
        valueField: "value",
        allowBlank: true,
        editable:false
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
			component.up("#ordermodifywindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var orderModify = component.up("ordermodify");
			var extraData = component.up("ordermodify").extraData;
			
			var orderId = orderModify.down("textfield[name=orderId]").getValue().trim(),
				foreignState = orderModify.down("combobox[name=foreignState]").getValue(),
				transfer = orderModify.down("combobox[name=transfer]").getValue(),
				affirmState = orderModify.down("combobox[name=affirmState]").getValue(),
			    remark = orderModify.down("textarea[name=remark]").getValue();
			    
			
			var params = {
				orderId: orderId,
		        foreignState: foreignState,
		        transfer: transfer,
		        affirmState: affirmState,
		        remark: remark
	        };
			
			Ext.Ajax.request({
				url: "/order/modify",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("请求主订单失败, 稍后重试!");
					} else {
		                Ext.ComponentQuery.query("ordergrid")[0].store.reload();
		                Ext.ComponentQuery.query("ordergrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#ordermodifywindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});