/**
 * 邮寄清单查询
 */
Ext.define("MIS.view.shipped.ShippedSearch", {
	extend: "Ext.form.Panel",
	alias: "widget.shippedSearchpanel",
	
	defaultType: "textfield",
	defaults: {
		anchor: '100%',
		padding: "15 80"
	},
	
	items: [{
		xtype:'fieldset',
        title: '邮寄清单查询',
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
				xtype: "button",
				text: '搜索',
        		name: "search",
        		anchor: '20%',
        		scope: this,
                handler: function(component){
                	// 找到store
                	var shippedStore = component.up("shippedview").down("shippedgrid").getStore();
                	
                	// 设置Store参数
                	var trackingNumber = component.up("shippedSearchpanel").down("textfield[name=trackingNumber]").getValue(),
                		transferCompany = component.up("shippedSearchpanel").down("combobox[name=transferCompany]").getValue(),
                		submitTimeStart = component.up("shippedSearchpanel").down("monthfield[name=submitTimeStart]").getValue(),
                		submitTimeEnd = component.up("shippedSearchpanel").down("monthfield[name=submitTimeEnd]").getValue(),
                		flag = component.up("shippedSearchpanel").down("radio[name=flag]").getGroupValue();
                	console.info(Ext.util.Format.date(submitTimeStart,'Ym')+"|"+Ext.util.Format.date(submitTimeEnd,'Ym'));
                	
                	var params = shippedStore.proxy.extraParams;
                	params.trackingNumber = trackingNumber;
                	params.transferCompany = transferCompany;
                	params.submitTimeStart = Ext.util.Format.date(submitTimeStart,'Ym');
                	params.submitTimeEnd = Ext.util.Format.date(submitTimeEnd,'Ym');
                	params.flag = flag;
                	
                	
                	// reload store
                	shippedStore.reload();
                }
			}, {
				margin: "5 10",
				xtype: "button",
				text: "重置",
				scope: this,
				handler: function(component){
					component.up("shippedSearchpanel").form.reset();
				}
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
				xtype: "container",
				layout: "hbox",
				items:[{
					margin: "5 5",
		            xtype:"radio",
		            name:"flag",
		            labelWidth: 50,
		            checked : true,
		            inputValue: '1',
		            boxLabel:"已邮寄"
				}, {
					margin: "5 5",
		            xtype: "radio",
		            name: "flag",
		            labelWidth: 50,
		            inputValue: '2',
		            boxLabel: "已入库"
				}, {
					margin: "5 5",
		            xtype: "radio",
		            name: "flag",
		            labelWidth: 50,
		            inputValue: '',
		            boxLabel: "所有"
				}]
			}]
			
			
		}, {
			margin: "0 10",
			width: 200,
			xtype: 'monthfield',
			fieldLabel: "提交开始时间",
			name: "submitTimeStart",
			anchor: "55%",
	        format: 'Y-m',
	        editable:false,
			labelWidth: 80,
			anchor: "55%"
			
		}, {
			margin: "0 10",
			width: 200,
			xtype: 'monthfield',
			fieldLabel: "提交结束时间",
			name: "submitTimeEnd",
			anchor: "55%",
	        format: 'Y-m',
	        editable:false,
			labelWidth: 80,
			anchor: "55%"
			
		}]
	}]
});