/**
 * 描述：字典修改窗口
 * 作者：王航
 */
Ext.define("MIS.view.dict.DictModify",{
	extend: "Ext.form.Panel",
	alias: "widget.dictmodify",
	
	defaultType: "textfield",
	defaults: {
		anchor: "100%",
		padding: "5 5"
	},
	
	items: [{
		fieldLabel: "字典条目名",
		name: "name",
		allowBlank: false,
	}, {
		fieldLabel: "字典代码",
		name: "code",
		allowBlank: false
	}, {
		fieldLabel: "描述",
		name: "description",
		allowBlank: true
	}],
	
	buttons: [{
		text: "取消",
		handler: function (component){
			component.up("#dictmodifywindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var dictname = component.up("dictmodify").down("textfield[name=name]").getValue(),
			    dictcode = component.up("dictmodify").down("textfield[name=code]").getValue(),
			    description = component.up("dictmodify").down("textfield[name=description]").getValue(),
			    dictid = component.up("#dictmodifywindow").extraData.id;
				dicttype = component.up("#dictmodifywindow").extraData.type;
			
			Ext.Ajax.request({
				url: "/dict/modify",
				params: {
					id: dictid,
					name: dictname,
					code: dictcode,
					description: description,
					type: dicttype
				},
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("请求失败", "修改字典失败" + result.resultMessage);
					} else {
						Ext.ComponentQuery.query("dictgrid")[0].store.reload();
						Ext.ComponentQuery.query("dictgrid")[0].getView().getSelectionModel().deselectAll();
						component.up("#dictmodifywindow").close();
					}
				},
				failure: function(conn, request, option, eOpts){
					Ext.MessageBox.alert("请求失败", "服务器繁忙，稍后重试!");
				}
				
			});
			 
		}
	}]
});