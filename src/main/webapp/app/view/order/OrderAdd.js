/**
 * 主订单添加
 */
Ext.define("MIS.view.order.OrderAdd", {
	extend: "Ext.form.Panel",
	alias: "widget.orderadd",
	
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
		fieldLabel: "付款人",
        name: "payby",
        xtype: "combobox",
        store: Ext.create("MIS.store.user.UserStore"),
        mode: "local",
        displayField: 'realname',
        valueField: "realname",
        allowBlank: true,
        editable:false
    }],
	
	buttons: [{
		text: "取消",
		handler: function(component){
			component.up("#orderaddwindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var subOrderAdd = component.up("orderadd");
			
			var payby = subOrderAdd.down("combobox[name=payby]").getValue().trim();
			
			
			
			var params = {
					payby: payby,
					foreignState: 0,//国外订单状态
	            	transfer: 0,//转运状态
	            	affirmState: 0//确认收货状态
					
	        };
			
			Ext.Ajax.request({
				url: "/order/add",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("添加主订单失败, 原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("ordergrid")[0].store.reload();
		                Ext.ComponentQuery.query("ordergrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#orderaddwindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});