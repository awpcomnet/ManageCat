/**
 * 售出清单添加（单条）
 */
Ext.define("MIS.view.storage.SelledAdd", {
	extend: "Ext.form.Panel",
	alias: "widget.selledadd",
	
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
		fieldLabel: "售出数量",
        name: "sellNum",
        xtype: "numberfield",
        colspan: 1,
        anchor: "55%",
        minValue: 1,
        editable:true
    }, {
		fieldLabel: "单个售价(￥)",
        name: "sellingPrice",
        xtype: "numberfield",
        colspan: 1,
        anchor: "55%",
        minValue: 0,
        allowBlank: true,
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
		fieldLabel: "涨幅(%)",
        name: "rate",
        xtype: "numberfield",
        colspan: 1,
        anchor: "55%",
        minValue: 0,
        editable:true
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
		text: "推荐售价",
		handler: function(component){
			var selledadd = component.up("selledadd");
			
			var rate = selledadd.down("numberfield[name=rate]").getValue();
			var unitCost = selledadd.down("textfield[name=unitCost]").getValue().trim();
			var sellingPrice = selledadd.down("numberfield[name=sellingPrice]");
			
			if(rate == null || rate == '' || rate == 'null'){
				rate = 1;
			}
			if(unitCost == null || unitCost == '' || unitCost == 'null'){
				Ext.MessageBox.alert("错误提示", "成本价数据非法");
				return;
			}
			sellingPrice.setValue((1+(Number.parseFloat(rate)/100)) * Number.parseFloat(unitCost));
		}
	}, {
		text: "取消",
		handler: function(component){
			component.up("#selledaddwindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var form = component.up("selledadd");
        	var params = form.getForm().getValues();
        	
			Ext.Ajax.request({
				url: "/selled/add",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("添加售出清单失败", "原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("storagegrid")[0].store.reload();
		                Ext.ComponentQuery.query("storagegrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#selledaddwindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});