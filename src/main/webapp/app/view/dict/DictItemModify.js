/**
 * 描述: 字典子项修改窗口
 * 作者: 王航
 */
Ext.define("MIS.view.dict.DictItemModify", {
    extend: "Ext.form.Panel",
    alias: "widget.dictitemmodify",

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
        fieldLabel: "字典条目值",
        name: "value",
        allowBlank: false
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
        handler: function (component) {
        	component.up("#dictitemmodifywindow").close();
        }
    }, {
        text: "确定",
        handler: function (component) {
        	var dictname = component.up("dictitemmodify").down("textfield[name=name]").getValue(),
        		dictvalue = component.up("dictitemmodify").down("textfield[name=value]").getValue(),
        		dictcode = component.up("dictitemmodify").down("textfield[name=code]").getValue(),
        		description = component.up("dictitemmodify").down("textfield[name=description]").getValue(),
        		dictid = component.up("#dictitemmodifywindow").extraData.id;
        	
        	Ext.Ajax.request({
        		url: "/dictitem/modify",
        		params: {
        			id: dictid,
        			name: dictname,
        			value: dictvalue,
        			code: dictcode,
        			description: description
        		},
        		success: function(conn, request, option, eOpts){
        			var result = Ext.JSON.decode(conn.responseText, true);
        			if(result.resultCode != 0){
        				Ext.MessageBox.alert("请求失败", "修改字典子项失败" + result.resultMessage);
        			} else {
        				Ext.ComponentQuery.query("dictitemgrid")[0].store.reload();
        				Ext.ComponentQuery.query("dictitemgrid")[0].getView().getSelectionModel().deselectAll();
        				component.up("#dictitemmodifywindow").close();
        			}
        		},
        		failure: function(conn, request, option, eOpts){
        			Ext.MessageBox.alert("请求失败", "服务器繁忙，稍后重试!");
        		}
        	});
        }
    }]
});
