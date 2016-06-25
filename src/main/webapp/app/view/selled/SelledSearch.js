/**
 * 入库清单查询
 */
Ext.define("MIS.view.selled.SelledSearch", {
	extend: "Ext.form.Panel",
	alias: "widget.selledSearchpanel",
	
	defaultType: "textfield",
	defaults: {
		anchor: '100%',
		padding: "15 80"
	},
	
	items: [{
		xtype:'fieldset',
        title: '售出记录查询',
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
				width: 180,
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
		                var brandIdStore = Ext.ComponentQuery.query('selledSearchpanel')[0].down("combobox[name=brandId]").getStore();

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
				xtype: "container",
				layout: "hbox",
				items:[{
					margin: "5 10",
					xtype: "button",
					text: '搜索',
	        		name: "search",
	        		anchor: '20%',
	        		scope: this,
	                handler: function(component){
	                	// 找到store
	                	var storageStore = component.up("selledview").down("selledgrid").getStore();
	                	
	                	// 设置Store参数
	                	var seriesId = component.up("selledSearchpanel").down("combobox[name=seriesId]").getValue(),
	                		brandId = component.up("selledSearchpanel").down("combobox[name=brandId]").getValue(),
	                		singleId = component.up("selledSearchpanel").down("combobox[name=singleId]").getValue(),
	                		startTime = component.up("selledSearchpanel").down("datefield[name=startTime]").getValue(),
	                		endTime = component.up("selledSearchpanel").down("datefield[name=endTime]").getValue(),
	                		selledStatus = component.up("selledSearchpanel").down("radio[name=selledStatus]").getGroupValue();
	                	
	                	var params = storageStore.proxy.extraParams;
	                	params.seriesId = seriesId;
	                	params.brandId = brandId;
	                	params.singleId = singleId;
	                	params.startTime = Ext.util.Format.date(startTime,'Ymd');
	                	params.endTime = Ext.util.Format.date(endTime,'Ymd');
	                	params.selledStatus = selledStatus;
	                	
	                	
	                	// reload store
	                	storageStore.reload();}
				}, {
					margin: "5 10",
					xtype: "button",
					text: "重置",
					scope: this,
					handler: function(component){
						component.up("selledSearchpanel").form.reset();
					}
				}, {
					margin: "5 10",
		            xtype: "radio",
		            name: "selledStatus",
		            labelWidth: 45,
		            inputValue: '3',
		            boxLabel: "已售出"
				}]
			}]
		}, {
			xtype: "container",
			layout: "anchor",
			items:[{
				margin: "0 10",
				width: 180,
				labelWidth: 60,
				anchor: "65%",
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
		                var seriesIdStore = Ext.ComponentQuery.query('selledSearchpanel')[0].down("combobox[name=seriesId]").getStore();

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
				xtype: "container",
				layout: "hbox",
				items:[{
		        	margin: "5 10",
		            xtype: "radio",
		            name: "selledStatus",
		            labelWidth: 45,
		            inputValue: '5',
		            boxLabel: "已售出（补损）"
		        }, {
		        	margin: "5 10",
		            xtype: "radio",
		            name: "selledStatus",
		            labelWidth: 45,
		            inputValue: '98',
		            boxLabel: "已损坏"
		        }]
	        	
	        }]
			
		}, {
			margin: "0 10",
			width: 180,
			labelWidth: 60,
			anchor: "55%",
			fieldLabel: "单品名称",
	        name: "singleId",
	        xtype: "combobox",
	        store: Ext.create("MIS.store.singleproduct.SingleproductStore"),
	        listeners: {
	            change : function(field,newValue,oldValue){
	                // 找到store
	                var singleIdStore = Ext.ComponentQuery.query('selledSearchpanel')[0].down("combobox[name=singleId]").getStore();

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
        	xtype: "container",
			layout: "anchor",
			items:[{
				margin: "0 10",
				width: 180,
				xtype: 'datefield',
				fieldLabel: "起始时间",
				name: "startTime",
				anchor: "55%",
		        format: 'Ymd',
		        editable:false,
				labelWidth: 80,
				anchor: "55%"
			}, {
	        	margin: "5 10",
				width: 180,
				xtype: 'datefield',
				fieldLabel: "结束时间",
				name: "endTime",
				anchor: "55%",
		        format: 'Ymd',
		        editable:false,
				labelWidth: 80,
				anchor: "55%"
			}]
		}]
	}]
});