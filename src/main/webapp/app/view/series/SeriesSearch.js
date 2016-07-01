/**
 * 系列查询
 */
Ext.define("MIS.view.series.SeriesSearch", {
	extend: "Ext.form.Panel",
	alias: "widget.seriesSearchpanel",
	
	defaultType: "textfield",
	defaults: {
		anchor: '100%',
		padding: "15 80"
	},
	
	items: [{
		xtype:'fieldset',
        title: '系列查询',
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
				fieldLabel: "所属品牌",
				labelWidth: 60,
				name: "ofOrigin",
				anchor: "55%",
				xtype: "combo",
		        store: Ext.create("MIS.store.brand.BrandAllStore"),
		        listeners: {
		            change : function(field,newValue,oldValue){
		                // 找到store
		                var ofOriginStore = Ext.ComponentQuery.query('seriesSearchpanel')[0].down("combo[name=ofOrigin]").getStore();

		                //对store 进行过滤
		                ofOriginStore.filterBy(function(record){
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
                	var seriesStore = component.up("seriesview").down("seriesgrid").getStore();
                	
                	// 设置Store参数
                	var ofOrigin = component.up("seriesSearchpanel").down("combo[name=ofOrigin]").getValue(),
                		isUse = component.up("seriesSearchpanel").down("combo[name=isUse]").getValue(),
                		seriesName = component.up("seriesSearchpanel").down("textfield[name=seriesName]").getValue(),
                		seriesEname = component.up("seriesSearchpanel").down("textfield[name=seriesEname]").getValue();
                	
                	
                	var params = seriesStore.proxy.extraParams;
                	params.ofOrigin = ofOrigin;
                	params.isUse = isUse;
                	params.seriesName = seriesName;
                	params.seriesEname = seriesEname;
                	
                	// reload store
                	seriesStore.reload();
                }
			}, {
				margin: "5 10",
				xtype: "button",
				text: "重置",
				scope: this,
				handler: function(component){
					component.up("seriesSearchpanel").form.reset();
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
			width: 190,
			xtype: "textfield",
			fieldLabel: "系列名称",
			labelWidth: 60,
			name: "seriesName",
			anchor: "55%" 
		}, {
			margin: "0 10",
			width: 190,
			xtype: "textfield",
			fieldLabel: "系列英文名称",
			labelWidth: 80,
			name: "seriesEname",
			anchor: "55%" 
		}]
	}]
});