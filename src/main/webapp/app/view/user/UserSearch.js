/**
 * 描述: 用户搜索面板
 * 作者: 
 */
Ext.define("MIS.view.user.UserSearch", {
    extend: "Ext.form.Panel",
    alias: "widget.usersearch",

    layout: {
        type: "anchor"
    },

    style: "border-bottom: 4px solid #3892d3",

    showState: false,

    defaultType: "textfield",
    defaults: {
        anchor: "100%",
        labelWidth: 60,
        padding: "5 5 5 5"
    },
    items: [{
        fieldLabel: "用户名",
        name: "username",
        allowBlank: true
    }],

    buttons: [{
        text: "重置",
        handler: function () {
            this.up("form").getForm().reset();
        }
    }, {
        text: "搜索",
        handler: function () {
            console.log("搜索数据!");
        }
    }]
});
