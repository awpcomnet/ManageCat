/**
 * 邮件用户添加（单条）
 */
Ext.define("MIS.view.mailuser.MailUserAdd", {
	extend: "Ext.form.Panel",
	alias: "widget.mailuseradd",
	
//	width: 600,
//	height: 185,
//	closable: true,
	closeAction: "destory",
	showFlag: "",
    defaultType: "textfield",
    defaults: {
        anchor: "100%",
        padding: "15 15"
    },
    layout: {
    	type: 'table',
    	columns:2
    },
	
	
	items: [{
		fieldLabel: "邮箱地址",
        name: "username",
        xtype: "textfield",
        colspan: 2,
        anchor: "55%",
        width: 300,
        editable:false
    }, {
		fieldLabel: "备注",
        name: "arg0",
        xtype: "textfield",
        colspan: 2,
        anchor: "55%",
        width: 300,
        editable:false
    }, {
        name: "id",
        hidden: true
    }],
	
	buttons: [{
		text: "取消",
		handler: function(component){
			component.up("#mailuseraddwindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var form = component.up("mailuseradd");
        	var params = form.getForm().getValues();
        	
			Ext.Ajax.request({
				url: "/mailuser/add",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("添加邮件用户失败", "原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("mailusergrid")[0].store.reload();
		                Ext.ComponentQuery.query("mailusergrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#mailuseraddwindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});