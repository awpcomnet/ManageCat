/**
 * 描述: 增加字典界面
 * 作者: 
 */
Ext.define("MIS.view.dict.DictAdd", {
    extend: "Ext.form.Panel",
    alias: "widget.dictadd",

    defaultType: "textfield",
    defaults: {
        anchor: "100%",
        padding: "5 5"
    },

    items: [{
        fieldLabel: "字典名",
        name: "name",
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
            component.up("#dictaddwindow").close();
        }
    }, {
        text: "新增",
        handler: function (component) {
            var dictname = component.up("dictadd").down("textfield[name=name]").getValue(),
                dictcode = component.up("dictadd").down("textfield[name=code]").getValue(),
                description = component.up("dictadd").down("textfield[name=description]").getValue(),
                dictType = Ext.ComponentQuery.query("dictgrid")[0].dictType;

            Ext.Ajax.request({
                url: "/dict/add",
                params: {
                    type: dictType,
                    name: dictname,
                    code: dictcode,
                    description: description
                },
                success: function (conn, request, option, eOpts) {
                    var result = Ext.JSON.decode(conn.responseText, true);
                    if (result.resultCode != 0) {
                        Ext.MessageBox.alert("请求失败", "创建字典失败 " + result.resultMessage);
                    } else {
                        Ext.ComponentQuery.query("dictgrid")[0].store.reload();
                        component.up("#dictaddwindow").close();
                    }
                },
                failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("请求失败", "服务器繁忙, 稍后重试!");
                }
            });
        }
    }]
});
