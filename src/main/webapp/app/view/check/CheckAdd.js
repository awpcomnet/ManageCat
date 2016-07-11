/**
 * 下单清单添加
 */
Ext.define("MIS.view.check.CheckAdd", {
	extend: "Ext.form.Panel",
	alias: "widget.checkadd",
	
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
        store: Ext.create("MIS.store.batch.BatchAssignStore"),
        mode: "local",
        displayField: 'batchNo',
        valueField: "batchNo",
        allowBlank: true,
        editable:false
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
        value: '0',
        editable:false
    }, {
		fieldLabel: "快递单号",
        name: "trackingNumber",
        xtype: "textfield",
        colspan: 1,
        anchor: "55%"
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
        listeners: {
            change : function(field,newValue,oldValue){
                // 找到store
                var singleIdStore = Ext.ComponentQuery.query('checkadd')[0].down("combobox[name=orderAddr]").getStore();

                //对store 进行过滤
                singleIdStore.filterBy(function(record){
                    var name = record.raw.name,
                        code = record.raw.value;
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
        displayField: 'name',
        valueField: "value",
        allowBlank: true,
        editable:true
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
                var brandIdStore = Ext.ComponentQuery.query('checkadd')[0].down("combobox[name=brandId]").getStore();

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
        store: Ext.create("MIS.store.series.SeriesStore"),
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
                var seriesIdStore = Ext.ComponentQuery.query('checkadd')[0].down("combobox[name=seriesId]").getStore();

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
                var singleIdStore = Ext.ComponentQuery.query('checkadd')[0].down("combobox[name=singleId]").getStore();

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
        store: Ext.create("MIS.store.user.UserStateStore"),
        mode: "local",
        displayField: 'username',
        valueField: "realname",
        allowBlank: true,
        editable:false
    }, {
		fieldLabel: "备注",
        name: "remark",
        xtype: "textarea",
        colspan: 3,
        width: 530
    }],
	
	buttons: [{
		text: "生成批次号",
		handler: function(component){
			Ext.Ajax.request({
				url: "/batch/create",
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("创建批次号失败", "原因:" + result.resultMessage);
					} else {
						var resultBatchNo = result.results[0];
						var batchNo = Ext.ComponentQuery.query("checkadd combobox[name=batchNo]")[0];
						
						batchNo.setValue(resultBatchNo.trim());
						batchNo.setReadOnly(true);
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
			component.up("#checkaddwindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var checkAdd = component.up("checkadd");
			
			var trackingNumber = checkAdd.down("textfield[name=trackingNumber]").getValue().trim(),
				orderTime = checkAdd.down("datefield[name=orderTime]").getValue(),
				payby = checkAdd.down("combobox[name=payby]").getValue(),
				transferCompany = checkAdd.down("combobox[name=transferCompany]").getValue(),
				orderAddr = checkAdd.down("combobox[name=orderAddr]").getValue(),
				brandId = checkAdd.down("combobox[name=brandId]").getValue(),
				seriesId = checkAdd.down("combobox[name=seriesId]").getValue(),
				singleId = checkAdd.down("combobox[name=singleId]").getValue(),
				num = checkAdd.down("numberfield[name=num]").getValue(),
				unitPrice = checkAdd.down("textfield[name=unitPrice]").getValue(),
				remark = checkAdd.down("textarea[name=remark]").getValue(),
				batchNo = checkAdd.down("textfield[name=batchNo]").getValue(),
				rate = checkAdd.down("numberfield[name=rate]").getValue(),
				currency = checkAdd.down("combobox[name=currency]").getValue();
			
			
			if(batchNo == null && batchNo == '' && batchNo == 'null'){
				Ext.MessageBox.alert("批次号错误！["+batchNo+"]");
				return;
			}
			
			console.log("brandId="+brandId+"|orderTime="+Ext.util.Format.date(orderTime,'Ymd')+"|payby="+payby);
			
			var params = {
					trackingNumber: trackingNumber,
					orderTime: Ext.util.Format.date(orderTime,'Ymd'),
					payby: payby,
					transferCompany: transferCompany,
					orderAddr: orderAddr,
					brandId: brandId,
					seriesId: seriesId,
					singleId: singleId,
					num: num,
					unitPrice: unitPrice,
					remark: remark,
					batchNo: batchNo,
					rate: rate,
					currency: currency
	        };
			
			Ext.Ajax.request({
				url: "/check/add",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("添加下单清单失败", "原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("checkgrid")[0].store.reload();
		                Ext.ComponentQuery.query("checkgrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#checkaddwindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});