/**
 * 密码重置
 */
Ext.define("MIS.view.user.ResetPassWord", {
	extend: "Ext.form.Panel",
	alias: "widget.resetpassword",
	
//	width: 600,
//	height: 185,
//	closable: true,
	closeAction: "destory",
	showFlag: "",
    defaultType: "textfield",
    defaults: {
        anchor: "100%",
        padding: "14 13"
    },
    layout: {
    	type: 'table',
    	columns:1
    },
	
	
	items: [{
		fieldLabel: "用户名",
        name: "username",
        xtype: "textfield",
        colspan: 1,
        anchor: "55%",
        width: 300,
        disabled: true
    }, {
		fieldLabel: "*密码",
	    name: "password",
	    xtype: "textfield",
	    inputType:'password',
	    colspan: 1,
        width: 300,
	    editable:true
    }, {
		fieldLabel: "*确认密码",
	    name: "repeatPassword",
	    xtype: "textfield",
	    inputType:'password',
	    colspan: 1,
        width: 300,
	    editable:true
    }, {
        name: "userId",
        hidden: true
    }],
	
	buttons: [{
		text: "取消",
		handler: function(component){
			component.up("#resetpasswordwindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			
			var userId = component.up("resetpassword").down("textfield[name=userId]").getValue();
			var password = component.up("resetpassword").down("textfield[name=password]").getValue();
			var repeatPassword = component.up("resetpassword").down("textfield[name=repeatPassword]").getValue();
			
			Ext.Ajax.request({
				url: "/user/reset",
				params: {
					userId: userId,
					password: password,
					repeatPassword: repeatPassword
				},
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("重置密码失败", "原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("usergrid")[0].store.reload();
		                Ext.ComponentQuery.query("usergrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#resetpasswordwindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});