/**
 * 邮寄清单子单修改
 */
Ext.define("MIS.view.shipped.ShippedsModify", {
	extend: "Ext.form.Panel",
	alias: "widget.shippedsmodify",
	
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
		fieldLabel: "快递单号",
        name: "trackingNumber",
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
		fieldLabel: "数量",
        name: "num",
        xtype: "textfield",
        colspan: 1,
        anchor: "55%",
        readOnly: true
    }, {
		xtype: 'numberfield',
		fieldLabel: "汇率(非必填)",
		name: "rate",
		anchor: "55%",
		listeners : {
	       change : function(field,newValue,oldValue){
	    	   var unitPrice = Ext.ComponentQuery.query("shippedsmodify textfield[name=unitPrice]")[0].getValue();
	    	   var planRmb = Ext.ComponentQuery.query("shippedsmodify numberfield[name=planRmb]")[0];
	    	   var planPostage = Ext.ComponentQuery.query("shippedsmodify numberfield[name=planPostage]")[0].getValue();
	    	   var planCost = Ext.ComponentQuery.query("shippedsmodify numberfield[name=planCost]")[0];
	    	   if(newValue == '' || newValue == null)
	    		   newValue = 0;
	    	   if(unitPrice == '' || unitPrice == null)
	    		   unitPrice = 0;
	    	   if(planPostage == '' || planPostage == null)
	    		   planPostage = 0;
	    	   
	    	   planRmb.setValue(Number.parseFloat(unitPrice)*Number.parseFloat(newValue));
	    	   planCost.setValue(Number.parseFloat(unitPrice)*Number.parseFloat(newValue)+Number.parseFloat(planPostage));
	       }
		},
        editable:true
	}, {
		fieldLabel: "预计单价(￥)",
        name: "planRmb",
        xtype: "numberfield",
        allowBlank: true,
        decimalPrecision: 2,
        listeners : {
 	       change : function(field,newValue,oldValue){
 	    	   var planPostage = Ext.ComponentQuery.query("shippedsmodify numberfield[name=planPostage]")[0].getValue();
 	    	   var planCost = Ext.ComponentQuery.query("shippedsmodify numberfield[name=planCost]")[0];
 	    	   if(newValue == '' || newValue == null)
 	    		   newValue = 0;
 	    	   if(planPostage == '' || planPostage == null)
 	    		   planPostage = 0;
 	    	   
 	    	   planCost.setValue(Number.parseFloat(newValue)+Number.parseFloat(planPostage));
 	       }
 		},
        editable:true
	}, {
		fieldLabel: "预计邮费(￥)",
        name: "planPostage",
        xtype: "numberfield",
        allowBlank: true,
        decimalPrecision: 2,
        listeners : {
  	       change : function(field,newValue,oldValue){
  	    	   var planRmb = Ext.ComponentQuery.query("shippedsmodify numberfield[name=planRmb]")[0].getValue();
  	    	   var planCost = Ext.ComponentQuery.query("shippedsmodify numberfield[name=planCost]")[0];
  	    	   if(newValue == '' || newValue == null)
  	    		   newValue = 0;
  	    	   if(planRmb == '' || planRmb == null)
  	    		   planRmb = 0;
  	    	   
  	    	   planCost.setValue(Number.parseFloat(newValue)+Number.parseFloat(planRmb));
  	       }
  		},
        editable:true
	}, {
		fieldLabel: "预计成本(￥)",
        name: "planCost",
        xtype: "numberfield",
        decimalPrecision: 2,
        allowBlank: true,
        colspan: 2,
        editable:true
	}, {
		fieldLabel: "备注",
        name: "remark",
        xtype: "textarea",
        colspan: 2,
        width: 530,
        editable:true
    }, {
        name: "id",
        hidden: true
    }],
	
	buttons: [{
		text: "取消",
		handler: function(component){
			component.up("#shippedsmodifywindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var form = component.up("shippedsmodify");
        	var params = form.getForm().getValues();
			
			Ext.Ajax.request({
				url: "/shipped/modify",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("修改邮寄清单记录失败", "原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("shippedsgrid")[0].store.reload();
		                Ext.ComponentQuery.query("shippedsgrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#shippedsmodifywindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});