/**
 * 描述：字典项查询
 * 作者：王航
 */
Ext.define("MIS.view.dict.DictItemSearch", {
	extend: "Ext.form.Panel",
	alias: "widget.dictSearchpanel",
	
	defaultType: "textfield",
	defaults: {
		anchor: '100%',
		padding: "15 80"
	},
	
	items: [{
		xtype:'fieldset',
        title: '字典项查询',
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
                	var dictItemStore = component.up("dictview").down("dictitemgrid").getStore();
                	
                	// 设置Store参数
                	var selections = Ext.ComponentQuery.query("dictgrid")[0].getView().getSelectionModel().getSelection();
                	if(selections.length != 1){
                		Ext.MessageBox.alert("友情提示", "请选中单个字典查询");
                		return;
                	}
                	var dictitemname = component.up("dictSearchpanel").down("textfield[name=searchName]").getValue().trim(),
	            		dictitemcode = component.up("dictSearchpanel").down("textfield[name=searchCode]").getValue().trim(),
	            		dictitemvalue = component.up("dictSearchpanel").down("textfield[name=searchValue]").getValue().trim();
                	
                	var params = dictItemStore.proxy.extraParams;
                	params.typeId = selections[0].raw.id;
                	params.code = dictitemcode;
                	params.name = dictitemname;
                	params.value = dictitemvalue;
                	
                	// reload store
                	dictItemStore.reload();
                }
			}, {
				margin: "5 10",
				xtype: "button",
				text: "重置",
				scope: this,
				handler: function(component){
					component.up("dictSearchpanel").form.reset();
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
			fieldLabel: "值",
			labelWidth: 40,
			name: "searchValue",
			anchor: "55%" 
		}]
	}]
});