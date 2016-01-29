/**
 * 品牌查询
 */
Ext.define("MIS.view.brand.BrandSearch", {
	extend: "Ext.form.Panel",
	alias: "widget.brandSearchpanel",
	
	defaultType: "textfield",
	defaults: {
		anchor: '100%',
		padding: "15 80"
	},
	
	items: [{
		xtype:'fieldset',
        title: '品牌查询',
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
				width: 170,
				fieldLabel: "所属国家",
				labelWidth: 80,
				name: "ofOrigin",
				anchor: "55%",
				xtype: "combo",
		        store: Ext.create("MIS.store.dict.DictQueryStore", {
	            	dictcode: "country"
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
                	var brandStore = component.up("brandview").down("brandgrid").getStore();
                	
                	// 设置Store参数
                	var ofOrigin = component.up("brandSearchpanel").down("combo[name=ofOrigin]").getValue(),
                		isUse = component.up("brandSearchpanel").down("combo[name=isUse]").getValue(),
                		brandName = component.up("brandSearchpanel").down("textfield[name=brandName]").getValue(),
                		brandEname = component.up("brandSearchpanel").down("textfield[name=brandEname]").getValue();
                	
                	
                	var params = brandStore.proxy.extraParams;
                	params.ofOrigin = ofOrigin;
                	params.isUse = isUse;
                	params.brandName = brandName;
                	params.brandEname = brandEname;
                	
                	// reload store
                	brandStore.reload();
                }
			}, {
				margin: "5 10",
				xtype: "button",
				text: "重置",
				scope: this,
				handler: function(component){
					component.up("brandSearchpanel").form.reset();
				}
			}]
		}, {
			margin: "0 10",
			width: 180,
			fieldLabel: "是否生效",
			labelWidth: 60,
			name: "isUse",
			anchor: "55%",
			xtype: "combo",
	        store: Ext.create("MIS.store.dict.DictQueryStore", {
            	dictcode: "onOff"
            }),
	        mode: "local",
	        displayField: 'name',
	        valueField: "value",
	        editable:false
		}, {
			margin: "0 10",
			width: 200,
			xtype: "textfield",
			fieldLabel: "品牌名称",
			labelWidth: 60,
			name: "brandName",
			anchor: "55%" 
		}, {
			margin: "0 10",
			width: 200,
			xtype: "textfield",
			fieldLabel: "品牌英文名称",
			labelWidth: 80,
			name: "brandEname",
			anchor: "55%" 
		}]
	}]
});