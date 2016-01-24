/**
 * 描述：栏目修改窗口
 * 作者：王航
 */
Ext.define("MIS.view.catalog.CatalogModify", {
	extend: "Ext.window.Window",
	alias: "widget.catalogmodify",
	
	width: 600,
    height: 300,
    closable: true,
    closeAction: "destroy",

    layout: {
        type: "fit"
    },

    renderTo: Ext.ComponentQuery.query("catalogview")[0],
	
	items: [{
        xtype: "form",
        layout: "form",
        padding: "5",
        defaultType: "textfield",
        fieldDefaults: {
            labelAlign: 'right',
            labelWidth: 80,
            msgTarget: 'qtip'
        },
        items: [{
            xtype: "fieldset",
            title: "栏目基本信息",
            layout: "anchor",
            margin: "0 5 0 0",
            defaults: {
                anchor: "100%"
            },
            items: [{
                xtype: "container",
                layout: "hbox",
                margin: '0 0 5 0',
                items: [{
                    xtype: "textfield",
                    fieldLabel: "栏目名称",
                    name: "name",
                    flex: 0.5
                }, {
                    xtype: "textfield",
                    fieldLabel: "英文标识",
                    name: "abbr",
                    flex: 0.5
                }]
            }, {
                xtype: "textfield",
                fieldLabel: "描述",
                name: "description"
            }]
        }, {
            xtype: "fieldset",
            title: "栏目属性",
            layout: "anchor",
            margin: "0 5 0 0",
            defaults: {
                anchor: "100%"
            },
            items: [{
                xtype: "container",
                layout: "hbox",
                margin: "0 0 5 0",
                items: [{
                    xtype: "checkbox",
                    fieldLabel: "是否启用",
                    name: "state"
                }]
            }, {
                xtype: "container",
                layout: "hbox",
                margin: "0 0 5 0",
                items: [{
                    xtype: "textfield",
                    fieldLabel: "图标样式",
                    name: "icon",
                    flex: 0.5
                }, {
                    xtype: "numberfield",
                    fieldLabel: "排序值",
                    name: "orderNum",
                    flex: 0.5
                }]
            }, {
                xtype: "container",
                layout: "hbox",
                margin: "0 0 5 0",
                items: [{
                    xtype: "textfield",
                    fieldLabel: "URL类型",
                    name: "urlType",
                    flex: 0.5
                }, {
                    xtype: "textfield",
                    fieldLabel: "URL地址",
                    name: "url",
                    flex: 0.5
                }]
            }]
        }]
    }],
    
	buttons: [{
		text: "取消",
		handler: function(component){
			component.up("#catalogmodifywindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var cataname = component.up("catalogmodify").down("textfield[name=name]").getValue(),
				cataabbr = component.up("catalogmodify").down("textfield[name=abbr]").getValue(),
				cataurl = component.up("catalogmodify").down("textfield[name=url]").getValue(),
				description = component.up("catalogmodify").down("textfield[name=description]").getValue(),
				cataordernum = component.up("catalogmodify").down("numberfield[name=orderNum]").getValue(),
				cataicon = component.up("catalogmodify").down("textfield[name=icon]").getValue(),
				cataurltype = component.up("catalogmodify").down("textfield[name=urlType]").getValue(),
				catastate = component.up("catalogmodify").down("checkbox[name=state]").getValue()+0;
				cataid = component.up("#catalogmodifywindow").extraData.id;
				
			//console.log("name="+cataname+" abbr="+cataabbr+" url="+cataurl+" description="+description+" orderNum="+cataordernum+" icon="+cataicon+" urlType="+cataurltype+" state="+catastate+" id="+cataid);
			Ext.Ajax.request({
				url: "/catalog/modify",
				params: {
					name: cataname,
					abbr: cataabbr,
					url: cataurl,
					urlType: cataurltype,
					description: description,
					order: cataordernum || 0,
					state: catastate,
					icon: cataicon,
					id: cataid
				},
				success: function(conn, request, option, eOpts){
	        		var result = Ext.JSON.decode(conn.responseText, true);
	        		if(result.resultCode != 0){
	        			Ext.MessageBox.alert("请求失败", "修改栏目失败"+ result.resultMessage);
	        		} else {
	        			Ext.ComponentQuery.query("catalogtree")[0].store.reload();
	        			Ext.ComponentQuery.query("cataloggrid")[0].store.reload();
	        			Ext.ComponentQuery.query("cataloggrid")[0].getView().getSelectionModel().deselectAll();
	        			component.up("catalogmodify").close();
	        		}
	        	},
	        	failure: function(conn, request, option, eOpts){
	        		Ext.MessageBox.alert("请求失败", "服务器繁忙，稍后重试!");
	        	}
			});
				
				console.log(component.up("#catalogmodifywindow").extraData);
		}
		
	}]
});