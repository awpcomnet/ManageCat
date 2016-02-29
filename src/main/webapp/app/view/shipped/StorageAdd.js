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
        name: "checkNum",
        xtype: "textfield",
        colspan: 1,
        anchor: "55%",
        disabled: true
    }, {
		fieldLabel: "下单单价($)",
        name: "unitPrice",
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
		fieldLabel: "单个重量(磅)",
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
        colspan: 2,
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
			var storageadd = component.up("storageadd");
			
			var shippedId = storageadd.down("textfield[name=shippedId]").getValue().trim();
			
			var params = {
				shippedId: shippedId
			}
			
			Ext.Ajax.request({
				url: "/store/calculate",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("计算邮费失败", "原因:" + result.resultMessage);
					} else {
						var calculatePost = result.results[0].calculatePost;
						
						var unitRmb = Ext.ComponentQuery.query("storageadd numberfield[name=unitRmb]")[0].getValue();
			   	    	var unitCost = Ext.ComponentQuery.query("storageadd numberfield[name=unitCost]")[0];
		   	    	    if(calculatePost == '' || calculatePost == null)
		   	    	    	calculatePost = 0;
		   	    	    if(unitRmb == '' || unitRmb == null)
		   	    		    unitRmb = 0;
		   	    	   
		   	    	    unitCost.setValue(Number.parseFloat(calculatePost)+Number.parseFloat(unitRmb));
		   	    	    
		   	    	    var unitPostage = Ext.ComponentQuery.query("storageadd numberfield[name=unitPostage]")[0];
		   	    	    unitPostage.setValue(calculatePost);
		   	    	    Ext.MessageBox.alert("温馨提示", "计算所得邮费仅供参考");
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
			component.up("#shippedstorewindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var form = component.up("storageadd");
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