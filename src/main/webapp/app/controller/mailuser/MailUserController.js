/**
 * 邮件用户控制器
 */
Ext.define("MIS.controller.mailuser.MailUserController", {
	extend: "Ext.app.Controller",
	
	models : [ 
	    "MIS.model.mailuser.MailUser" 
	],

	stores : [ 
	    "MIS.store.mailuser.MailUserStore"
	],
	
	views: [
	    "MIS.view.mailuser.MailUserView",
	    "MIS.view.mailuser.MailUserGrid",
	    "MIS.view.mailuser.MailUserAdd"
	    
	],
	
	init: function(){
		this.control({
			"mailusergrid": {
				render: this.onMailUserGridRender
			}
		});
	},
	
	onMailUserGridRender: function(component, options){
		component.getStore().load({
			callback: function(records, operation, success){
				var result = Ext.JSON.decode(operation.response.responseText);
				if(result.resultCode != 0){
					 Ext.MessageBox.alert("错误提示", "错误原因：" + result.resultMessage);
				}
	        }
			
		});
	}
	
});