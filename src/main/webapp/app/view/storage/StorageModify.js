/**
 * 仓库清单单条修改
 */
Ext.define("MIS.view.storage.StorageModify", {
	extend: "Ext.form.Panel",
	alias: "widget.storagemodify",
	
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
	    	   var unitPrice = Ext.ComponentQuery.query("storagemodify textfield[name=unitPrice]")[0].getValue();
	    	   var unitRmb = Ext.ComponentQuery.query("storagemodify numberfield[name=unitRmb]")[0];
	    	   var unitPostage = Ext.ComponentQuery.query("storagemodify numberfield[name=unitPostage]")[0].getValue();
	    	   var unitCost = Ext.ComponentQuery.query("storagemodify numberfield[name=unitCost]")[0];
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
		fieldLabel: "实际单价(￥)",
        name: "unitRmb",
        xtype: "numberfield",
        allowBlank: true,
        decimalPrecision: 2,
        listeners : {
 	       change : function(field,newValue,oldValue){
 	    	   var unitPostage = Ext.ComponentQuery.query("storagemodify numberfield[name=unitPostage]")[0].getValue();
 	    	   var unitCost = Ext.ComponentQuery.query("storagemodify numberfield[name=unitCost]")[0];
 	    	   if(newValue == '' || newValue == null)
 	    		   newValue = 0;
 	    	   if(unitPostage == '' || unitPostage == null)
 	    		   unitPostage = 0;
 	    	   
 	    	   unitCost.setValue(Number.parseFloat(newValue)+Number.parseFloat(unitPostage));
 	       }
 		},
        editable:true
	}, {
		fieldLabel: "实际邮费(￥)",
        name: "unitPostage",
        xtype: "numberfield",
        allowBlank: true,
        decimalPrecision: 2,
        listeners : {
  	       change : function(field,newValue,oldValue){
  	    	   var unitRmb = Ext.ComponentQuery.query("storagemodify numberfield[name=unitRmb]")[0].getValue();
  	    	   var unitCost = Ext.ComponentQuery.query("storagemodify numberfield[name=unitCost]")[0];
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
			component.up("#storagemodifywindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var form = component.up("storagemodify");
        	var params = form.getForm().getValues();
			
			Ext.Ajax.request({
				url: "/store/modify",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("修改仓库记录失败", "原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("storagegrid")[0].store.reload();
		                Ext.ComponentQuery.query("storagegrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#storagemodifywindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});