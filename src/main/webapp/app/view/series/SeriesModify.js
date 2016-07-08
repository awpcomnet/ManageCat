/**
 * 系列修改
 */
Ext.define("MIS.view.series.SeriesModify", {
	extend: "Ext.form.Panel",
	alias: "widget.seriesmodify",
	
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
		fieldLabel: "所属品牌",
        name: "ofOrigin",
        xtype: "combo",
        store: Ext.create("MIS.store.brand.BrandAllStore"),
        listeners: {
            change : function(field,newValue,oldValue){
                // 找到store
                var ofOriginStore = Ext.ComponentQuery.query('seriesmodify')[0].down("combo[name=ofOrigin]").getStore();

                //对store 进行过滤
                ofOriginStore.filterBy(function(record){
                    var name = record.raw.brandEname,
                        code = record.raw.brandId;
                    //如果输入框为空，直接放回所有记录
                    if(newValue == '' || newValue == null)
                        return true;

                    if(name.indexOf(newValue) >= 0){
                        return true;
                    }
                    return false;
                });
            }
        },
        mode: "local",
        displayField: 'brandEname',
        valueField: "brandId",
        allowBlank: true,
        editable:true
	}, {
		fieldLabel: "系列名称",
	    name: "seriesName",
	    allowBlank: true
    }, {
		fieldLabel: "系列英文名",
	    name: "seriesEname",
	    allowBlank: true
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
	    name: "seriesId",
	    readOnly: true,
	    hidden: true
    }],
	
	buttons: [{
		text: "取消",
		handler: function(component){
			component.up("#seriesmodifywindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var orderModify = component.up("seriesmodify");
			
			var seriesName = orderModify.down("textfield[name=seriesName]").getValue().trim(),
				seriesEname = orderModify.down("textfield[name=seriesEname]").getValue().trim(),
				ofOrigin = orderModify.down("combo[name=ofOrigin]").getValue(),
				isUse = orderModify.down("combo[name=isUse]").getValue(),
				seriesId = orderModify.down("textfield[name=seriesId]").getValue().trim();
			
			
			var params = {
				seriesName: seriesName,
				seriesEname: seriesEname,
				ofOrigin: ofOrigin,
				isUse: isUse,
				seriesId:seriesId
	        };
			
			Ext.Ajax.request({
				url: "/Series/update",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("请求失败", "原因：" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("seriesgrid")[0].store.reload();
		                Ext.ComponentQuery.query("seriesgrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#seriesmodifywindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});