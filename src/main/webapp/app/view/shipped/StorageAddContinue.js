/**
 * 入库清单添加(后续入库)
 */
Ext.define("MIS.view.shipped.StorageAddContinue", {
	extend: "Ext.form.Panel",
	alias: "widget.storageaddcontinue",
	
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
		fieldLabel: "数量",
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
		fieldLabel: "入库数量",
        name: "num",
        xtype: "numberfield",
        colspan: 1,
        anchor: "55%",
        minValue: 1,
        editable: true
    }, {
        name: "shippedId",
        hidden: true
    }],
	
	buttons: [{
		text: "取消",
		handler: function(component){
			component.up("#shippedstorecontinuewindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var form = component.up("storageaddcontinue");
        	var params = form.getForm().getValues();
        	
			Ext.Ajax.request({
				url: "/store/add",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("添加入库清单失败", "原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("shippedsgrid")[0].store.reload();
		                Ext.ComponentQuery.query("shippedsgrid")[0].getView().getSelectionModel().deselectAll();
		                Ext.ComponentQuery.query("shippedgrid")[0].store.reload();
		                component.up("#shippedstorecontinuewindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});