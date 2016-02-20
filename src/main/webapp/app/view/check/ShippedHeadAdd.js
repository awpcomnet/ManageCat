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
		text: "校验快递单号",
		handler: function(component){
			var shippedheadadd = component.up("shippedheadadd");
			
			var trackingNumber = shippedheadadd.down("textfield[name=trackingNumber]").getValue().trim();
			
			var params = {
				trackingNumber:trackingNumber
			}
			
			Ext.Ajax.request({
				url: "/shippedHead/queryAll",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("校验快递单号失败, 原因:" + result.resultMessage);
					} else {
						var results = result.results;
						if(results.length != 0){
							shippedheadadd.down("datefield[name=submitTime]").setValue(results[0].submitTime);
							shippedheadadd.down("combobox[name=transferCompany]").setValue(results[0].transferCompany);
							shippedheadadd.down("textfield[name=postage]").setValue(results[0].postage);
							
							shippedheadadd.down("textfield[name=trackingNumber]").setReadOnly(true);
							shippedheadadd.down("datefield[name=submitTime]").setReadOnly(true);
							shippedheadadd.down("combobox[name=transferCompany]").setReadOnly(true);
							shippedheadadd.down("textfield[name=postage]").setReadOnly(true);
							
							Ext.MessageBox.alert("友情提示", "该快递单号已经存在邮寄清单中，记录将记录在已存在邮寄清单中");
						}else{
							Ext.MessageBox.alert("友情提示", "该快递单号不存在邮寄清单中，提交将建立新的邮寄清单");
						}
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}, {
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