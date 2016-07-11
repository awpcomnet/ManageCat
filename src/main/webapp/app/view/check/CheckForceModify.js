/**
 * 下单清单修改
 */
Ext.define("MIS.view.check.CheckForceModify", {
	extend: "Ext.form.Panel",
	alias: "widget.checkforcemodify",
	
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
    	columns:3
    },
	
	
	items: [{
		fieldLabel: "批次号",
        name: "batchNo",
        xtype: "combobox",
        colspan: 1,
        //width: 530,
        anchor: "55%",
        store: Ext.create("MIS.store.batch.BatchAssignStore"),
        mode: "local",
        displayField: 'batchNo',
        valueField: "batchNo",
        allowBlank: true,
        editable:false
    }, {
		fieldLabel: "快递单号",
        name: "trackingNumber",
        xtype: "textfield",
        colspan: 1,
        anchor: "55%"
    }, {
		fieldLabel: "汇率",
		name: "rate",
        xtype: "numberfield",
        decimalPrecision: 8,
        colspan: 1,
        //width: 130,
        editable:true,
        anchor: "55%"
    }, {
		fieldLabel: "币种",
		name: "currency",
        xtype: "combobox",
        store: Ext.create("MIS.store.dict.DictQueryStore", {
        	dictcode: "currency"
        }),
        mode: "local",
        displayField: 'name',
        valueField: "value",
        allowBlank: true,
        editable:false
    }, {
		xtype: 'datefield',
		fieldLabel: "下单时间",
		name: "orderTime",
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
		fieldLabel: "下单网站",
        name: "orderAddr",
        xtype: "combobox",
        store: Ext.create("MIS.store.dict.DictQueryStore", {
        	dictcode: "orderAddr"
        }),
        mode: "local",
        displayField: 'name',
        valueField: "value",
        allowBlank: true,
        editable:false
	}, {
		fieldLabel: "品牌名称",
        name: "brandId",
        xtype: "combobox",
        store: Ext.create("MIS.store.brand.BrandAllStore"),
        listeners: {
            select: function (combobox, record) {
                record = parseInt(combobox.getValue());
            	var seriesId = combobox.up("form").down("combobox[name='seriesId']");
            	seriesId.setValue("");
            	var seriesIdStore = seriesId.getStore();
            	seriesIdStore.proxy.extraParams.ofOrigin = record;
            	seriesIdStore.proxy.extraParams.isUse = 1;
            	seriesIdStore.reload();
            },
            
            change : function(field,newValue,oldValue){
                // 找到store
                var brandIdStore = Ext.ComponentQuery.query('checkforcemodify')[0].down("combobox[name=brandId]").getStore();

                //对store 进行过滤
                brandIdStore.filterBy(function(record){
                    var name = record.raw.brandEname,
                        code = record.raw.brandId;
                    //如果输入框为空，直接放回所有记录
                    if(newValue == '' || newValue == null)
                        return true;

                    if(name.indexOf(newValue) >= 0){
                        return true;
                    }
                    return false;
                });
            }
        },
        mode: "local",
        displayField: 'brandEname',
        valueField: "brandId",
        allowBlank: true,
        editable:true
	}, {
		fieldLabel: "系列名称",
        name: "seriesId",
        xtype: "combobox",
        store: Ext.create("MIS.store.series.SeriesAllStore"),
        listeners: {
            select: function (combobox, record) {
                record = parseInt(combobox.getValue());
            	var singleId = combobox.up("form").down("combobox[name='singleId']");
            	singleId.setValue("");
            	var singleIdStore = singleId.getStore();
            	singleIdStore.proxy.extraParams.ofOrigin = record;
            	singleIdStore.proxy.extraParams.isUse = 1;
            	singleIdStore.reload();
            },
            
            change : function(field,newValue,oldValue){
                // 找到store
                var seriesIdStore = Ext.ComponentQuery.query('checkforcemodify')[0].down("combobox[name=seriesId]").getStore();

                //对store 进行过滤
                seriesIdStore.filterBy(function(record){
                    var name = record.raw.seriesName,
                        code = record.raw.seriesId;
                    //如果输入框为空，直接放回所有记录
                    if(newValue == '' || newValue == null)
                        return true;

                    if(name.indexOf(newValue) >= 0){
                        return true;
                    }
                    return false;
                });
            }
        },
        mode: "local",
        displayField: 'seriesName',
        valueField: "seriesId",
        allowBlank: true,
        editable:true
    }, {
		fieldLabel: "单品名称",
        name: "singleId",
        xtype: "combobox",
        store: Ext.create("MIS.store.singleproduct.SingleproductAllStore"),
        listeners: {
            change : function(field,newValue,oldValue){
                // 找到store
                var singleIdStore = Ext.ComponentQuery.query('checkforcemodify')[0].down("combobox[name=singleId]").getStore();

                //对store 进行过滤
                singleIdStore.filterBy(function(record){
                    var name = record.raw.singleName,
                        code = record.raw.singleId;
                    //如果输入框为空，直接放回所有记录
                    if(newValue == '' || newValue == null)
                        return true;

                    if(name.indexOf(newValue) >= 0){
                        return true;
                    }
                    return false;
                });
            }
        },
        mode: "local",
        displayField: 'singleName',
        valueField: "singleId",
        allowBlank: true,
        editable:true
    }, {
		fieldLabel: "数量",
        name: "num",
        xtype: "numberfield",
        minValue: 1, 
        allowBlank: true,
        editable:true
    }, {
		fieldLabel: "下单单价",
	    name: "unitPrice",
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
		fieldLabel: "付款人",
        name: "payby",
        xtype: "combobox",
        store: Ext.create("MIS.store.user.UserStore"),
        mode: "local",
        displayField: 'realname',
        valueField: "realname",
        allowBlank: true,
        editable:false
    }, {
		fieldLabel: "备注",
        name: "remark",
        xtype: "textarea",
        colspan: 2,
        width: 530
    }, {
        name: "id",
        hidden: true
    }, {
        name: "orderStatus",
        hidden: true
    }],
	
	buttons: [{
		text: "取消",
		handler: function(component){
			component.up("#checkforcemodifywindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var form = component.up("checkforcemodify");
        	var params = form.getForm().getValues();
        	
        	var tipText = "您是否确定强制修改该条信息，修改将可能影响到其他清单记录，修改后不能恢复。";
        	Ext.MessageBox.confirm("强制修改提示", tipText, function (confirmId) {
        		if(confirmId == "yes"){
        			Ext.Ajax.request({
        				url: "/check/modifyForce",
        				params: params,
        				success: function(conn, request, option, eOpts){
        					var result = Ext.JSON.decode(conn.responseText, true);
        					if(result.resultCode != 0){
        						Ext.MessageBox.alert("强制修改下单清单记录失败", "原因:" + result.resultMessage);
        					} else {
        						Ext.MessageBox.alert("强制修改成功", result.resultMessage);
        		                Ext.ComponentQuery.query("checkgrid")[0].store.reload();
        		                Ext.ComponentQuery.query("checkgrid")[0].getView().getSelectionModel().deselectAll();
        		                component.up("#checkforcemodifywindow").close();
        					}
        				},
        				failure: function (conn, request, option, eOpts) {
                            Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                        }
        				
        			});
        		}
        	});
			
		}
	}]
	
});