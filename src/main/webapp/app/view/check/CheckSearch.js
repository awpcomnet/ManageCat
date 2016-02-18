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
        title: '主订单查询',
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
				width: 150,
				fieldLabel: "国外转运状态",
				labelWidth: 80,
				name: "foreignState",
				anchor: "55%",
				xtype: "combo",
		        store: Ext.create("MIS.store.dict.DictQueryStore", {
	            	dictcode: "foreignState"
	            }),
		        mode: "local",
		        displayField: 'name',
		        valueField: "value",
		        editable:false
			}, {
				margin: "5 10",
				xtype: "button",
				text: '搜索',
        		name: "search",
        		anchor: '20%',
        		scope: this,
                handler: function(component){
                	// 找到store
                	var orderStore = component.up("orderview").down("ordergrid").getStore();
                	
                	// 设置Store参数
                	var foreignState = component.up("orderSearchpanel").down("combo[name=foreignState]").getValue(),
                		transfer = component.up("orderSearchpanel").down("combo[name=transfer]").getValue(),
                		affirmState = component.up("orderSearchpanel").down("combo[name=affirmState]").getValue(),
                		startTime = component.up("orderSearchpanel").down("datefield[name=startTime]").getValue(),
                		endTime = component.up("orderSearchpanel").down("datefield[name=endTime]").getValue();
                	
                	console.log(foreignState+"|"+transfer+"|"+affirmState+"|"+Ext.util.Format.date(startTime,'Ymd')+"|"+Ext.util.Format.date(endTime,'Ymd'));
                	
                	var params = orderStore.proxy.extraParams;
                	params.foreignState = foreignState;
                	params.transfer = transfer;
                	params.affirmState = affirmState;
                	params.startTime = Ext.util.Format.date(startTime,'Ymd');
                	params.endTime = Ext.util.Format.date(endTime,'Ymd');
                	
                	// reload store
                	orderStore.reload();
                }
			}, {
				margin: "5 10",
				xtype: "button",
				text: "重置",
				scope: this,
				handler: function(component){
					component.up("orderSearchpanel").form.reset();
				}
			}]
		}, {
			margin: "0 10",
			width: 180,
			fieldLabel: "转运状态",
			labelWidth: 60,
			name: "transfer",
			anchor: "55%",
			xtype: "combo",
	        store: Ext.create("MIS.store.dict.DictQueryStore", {
            	dictcode: "transfer"
            }),
	        mode: "local",
	        displayField: 'name',
	        valueField: "value",
	        editable:false
		}, {
			margin: "0 10",
			width: 180,
			fieldLabel: "确认收货状态",
			labelWidth: 80,
			name: "affirmState",
			anchor: "55%",
			xtype: 'combo',
	        editable:false,
	        store: Ext.create("MIS.store.dict.DictQueryStore", {
            	dictcode: "affirmState"
            }),
	        displayField : 'name',
			valueField : "value",
	        mode: "local"
		}, {
			margin: "0 15",
			width: 200,
			xtype: 'datefield',
			fieldLabel: "起始时间",
			labelWidth: 60,
			name: "startTime",
			anchor: "55%",
            format: 'Y年m月d日'
		}, {
			margin: "0 15",
			width: 200,
			xtype: 'datefield',
			fieldLabel: "结束时间",
			labelWidth: 60,
			name: "endTime",
			anchor: "55%" ,
            format: 'Y年m月d日'
		}]
	}]
});