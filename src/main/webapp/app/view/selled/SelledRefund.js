/**
 * 售出清单添加补损信息
 */
Ext.define("MIS.view.selled.SelledRefund", {
	extend: "Ext.form.Panel",
	alias: "widget.selledrefund",
	
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
		fieldLabel: "补损金额(￥)",
        name: "refund",
        xtype: "numberfield",
        colspan: 1,
        anchor: "55%",
        allowBlank: true,
        //补损金额允许添加负值
        //minValue: 0.0,
        decimalPrecision: 2,
        editable:true,
        id: 'refund-tip',
        listeners : { 
	    	afterrender: function () {
	    		$("#refund-tip-inputEl").poshytip({
	                content: "补损金额：赔付客户金额为正值；商家或转运公司赔偿金额为负值",
	                offsetX: 50,
	            	offsetY: 50,
	            	allowTipHover: false,
	                showTimeout: 1000
	            });
	    	}
        } 
    }, {
		xtype: 'datefield',
		fieldLabel: "售出/损坏时间",
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
			component.up("#selledrefundwindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var form = component.up("selledrefund");
        	var params = form.getForm().getValues();
			
			Ext.Ajax.request({
				url: "/selled/refund",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("修改售出记录失败", "原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("selledgrid")[0].store.reload();
		                Ext.ComponentQuery.query("selledgrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#selledrefundwindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
    
});