/**
 * 描述：栏目查询
 * 作者：王航
 */
Ext.define("MIS.view.catalog.CatalogSearch", {
	extend: "Ext.form.Panel",
	alias: "widget.catalogSearchpanel",
	
	defaultType: "textfield",
	defaults: {
		anchor: '100%',
		padding: "15 80"
	},
	
	items: [{
		xtype:'fieldset',
        title: '栏目查询',
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
				fieldLabel: "名称",
				labelWidth: 40,
				name: "searchName",
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
                	var catalogStore = component.up("catalogview").down("cataloggrid").getStore();
                	
                	// 设置Store参数
                	var selections = Ext.ComponentQuery.query("catalogtree")[0].getView().getSelectionModel().getSelection();
                	if(selections.length != 1){
                		Ext.MessageBox.alert("友情提示", "请选中单个字典查询");
                		return;
                	}
                	var catalogname = component.up("catalogSearchpanel").down("textfield[name=searchName]").getValue().trim(),
                		catalogcode = component.up("catalogSearchpanel").down("textfield[name=searchCode]").getValue().trim(),
                		catalogUrlType = component.up("catalogSearchpanel").down("textfield[name=searchUrlType]").getValue().trim();
                	
                	var params = catalogStore.proxy.extraParams;
                	params.parentId = selections[0].raw.id;
                	params.abbr = catalogcode;
                	params.name = catalogname;
                	params.urlType = catalogUrlType;
                	
                	// reload store
                	catalogStore.reload();
                }
			}, {
				margin: "5 10",
				xtype: "button",
				text: "重置",
				scope: this,
				handler: function(component){
					component.up("catalogSearchpanel").form.reset();
				}
			}]
		}, {
			margin: "0 10",
			width: 200,
			xtype: "textfield",
			fieldLabel: "代码",
			labelWidth: 40,
			name: "searchCode",
			anchor: "55%" 
		}, {
			margin: "0 10",
			width: 200,
			xtype: "textfield",
			fieldLabel: "栏目类型",
			labelWidth: 60,
			name: "searchUrlType",
			anchor: "55%" 
		}]
	}]
	
	
	
});