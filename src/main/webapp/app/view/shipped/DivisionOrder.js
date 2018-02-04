/**
 * 分割订单
 */
Ext.define("MIS.view.shipped.DivisionOrder", {
	extend: "Ext.form.Panel",
	alias: "widget.divisionorder",
	
//	width: 600,
//	height: 185,
//	closable: true,
	closeAction: "destory",
	showFlag: "",
    defaultType: "textfield",
    defaults: {
        anchor: "100%",
        padding: "15 15"
    },
    layout: {
    	type: 'table',
    	columns:2
    },
	
	
	items: [{
		fieldLabel: "下单数量",
        name: "checkNum",
        xtype: "textfield",
        colspan: 1,
        anchor: "55%",
        disabled: true
    }, {
		fieldLabel: "已入库数量",
        name: "storeNum",
        xtype: "textfield",
        colspan: 1,
        anchor: "55%",
        disabled: true
    }, {
		fieldLabel: "分割数量上限",
        name: "maxDivisionNum",
        xtype: "textfield",
        colspan: 1,
        anchor: "55%",
        readOnly: true
    }, {
		fieldLabel: "分割数量",
        name: "divisionNum",
        xtype: "numberfield",
        colspan: 1,
        anchor: "55%",
        minValue: 0,
        editable: true
    }, {
        name: "shippedId",
        hidden: true
    }],
	
	buttons: [{
		text: "取消",
		handler: function(component){
			component.up("#divisionorderwindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var form = component.up("divisionorder");
        	var params = form.getForm().getValues();
        	
        	if(params.divisionNum == 0){
        		Ext.MessageBox.alert("分割订单完成", "需要分割的订单数量为0，不需要分割。");
        		return;
        	}

        	Ext.Ajax.request({
				url: "/shipped/divisionOrder",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("分割订单失败", "原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("shippedsgrid")[0].store.reload();
		                Ext.ComponentQuery.query("shippedsgrid")[0].getView().getSelectionModel().deselectAll();
		                Ext.ComponentQuery.query("shippedgrid")[0].store.reload();
		                component.up("#divisionorderwindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});