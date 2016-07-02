/**
 * 品牌修改
 */
Ext.define("MIS.view.brand.BrandModify", {
	extend: "Ext.form.Panel",
	alias: "widget.brandmodify",
	
//	width: 600,
//	height: 185,
//	closable: true,
	closeAction: "destory",
	showFlag: "",
    defaultType: "textfield",
    defaults: {
        anchor: "100%",
        padding: "15 10"
    },
    layout: {
    	type: 'table',
    	columns:2
    },
	
	
	items: [{
		fieldLabel: "品牌名称",
	    name: "brandName",
	    allowBlank: true
    }, {
		fieldLabel: "品牌英文名",
	    name: "brandEname",
	    allowBlank: true
    }, {
		fieldLabel: "所属国家",
        name: "ofOrigin",
        xtype: "combo",
        store: Ext.create("MIS.store.dict.DictQueryStore", {
        	dictcode: "country"
        }),
        mode: "local",
        displayField: 'name',
        valueField: "value",
        allowBlank: true,
        editable:false
	}, {
		fieldLabel: "是否启用",
        name: "isUse",
        xtype: "combo",
        store: Ext.create("MIS.store.dict.DictQueryStore", {
        	dictcode: "onOff"
        }),
        mode: "local",
        displayField: 'name',
        valueField: "value",
        allowBlank: true,
        editable:false
	}, {
	    name: "brandId",
	    readOnly: true,
	    hidden: true
    }],
	
	buttons: [{
		text: "取消",
		handler: function(component){
			component.up("#brandmodifywindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var orderModify = component.up("brandmodify");
			
			var brandName = orderModify.down("textfield[name=brandName]").getValue().trim(),
				brandEname = orderModify.down("textfield[name=brandEname]").getValue().trim(),
				ofOrigin = orderModify.down("combo[name=ofOrigin]").getValue(),
				isUse = orderModify.down("combo[name=isUse]").getValue(),
				brandId = orderModify.down("textfield[name=brandId]").getValue().trim();
			
			
			var params = {
				brandName: brandName,
				brandEname: brandEname,
				ofOrigin: ofOrigin,
				isUse: isUse,
				brandId:brandId
	        };
			
			Ext.Ajax.request({
				url: "/brand/update",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("请求失败", "原因： " + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("brandgrid")[0].store.reload();
		                Ext.ComponentQuery.query("brandgrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#brandmodifywindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});