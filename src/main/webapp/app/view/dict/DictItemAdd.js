/**
 * 描述: 字典项增加窗口
 * 作者: 
 */
Ext.define("MIS.view.dict.DictItemAdd", {
    extend: "Ext.form.Panel",
    alias: "widget.dictitemadd",

    defaultType: "textfield",
    defaults: {
        anchor: "100%",
        padding: "5 5"
    },

    items: [{
        fieldLabel: "字典条目名",
        name: "name",
        allowBlank: false
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
        	component.up("#dictitemaddwindow").close();
        }
    }, {
        text: "确定",
        handler: function (component) {
        	var dictname = component.up("dictitemadd").down("textfield[name=name]").getValue(),
        		dictvalue = component.up("dictitemadd").down("textfield[name=value]").getValue(),
        		dictcode = component.up("dictitemadd").down("textfield[name=code]").getValue(),
        		description = component.up("dictitemadd").down("textfield[name=description]").getValue()
        		dictType = Ext.ComponentQuery.query("dictitemgrid")[0].dictType;
        	
        	Ext.Ajax.request({
        		url: "/dictitem/add",
        		params: {
        			typeId: dictType,
        			name: dictname,
        			value: dictvalue,
        			code: dictcode,
        			description: description
        		},
        		success: function(conn, request, option, eOpts){
        			var result = Ext.JSON.decode(conn.responseText, true);
        			if(result.resultCode != 0){
        				Ext.MessageBox.alert("请求失败", "添加字典子项失败" + result.resultMessage);
        			} else {
        				Ext.ComponentQuery.query("dictitemgrid")[0].store.reload();
        				component.up("#dictitemaddwindow").close();
        			}
        		},
        		failure: function(conn, request, option, eOpts){
        			Ext.MessageBox.alert("请求失败", "服务器繁忙，稍后重试!");
        		}
        	});
        }
    }]
});
