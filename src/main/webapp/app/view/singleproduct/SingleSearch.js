/**
 * 单品查询
 */
Ext.define("MIS.view.singleproduct.SingleSearch", {
	extend: "Ext.form.Panel",
	alias: "widget.singleSearchpanel",
	
	defaultType: "textfield",
	defaults: {
		anchor: '100%',
		padding: "15 80"
	},
	
	items: [{
		xtype:'fieldset',
        title: '单品查询',
        padding: "5 5 0 5",
        margin: "3 10",
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
				fieldLabel: "所属品牌",
				labelWidth: 60,
				name: "brandId",
				anchor: "55%",
				xtype: "combo",
		        store: Ext.create("MIS.store.brand.BrandAllStore"),
		        listeners: {
		            select: function (combobox, record) {
		                record = parseInt(combobox.getValue());
		            	var seriesId = combobox.up("form").down("combo[name='ofOrigin']");
		            	seriesId.setValue("");
		            	var seriesIdStore = seriesId.getStore();
		            	seriesIdStore.proxy.extraParams.ofOrigin = record;
		            	seriesIdStore.proxy.extraParams.isUse = 1;
		            	seriesIdStore.reload();
		            },
		            
		            change : function(field,newValue,oldValue){
		                // 找到store
		                var brandIdStore = Ext.ComponentQuery.query('singleSearchpanel')[0].down("combo[name=brandId]").getStore();

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
                	var singleStore = component.up("singleview").down("singlegrid").getStore();
                	
                	// 设置Store参数
                	var ofOrigin = component.up("singleSearchpanel").down("combo[name=ofOrigin]").getValue(),
                		isUse = component.up("singleSearchpanel").down("combo[name=isUse]").getValue(),
                		unit = component.up("singleSearchpanel").down("combo[name=unit]").getValue(),
                		capacity = component.up("singleSearchpanel").down("textfield[name=capacity]").getValue(),
                		singleName = component.up("singleSearchpanel").down("textfield[name=singleName]").getValue(),
                		singleEname = component.up("singleSearchpanel").down("textfield[name=singleEname]").getValue(),
                		brandId = component.up("singleSearchpanel").down("combo[name=brandId]").getValue();
                	
                	
                	var params = singleStore.proxy.extraParams;
                	params.ofOrigin = ofOrigin;
                	params.isUse = isUse;
                	params.singleName = singleName;
                	params.singleEname = singleEname;
                	params.unit = unit;
                	params.capacity = capacity;
                	params.brandId = brandId;
                	
                	// reload store
                	singleStore.reload();
                }
			}, {
				margin: "5 10",
				xtype: "button",
				text: "重置",
				scope: this,
				handler: function(component){
					component.up("singleSearchpanel").form.reset();
				}
			}]
		}, {
			xtype: "container",
			layout: "anchor",
			items:[{
				margin: "0 10",
				width: 180,
				fieldLabel: "所属系列",
				labelWidth: 60,
				name: "ofOrigin",
				anchor: "55%",
				xtype: "combo",
		        store: Ext.create("MIS.store.series.SeriesAllStore"),
		        listeners: {
		            change : function(field,newValue,oldValue){
		                // 找到store
		                var ofOriginStore = Ext.ComponentQuery.query('singleSearchpanel')[0].down("combo[name=ofOrigin]").getStore();

		                //对store 进行过滤
		                ofOriginStore.filterBy(function(record){
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
		        editable:true
			}, {
				margin: "5 10",
				width: 180,
				xtype: "textfield",
				fieldLabel: "规格",
				labelWidth: 60,
				name: "capacity",
				anchor: "20%" 
			}]
		}, {
			xtype: "container",
			layout: "anchor",
			items:[{
				margin: "0 10",
				width: 200,
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
				margin: "5 10",
				width: 200,
				fieldLabel: "单位",
				labelWidth: 60,
				name: "unit",
				anchor: "20%",
				xtype: "combo",
		        store: Ext.create("MIS.store.dict.DictQueryStore", {
	            	dictcode: "unitDict"
	            }),
		        mode: "local",
		        displayField: 'name',
		        valueField: "value",
		        editable:false
			}]
		}, {
			xtype: "container",
			layout: "anchor",
			items:[{
				margin: "0 10",
				width: 200,
				xtype: "textfield",
				fieldLabel: "单品名称",
				labelWidth: 80,
				name: "singleName",
				anchor: "55%" 
			}, {
				margin: "5 10",
				width: 200,
				xtype: "textfield",
				fieldLabel: "单品英文名称",
				labelWidth: 80,
				name: "singleEname",
				anchor: "55%" 
			}]
		}]
	}]
});