/**
 * 售出清单添加已损坏记录
 */
Ext.define("MIS.view.storage.DestroyAdd", {
	extend: "Ext.form.Panel",
	alias: "widget.destroyadd",
	
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
		fieldLabel: "实际成本(￥)",
        name: "unitCost",
        xtype: "textfield",
        colspan: 1,
        anchor: "55%",
        readOnly: true
    }, {
		fieldLabel: "剩余库存",
        name: "residueNum",
        xtype: "textfield",
        colspan: 1,
        anchor: "55%",
        readOnly: true
    }, {
		fieldLabel: "损坏数量",
        name: "sellNum",
        xtype: "numberfield",
        colspan: 1,
        anchor: "55%",
        minValue: 1,
        editable:true
    }, {
		xtype: 'datefield',
		fieldLabel: "提交时间",
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
        name: "storeId",
        hidden: true
    }],
	
	buttons: [{
		text: "提示",
		handler: function(component){
			Ext.MessageBox.alert("提示", "[损坏]仍然添加至售出清单，售出价格默认为0.0");
			
		}
	}, {
		text: "取消",
		handler: function(component){
			component.up("#destroywindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var form = component.up("destroyadd");
        	var params = form.getForm().getValues();
        	
			Ext.Ajax.request({
				url: "/selled/destroy",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("添加损坏记录失败", "原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("storagegrid")[0].store.reload();
		                Ext.ComponentQuery.query("storagegrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#destroywindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});