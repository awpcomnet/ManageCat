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
        editable:true
	}, {
		fieldLabel: "日期模式",
		name: "dateMode",
	    xtype:'radiogroup',  
        colspan: 1,
        items:[
        	{ boxLabel: '到期时间', name: 'rb', inputValue: '1', checked:true},   
            { boxLabel: '生产日期', name: 'rb', inputValue: '2' } 
        ],
        listeners : {
 	       change : function(field,newValue,oldValue){
 	    	   var nv = newValue.rb;
 	    	   var ov = oldValue.rb;
 	    	   var periodOfValidity = Ext.ComponentQuery.query("shippedsmodify datefield[name=periodOfValidity]")[0];
 	    	   var dateOfManufacture = Ext.ComponentQuery.query("shippedsmodify datefield[name=dateOfManufacture]")[0];
 	    	   var qualityGuaranteePeriod = Ext.ComponentQuery.query("shippedsmodify combobox[name=qualityGuaranteePeriod]")[0];
 	    	   var dateMode = Ext.ComponentQuery.query("shippedsmodify radiogroup[name=dateMode]")[0];
 	    	   if(nv == '1'){
 	    		  dateMode.colspan = 1;
 	    		  periodOfValidity.show();
 	    		  dateOfManufacture.hide();
 	    		  qualityGuaranteePeriod.hide();
 	    		  //dateOfManufacture.reset();
 	    		  //qualityGuaranteePeriod.reset();
 	    	   }else{
 	    		  dateMode.colspan = 2;
 	    		  periodOfValidity.hide();
 	    		  dateOfManufacture.show();
 	    		  qualityGuaranteePeriod.show();
 	    		  //periodOfValidity.reset();
 	    	   }
 	       }
 		},
        allowBlank: true,
        editable:false
    }, {
		fieldLabel: "到期日期",
        name: "periodOfValidity",
        xtype: "datefield",
        format: 'Ymd',
        allowBlank: true,
        colspan: 1,
        hidden:true,
        editable:false
	}, {
		fieldLabel: "生产日期",
        name: "dateOfManufacture",
        xtype: "datefield",
        format: 'Ymd',
        allowBlank: false,
        colspan: 1,
        hidden:true,
        editable:false
	}, {
		fieldLabel: "有效期",
        name: "qualityGuaranteePeriod",
        xtype: "combobox",
        store: Ext.create("MIS.store.dict.DictQueryStore", {
        	dictcode: "qualityGuaranteePeriod"
        }),
        mode: "local",
        displayField: 'name',
        valueField: "value",
        allowBlank: true,
        hidden:true,
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
    }, {
        name: "checkId",
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
        	if((params.dateOfManufacture != '' && params.qualityGuaranteePeriod == '') || (params.dateOfManufacture == '' && params.qualityGuaranteePeriod != '')){
        		Ext.MessageBox.alert("修改邮寄清单记录失败", "原因:生产日期和保质期必须同时不为空或同时为空");
        		return;
        	}
        	if(params.qualityGuaranteePeriod < 0){
        		Ext.MessageBox.alert("修改邮寄清单记录失败", "原因:有效期不能为负值");
        		return;
        	}
        	if(params.rb == 2){
        		params.periodOfValidity = '';
        		params.qualityGuaranteePeriod = params.qualityGuaranteePeriod == '' ? '-1' : params.qualityGuaranteePeriod;
        	} else {
        		params.dateOfManufacture = '';
        		params.qualityGuaranteePeriod = '-1';
        	}
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