/**
 * 子订单查询
 */
Ext.define("MIS.view.order.SubOrderSearch", {
	extend: "Ext.form.Panel",
	alias: "widget.subOrderSearchpanel",
	
	defaultType: "textfield",
	defaults: {
		anchor: '100%',
		padding: "15 80"
	},
	
	items: [{
		xtype:'fieldset',
        title: '子订单查询',
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
				xtype: "textfield",
				fieldLabel: "主订单编号",
				labelWidth: 40,
				name: "superOrderId",
				anchor: "55%" 
			}, {
				margin: "5 10",
				xtype: "button",
				text: '搜索',
        		name: "search",
        		anchor: '20%',
        		scope: this,
                handler: function(component){
                	// 找到store
                	var subOrderStore = component.up("suborderview").down("subordergrid").getStore();
                	
                	// 设置Store参数
                	var superOrderId = component.up("subOrderSearchpanel").down("textfield[name=superOrderId]").getValue().trim(),
                		curState = component.up("subOrderSearchpanel").down("combobox[name=curState]").getValue(),
                		brandId = component.up("subOrderSearchpanel").down("combobox[name=brandId]").getValue(),
                		seriesId = component.up("subOrderSearchpanel").down("combobox[name=seriesId]").getValue(),
                		singleId = component.up("subOrderSearchpanel").down("combobox[name=singleId]").getValue(),
                		startTime = component.up("subOrderSearchpanel").down("datefield[name=startTime]").getValue(),
                		endTime = component.up("subOrderSearchpanel").down("datefield[name=endTime]").getValue();
                	
                	console.log(superOrderId+"|"+curState+"|"+brandId+"|"+seriesId+"|"+singleId+"|"+Ext.util.Format.date(startTime,'Ymd')+"|"+Ext.util.Format.date(endTime,'Ymd'));
                	
                	var params = subOrderStore.proxy.extraParams;
                	params.superOrderId = superOrderId;
                	params.curState = curState;
                	params.brandId = brandId;
                	params.seriesId = seriesId;
                	params.singleId = singleId;
                	params.startTime = Ext.util.Format.date(startTime,'Ymd');
                	params.endTime = Ext.util.Format.date(endTime,'Ymd');
                	
                	// reload store
                	subOrderStore.reload();
                }
			}, {
				margin: "5 10",
				xtype: "button",
				text: "重置",
				scope: this,
				handler: function(component){
					component.up("subOrderSearchpanel").form.reset();
				}
			}]
		}, {
			xtype: "container",
			layout: "anchor",
			items:[{
				fieldLabel: "订单状态",
		        name: "curState",
		        xtype: "combobox",
		        store: Ext.create("MIS.store.dict.DictQueryStore", {
		        	dictcode: "subOrderState"
		        }),
		        mode: "local",
		        displayField: 'name',
		        valueField: "value",
		        allowBlank: true,
		        anchor: "55%",
		        labelWidth: 60,
		        width: 190,
		        margin: "0 10",
		        editable:false
			}, {
				margin: "5 10",
				width: 190,
				xtype: 'datefield',
				fieldLabel: "起始时间",
				labelWidth: 60,
				name: "startTime",
				anchor: "55%",
	            format: 'Y年m月d日'
			}]
		}, {
			xtype: "container",
			layout: "anchor",
			items:[{
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
		            	seriesIdStore.reload();
		            }
		        },
		        mode: "local",
		        displayField: 'brandName',
		        valueField: "brandId",
		        allowBlank: true,
		        anchor: "55%",
		        labelWidth: 60,
		        width: 190,
		        margin: "0 10",
		        editable:false
			}, {
				margin: "5 10",
				width: 190,
				xtype: 'datefield',
				fieldLabel: "结束时间",
				labelWidth: 60,
				name: "endTime",
				anchor: "55%" ,
	            format: 'Y年m月d日'
			}]
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
	            	var seriesIdStore = singleId.getStore();
	            	seriesIdStore.proxy.extraParams.ofOrigin = record;
	            	seriesIdStore.reload();
	            }
	        },
	        mode: "local",
	        displayField: 'seriesName',
	        valueField: "seriesId",
	        allowBlank: true,
	        anchor: "55%",
	        labelWidth: 60,
	        width: 190,
	        margin: "0 10",
	        editable:false
		}, {
			fieldLabel: "单品名称",
	        name: "singleId",
	        xtype: "combobox",
	        store: Ext.create("MIS.store.singleproduct.SingleproductStore"),
	        mode: "local",
	        displayField: 'singleName',
	        valueField: "singleId",
	        allowBlank: true,
	        anchor: "55%",
	        labelWidth: 60,
	        width: 190,
	        margin: "0 10",
	        editable:false
		}]
	}]
});