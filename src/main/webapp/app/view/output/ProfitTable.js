/**
 * 入库清单查询
 */
Ext.define("MIS.view.output.ProfitTable", {
	extend: "Ext.form.Panel",
	alias: "widget.profittable",
	
	defaultType: "textfield",
	defaults: {
		anchor: '100%',
		padding: "15 80"
	},
	
	items: [{
		xtype:'fieldset',
        title: '利润导出',
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
				width: 230,
				xtype: 'datefield',
				fieldLabel: "*下单时间(起始)",
				name: "startTime",
				anchor: "55%",
		        format: 'Ymd',
		        editable:false,
				labelWidth: 100,
				anchor: "55%"
			}, {
				margin: "5 10",
				xtype: "button",
				text: '导出',
        		name: "search",
        		anchor: '20%',
        		scope: this,
                handler: function(component){
                	var startTime = component.up("profittable").down("datefield[name=startTime]").getValue(),
                		endTime = component.up("profittable").down("datefield[name=endTime]").getValue();
                	//Ext.util.Format.date(storeTime,'Ymd');
                	
                	if(startTime == '' || endTime == '' || startTime == null || endTime == null){
                		Ext.MessageBox.alert("请求失败", "[起始时间]和[结束时间]不能为空");
                		return;
                	}
                	
                	window.location.href = '/clear/profit?startTime='+Ext.util.Format.date(startTime,'Ymd')+'&endTime='+Ext.util.Format.date(endTime,'Ymd');  
                	
                }
			}, {
				margin: "5 10",
				xtype: "button",
				text: "重置",
				scope: this,
				handler: function(component){
					component.up("profittable").form.reset();
				}
			}]
		}, {
			margin: "0 10",
			width: 230,
			xtype: 'datefield',
			fieldLabel: "*下单时间(结束)",
			name: "endTime",
			anchor: "55%",
	        format: 'Ymd',
	        editable:false,
			labelWidth: 100,
			anchor: "55%"
		}]
	}, {
		xtype:'fieldset',
        title: '售出清单导出',
        padding: "5 5 0 5",
        margin: "5 10",
        defaults: {
            anchor: "100%"
        },
		
		layout: "hbox",//横向表格
		
		items:[{
			xtype: "container",
			layout: "anchor",
			items:[{
				margin: "0 10",
				width: 230,
				xtype: 'datefield',
				fieldLabel: "*下单时间(起始)",
				name: "selledStartTime",
				anchor: "55%",
		        format: 'Ymd',
		        editable:false,
				labelWidth: 100,
				anchor: "55%"
			}, {
				margin: "5 10",
				xtype: "button",
				text: '导出',
        		name: "search",
        		anchor: '20%',
        		scope: this,
                handler: function(component){
                	var startTime = component.up("profittable").down("datefield[name=selledStartTime]").getValue(),
                		endTime = component.up("profittable").down("datefield[name=selledEndTime]").getValue();
                	//Ext.util.Format.date(storeTime,'Ymd');
                	
                	if(startTime == '' || endTime == '' || startTime == null || endTime == null){
                		Ext.MessageBox.alert("请求失败", "[起始时间]和[结束时间]不能为空");
                		return;
                	}
                	
                	window.location.href = '/clear/selled?startTime='+Ext.util.Format.date(startTime,'Ymd')+'&endTime='+Ext.util.Format.date(endTime,'Ymd');  
                	
                }
			}, {
				margin: "5 10",
				xtype: "button",
				text: "重置",
				scope: this,
				handler: function(component){
					component.up("profittable").form.reset();
				}
			}]
		}, {
			margin: "0 10",
			width: 230,
			xtype: 'datefield',
			fieldLabel: "*下单时间(结束)",
			name: "selledEndTime",
			anchor: "55%",
	        format: 'Ymd',
	        editable:false,
			labelWidth: 100,
			anchor: "55%"
		}]
	}]
});