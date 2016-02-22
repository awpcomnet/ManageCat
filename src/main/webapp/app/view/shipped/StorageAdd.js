/**
 * 入库清单添加（单条）
 */
Ext.define("MIS.view.shipped.StorageAdd", {
	extend: "Ext.form.Panel",
	alias: "widget.storageadd",
	
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
        name: "num",
        xtype: "textfield",
        colspan: 1,
        anchor: "55%",
        readOnly: true
    }, {
		fieldLabel: "下单单价($)",
        name: "unitPrice",
        xtype: "textfield",
        colspan: 1,
        anchor: "55%",
        readOnly: true
    }, {
		xtype: 'datefield',
		fieldLabel: "入库时间",
		name: "storeTime",
		anchor: "55%",
        format: 'Ymd',
        value: new Date(),
        editable:false
	}, {
		fieldLabel: "汇率(非必填)",
        name: "rate",
        xtype: "numberfield",
        decimalPrecision: 2,
        listeners : {
 	       change : function(field,newValue,oldValue){
 	    	   var unitPrice = Ext.ComponentQuery.query("storageadd textfield[name=unitPrice]")[0].getValue();
 	    	   var unitRmb = Ext.ComponentQuery.query("storageadd numberfield[name=unitRmb]")[0];
 	    	   var unitPostage = Ext.ComponentQuery.query("storageadd numberfield[name=unitPostage]")[0].getValue();
 	    	   var unitCost = Ext.ComponentQuery.query("storageadd numberfield[name=unitCost]")[0];
 	    	   if(newValue == '' || newValue == null)
 	    		   newValue = 0;
 	    	   if(unitPrice == '' || unitPrice == null)
 	    		   unitPrice = 0;
 	    	   if(unitPostage == '' || unitPostage == null)
 	    		   unitPostage = 0;
 	    	   
 	    	   unitRmb.setValue(Number.parseFloat(unitPrice)*Number.parseFloat(newValue));
 	    	   unitCost.setValue(Number.parseFloat(unitPrice)*Number.parseFloat(newValue)+Number.parseFloat(unitPostage));
 	       }
 		},
        editable:true
	}, {
		fieldLabel: "单个重量(kg)",
        name: "weight",
        xtype: "numberfield",
        allowBlank: true,
        decimalPrecision: 2,
        editable:true
	}, {
		fieldLabel: "实际单价(￥)",
        name: "unitRmb",
        xtype: "numberfield",
        allowBlank: true,
        decimalPrecision: 2,
        listeners : {
  	       change : function(field,newValue,oldValue){
  	    	   var unitPostage = Ext.ComponentQuery.query("storageadd numberfield[name=unitPostage]")[0].getValue();
  	    	   var unitCost = Ext.ComponentQuery.query("storageadd numberfield[name=unitCost]")[0];
  	    	   if(newValue == '' || newValue == null)
  	    		   newValue = 0;
  	    	   if(unitPostage == '' || unitPostage == null)
  	    		   unitPostage = 0;
  	    	   
  	    	   unitCost.setValue(Number.parseFloat(newValue)+Number.parseFloat(unitPostage));
  	       }
  		},
        editable:true
	}, {
		fieldLabel: "实际单个邮费(￥)",
        name: "unitPostage",
        xtype: "numberfield",
        allowBlank: true,
        decimalPrecision: 2,
        listeners : {
   	       change : function(field,newValue,oldValue){
   	    	   var unitRmb = Ext.ComponentQuery.query("storageadd numberfield[name=unitRmb]")[0].getValue();
   	    	   var unitCost = Ext.ComponentQuery.query("storageadd numberfield[name=unitCost]")[0];
   	    	   if(newValue == '' || newValue == null)
   	    		   newValue = 0;
   	    	   if(unitRmb == '' || unitRmb == null)
   	    		   unitRmb = 0;
   	    	   
   	    	   unitCost.setValue(Number.parseFloat(newValue)+Number.parseFloat(unitRmb));
   	       }
   		},
        editable:true
	}, {
		fieldLabel: "实际成本(￥)",
        name: "unitCost",
        xtype: "numberfield",
        allowBlank: true,
        decimalPrecision: 2,
        editable:true
	}, {
		fieldLabel: "备注",
	    name: "remark",
	    xtype: "textarea",
	    colspan: 2,
        width: 540,
	    editable:true
    }, {
        name: "shippedId",
        hidden: true
    }],
	
	buttons: [{
		text: "计算邮费",
		handler: function(component){
//			var shippedheadadd = component.up("shippedheadadd");
//			
//			var trackingNumber = shippedheadadd.down("textfield[name=trackingNumber]").getValue().trim();
//			
//			var params = {
//				trackingNumber:trackingNumber
//			}
//			
//			Ext.Ajax.request({
//				url: "/shippedHead/queryAll",
//				params: params,
//				success: function(conn, request, option, eOpts){
//					var result = Ext.JSON.decode(conn.responseText, true);
//					if(result.resultCode != 0){
//						Ext.MessageBox.alert("校验快递单号失败, 原因:" + result.resultMessage);
//					} else {
//						var results = result.results;
//						if(results.length != 0){
//							shippedheadadd.down("datefield[name=submitTime]").setValue(results[0].submitTime);
//							shippedheadadd.down("combobox[name=transferCompany]").setValue(results[0].transferCompany);
//							shippedheadadd.down("textfield[name=postage]").setValue(results[0].postage);
//							
//							shippedheadadd.down("textfield[name=trackingNumber]").setReadOnly(true);
//							shippedheadadd.down("datefield[name=submitTime]").setReadOnly(true);
//							shippedheadadd.down("combobox[name=transferCompany]").setReadOnly(true);
//							shippedheadadd.down("textfield[name=postage]").setReadOnly(true);
//							
//							Ext.MessageBox.alert("友情提示", "该快递单号已经存在邮寄清单中，记录将记录在已存在邮寄清单中");
//						}else{
//							Ext.MessageBox.alert("友情提示", "该快递单号不存在邮寄清单中，提交将建立新的邮寄清单");
//						}
//					}
//				},
//				failure: function (conn, request, option, eOpts) {
//                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
//                }
//				
//			});
		}
	}, {
		text: "取消",
		handler: function(component){
			component.up("#shippedstorewindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var form = component.up("storageadd");
        	var params = form.getForm().getValues();
			console.log(params);
        	
			Ext.Ajax.request({
				url: "/shippedHead/add",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("添加入库清单失败", "原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("shippedsgrid")[0].store.reload();
		                Ext.ComponentQuery.query("shippedsgrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#shippedstorewindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});