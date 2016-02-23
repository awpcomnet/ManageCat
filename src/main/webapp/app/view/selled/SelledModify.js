/**
 * 售出清单修改
 */
Ext.define("MIS.view.selled.SelledModify", {
	extend: "Ext.form.Panel",
	alias: "widget.selledmodify",
	
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
    	columns:2
    },
	
	
	items: [{
		fieldLabel: "售出价格(￥)",
        name: "sellingPrice",
        xtype: "numberfield",
        colspan: 1,
        anchor: "55%",
        allowBlank: true,
        decimalPrecision: 2,
        editable:true
    }, {
		xtype: 'datefield',
		fieldLabel: "售出时间",
		name: "sellTime",
		anchor: "55%",
        format: 'Ymd',
        value: new Date(),
        editable:false
	}, {
		fieldLabel: "备注",
	    name: "remark",
	    xtype: "textarea",
	    colspan: 2,
        width: 540,
	    editable:true
    }, {
        name: "id",
        hidden: true
    }],
	
	buttons: [{
		text: "取消",
		handler: function(component){
			component.up("#selledmodifywindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var form = component.up("selledmodify");
        	var params = form.getForm().getValues();
			
			Ext.Ajax.request({
				url: "/selled/modify",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("修改售出记录失败", "原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("selledgrid")[0].store.reload();
		                Ext.ComponentQuery.query("selledgrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#selledmodifywindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});