/**
 * 下单清单查询
 */
Ext.define("MIS.view.check.CheckSearch", {
	extend: "Ext.form.Panel",
	alias: "widget.checkSearchpanel",
	
	defaultType: "textfield",
	defaults: {
		anchor: '100%',
		padding: "15 80"
	},
	
	items: [{
		xtype:'fieldset',
        title: '下单清单查询',
        padding: "5 5 0 5",
        margin: "0 10",
        defaults: {
            anchor: "100%"
        },
		
		layout: "hbox",//横向表格
		
		items:[{
			xtype: "container",
			layout: "anchor",
			items:[{
				margin: "0 10",
				width: 200,
				fieldLabel: "快递单号",
				labelWidth: 70,
				name: "trackingNumber",
				anchor: "55%",
				xtype: "textfield",
		        editable:true
			}, {
				margin: "5 10",
				width: 200,
				fieldLabel: "批次号",
				labelWidth: 70,
				name: "batchNo",
				anchor: "55%",
				xtype: "textfield",
		        editable:true
			}]
		}, {
			xtype: "container",
			layout: "anchor",
			items:[{
				margin: "0 10",
				width: 180,
				fieldLabel: "转运公司",
				labelWidth: 60,
				name: "transferCompany",
				anchor: "55%",
				xtype: "combobox",
		        store: Ext.create("MIS.store.dict.DictQueryStore", {
	            	dictcode: "transferCompany"
	            }),
		        mode: "local",
		        displayField: 'name',
		        valueField: "value",
		        editable:false
			}, {
				margin: "5 10",
				anchor: "55%",
				width: 180,
				labelWidth: 60,
				fieldLabel: "付款人",
		        name: "payby",
		        xtype: "combobox",
		        store: Ext.create("MIS.store.user.UserStore"),
		        mode: "local",
		        displayField: 'realname',
		        valueField: "realname",
		        allowBlank: true,
		        editable:false
			}]
			
		}, {
			xtype: "container",
			layout: "anchor",
			items:[{
				margin: "0 10",
				width: 180,
				fieldLabel: "下单网站",
				labelWidth: 60,
				name: "orderAddr",
				anchor: "55%",
				xtype: "combobox",
		        store: Ext.create("MIS.store.dict.DictQueryStore", {
	            	dictcode: "orderAddr"
	            }),
		        mode: "local",
		        displayField: 'name',
		        valueField: "value",
		        editable:false
			}, {
				margin: "5 10",
				anchor: "55%",
				width: 180,
				labelWidth: 60,
				fieldLabel: "清单状态",
		        name: "orderStatus",
		        xtype: "combobox",
		        store: Ext.create("MIS.store.dict.DictQueryStore", {
	            	dictcode: "orderStatus"
	            }),
		        mode: "local",
		        displayField: 'name',
		        valueField: "value",
		        allowBlank: true,
		        editable:false
			}]
			
		}, {
			xtype: "container",
			layout: "anchor",
			items:[{
				margin: "0 10",
				width: 175,
				xtype: 'datefield',
				fieldLabel: "下单时间",
				name: "orderTime",
				anchor: "55%",
		        format: 'Ymd',
		        editable:false,
				labelWidth: 60,
				anchor: "55%"
			}, {
				margin: "5 10",
				width: 175,
				labelWidth: 60,
				anchor: "55%",
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
		                var seriesIdStore = Ext.ComponentQuery.query('checkSearchpanel')[0].down("combobox[name=seriesId]").getStore();

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
			}]
			
		}, {
			xtype: "container",
			layout: "anchor",
			items:[{
				margin: "0 10",
				width: 175,
				labelWidth: 60,
				anchor: "55%",
		        fieldLabel: "品牌名称",
		        name: "brandId",
		        xtype: "combobox",
		        store: Ext.create("MIS.store.brand.BrandStore"),
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
		                var brandIdStore = Ext.ComponentQuery.query('checkSearchpanel')[0].down("combobox[name=brandId]").getStore();

		                //对store 进行过滤
		                brandIdStore.filterBy(function(record){
		                    var name = record.raw.brandName,
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
		        displayField: 'brandName',
		        valueField: "brandId",
		        allowBlank: true,
		        editable:true
			}, {
				margin: "5 10",
				width: 175,
				labelWidth: 60,
				anchor: "55%",
				fieldLabel: "单品名称",
		        name: "singleId",
		        xtype: "combobox",
		        store: Ext.create("MIS.store.singleproduct.SingleproductStore"),
		        listeners: {
		            change : function(field,newValue,oldValue){
		                // 找到store
		                var singleIdStore = Ext.ComponentQuery.query('checkSearchpanel')[0].down("combobox[name=singleId]").getStore();

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
			}]
			
		}, {
			xtype: "container",
			layout: "anchor",
			items:[{
				margin: "0 10",
				xtype: "button",
				text: '搜索',
				name: "search",
				anchor: '20%',
				scope: this,
			    handler: function(component){
			    	// 找到store
			    	var checkStore = component.up("checkview").down("checkgrid").getStore();
			    	
			    	// 设置Store参数
			    	var trackingNumber = component.up("checkSearchpanel").down("textfield[name=trackingNumber]").getValue(),
			    		transferCompany = component.up("checkSearchpanel").down("combobox[name=transferCompany]").getValue(),
			    		orderTime = component.up("checkSearchpanel").down("datefield[name=orderTime]").getValue(),
			    		orderAddr = component.up("checkSearchpanel").down("combobox[name=orderAddr]").getValue(),
			    		payby = component.up("checkSearchpanel").down("combobox[name=payby]").getValue(),
			    		orderStatus = component.up("checkSearchpanel").down("combobox[name=orderStatus]").getValue(),
			    		brandId = component.up("checkSearchpanel").down("combobox[name=brandId]").getValue(),
			    		seriesId = component.up("checkSearchpanel").down("combobox[name=seriesId]").getValue(),
			    		singleId = component.up("checkSearchpanel").down("combobox[name=singleId]").getValue(),
			    		batchNo = component.up("checkSearchpanel").down("textfield[name=batchNo]").getValue();
			    		
			    	
			    	
			    	var params = checkStore.proxy.extraParams;
			    	params.trackingNumber = trackingNumber;
			    	params.transferCompany = transferCompany;
			    	params.orderTime = Ext.util.Format.date(orderTime,'Ymd');
			    	params.orderAddr = orderAddr;
			    	params.payby = payby;
			    	params.orderStatus = orderStatus;
			    	params.brandId = brandId;
			    	params.seriesId = seriesId;
			    	params.singleId = singleId;
			    	params.batchNo = batchNo;
			    	
			    	// reload store
			    	checkStore.reload();
			    }
			}, {
				margin: "5 10",
				xtype: "button",
				text: "重置",
				scope: this,
				handler: function(component){
					component.up("checkSearchpanel").form.reset();
				}
			}]
		}]
	}]
});