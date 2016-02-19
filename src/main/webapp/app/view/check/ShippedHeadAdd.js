/**
 * 邮寄清单添加
 */
Ext.define("MIS.view.check.ShippedHeadAdd", {
	extend: "Ext.form.Panel",
	alias: "widget.shippedheadadd",
	
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
		fieldLabel: "快递单号",
        name: "trackingNumber",
        xtype: "textfield",
        colspan: 1,
        anchor: "55%"
    }, {
		xtype: 'datefield',
		fieldLabel: "提交邮寄时间",
		name: "submitTime",
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
		fieldLabel: "邮费(总价)",
	    name: "postage",
	    allowBlank: true,
	    editable:true
    }, {
        name: "checkIds",
        hidden: true
    }],
	
	buttons: [{
		text: "取消",
		handler: function(component){
			component.up("#shippedheadaddwindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var form = component.up("shippedheadadd");
        	var params = form.getForm().getValues();
			console.log(params);
        	
			Ext.Ajax.request({
				url: "/shippedHead/add",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("添加邮寄清单失败, 原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("checkgrid")[0].store.reload();
		                Ext.ComponentQuery.query("checkgrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#shippedheadaddwindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});