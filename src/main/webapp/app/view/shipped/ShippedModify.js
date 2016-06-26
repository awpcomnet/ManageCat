/**
 * 邮寄清单修改
 */
Ext.define("MIS.view.shipped.ShippedModify", {
	extend: "Ext.form.Panel",
	alias: "widget.shippedmodify",
	
//	width: 600,
//	height: 185,
//	closable: true,
	closeAction: "destory",
	showFlag: "",
    defaultType: "textfield",
    defaults: {
        anchor: "100%",
        padding: "14 13"
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
        anchor: "55%",
        editable:true
        //readOnly: true
    }, {
		xtype: 'datefield',
		fieldLabel: "提交时间",
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
		fieldLabel: "邮费总价（￥）",
	    name: "postage",
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
        name: "id",
        hidden: true
    }],
	
	buttons: [{
		text: "取消",
		handler: function(component){
			component.up("#shippedmodifywindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var form = component.up("shippedmodify");
        	var params = form.getForm().getValues();
			
			Ext.Ajax.request({
				url: "/shippedHead/modify",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("修改邮寄清单记录失败, 原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("shippedgrid")[0].store.reload();
		                Ext.ComponentQuery.query("shippedgrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#shippedmodifywindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});