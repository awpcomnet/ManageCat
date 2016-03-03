/**
 * 描述: 栏目结构控制器
 * 作者: 
 */
Ext.define("MIS.controller.catalog.CatalogController", {
    extend: "Ext.app.Controller",

    models: [
        "MIS.model.catalog.Catalog"
    ],

    stores: [
        "MIS.store.catalog.CatalogStore",
        "MIS.store.catalog.CatalogInfoStore"
    ],

    views: [
        "MIS.view.catalog.CatalogView",
        "MIS.view.catalog.CatalogTree",
        "MIS.view.catalog.CatalogData",
        "MIS.view.catalog.CatalogGrid",
        "MIS.view.catalog.CatalogEdit",
        "MIS.view.catalog.CatalogAdd",
        "MIS.view.catalog.CatalogModify",
        "MIS.view.catalog.CatalogSearch"
    ],

    init: function () {
        this.control({
            "catalogview catalogform": {
                render: this.onCatalogFormRender 
            },
            "catalogview catalogtree": {
                itemclick: this.onItemClick
            },
            "catalogview cataloggrid": {
                render: this.onGridRender
            }
        });
    },

    /**
     * 栏目数据表单的初始化
     */
    onCatalogFormRender: function () {
    },

    /**
     * 栏目数据Grid
     */
    onGridRender: function (component, record, item, index, event, eOpts) {
        //console.log(component);
        component.store.load({
			callback: function(records, operation, success){
				var result = Ext.JSON.decode(operation.response.responseText);
				if(result.resultCode != 0){
					 Ext.MessageBox.alert("错误提示", "错误原因：" + result.resultMessage);
				}
	        }
			
		});
    },

    /**
     * 栏目树单击事件处理
     */
    onItemClick: function (component, record, item, index, event, eOpts) {
        var isLeaf = !!record.raw.leaf,
            id = record.raw.id,
            grid = null;

        if (!isLeaf) {
            grid = component.up("catalogview").down("cataloggrid");
            grid.store.proxy.extraParams.parentId = id;
            grid.store.reload({
    			callback: function(records, operation, success){
    				var result = Ext.JSON.decode(operation.response.responseText);
    				if(result.resultCode != 0){
    					 Ext.MessageBox.alert("错误提示", "错误原因：" + result.resultMessage);
    				}
    	        }
    			
    		});
            
            grid.selectedId = record.raw.id;
            grid.selectedText = record.raw.text;
        }
        

    }
});
