/**
 * 描述: 用户具体信息展示
 * 作者: 
 */
Ext.define("MIS.view.user.UserDetailsWindow", {
    extend: "Ext.window.Window",
    alias: "widget.userdetailswindow",

    title: "用户详情",
    width: 600,
    height: 205,

    layout: {
        type: "fit"
    },

    actionMode: "", /** 弹框行为模式 */
    modifyModeDetails: null,
    closeAction: "hide",

    items: [{
        xtype: "userdetailsform"
    }],

    buttons: [{
        text: "取消",
        handler: function (component) {
            component.up("userdetailswindow").close();
        }
    }, {
        text: "确认",
        handler: function (component) {
            component.up("userdetailswindow").onSubmit();
        }
    }],

    initComponent: function  () {
        var me = this;

        var addUserInit = function () {
            me.setTitle("新增用户");
            me.onSubmit = function () {
                var form = me.down("userdetailsform").getForm();
                if (form.isValid()) {
                    Ext.Ajax.request({
                        url: "/user/add",
                        params: form.getValues(),
                        success: function (response) {
                            var result = Ext.JSON.decode(response.responseText);
                            if (result && result.resultCode == 0) {
                                Ext.ComponentQuery.query("#id_usergrid")[0].getStore().reload();
                                me.close();
                            } else {
                                Ext.MessageBox.alert("请求失败", !result ? "请求失败, 请稍后重试" : result.resultMessage);
                            }
                        },
                        failure: function () {
                            Ext.MessageBox.alert("请求失败", "Http通信失败");
                        }
                    });
                }
            };
        },
        modifyUserInit = function () {
            me.setTitle("修改用户");

            var loadComboBoxStore = function (component) {
                var combos = component.down("userdetailsform").query("combobox");
                for (var i = combos.length - 1; i >= 0; i--) {
                    var combo = combos[i];
                    combo.getStore().reload();
                }

            }, initUserData = function (component) {
            	var roles = me.modifyModeDetails.raw.roles;
            	var arr = []; 
            	if(roles != '' && roles != null){
            		var roleArr = roles.split(',');
            		for (var c in roleArr) {arr.push(Number(roles[c]))}
            	}
            	
                component.down("textfield[name=username]").setValue(me.modifyModeDetails.raw.username);
                component.down("textfield[name=realname]").setValue(me.modifyModeDetails.raw.realname);
                component.down("combobox[name=state]").setValue(me.modifyModeDetails.raw.state);
                component.down("textfield[name=roles]").setValue(arr);
            };

            //增加事件监听处理器
            Ext.apply(me.listeners, {
                afterRender: function (component) {
                    loadComboBoxStore(component);
                    initUserData(component);
                }
            });

            // 修改用户数据时提交行为代码
            me.onSubmit = function () {
                var form = me.down("userdetailsform").getForm();
                if (form.isValid()) {
                    var params = form.getValues();
                    params.id = this.modifyModeDetails.raw.userId;
                    Ext.Ajax.request({
                        url: "/user/update",
                        params: params,
                        success: function (response) {
                            var result = Ext.JSON.decode(response.responseText);
                            if (result && result.resultCode == 0) {
                                var grid = Ext.ComponentQuery.query("#id_usergrid")[0];
                                grid.getStore().reload();
                                grid.getView().getSelectionModel().clearSelections();
                                me.close();

                            } else {
                                Ext.MessageBox.alert("错误提示", !result ? "请求失败, 稍后重试" : result.resultMessage);
                            }
                        },
                        failure: function (response) {
                            Ext.MessageBox.alert("错误提示", "Http通信错误, 请稍候重试");
                        }
                    });
                }
            };
        },
        showUserInit = function () {};

        /**
         * 根据细节框的MODE初始化组件
         */
        switch (this.actionMode) {
            case "ADD" :
                addUserInit();
                break;
            case "MODIFY" :
                modifyUserInit();
                break;
            case "SHOW" :
                showUserInit();
                break;
            default:
                break;
        }
        
        this.callParent();
    },

    /**
     * 提交按钮事件处理器
     */
    onSubmit: null
});
