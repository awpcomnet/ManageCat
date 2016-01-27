/**
 * 子订单合并
 */
Ext.define("MIS.view.order.SubOrderMerge", {
	extend: "Ext.form.Panel",
	alias: "widget.subordermerge",
	
//	width: 600,
//	height: 185,
//	closable: true,
	closeAction: "destory",
	showFlag: "",
    defaultType: "textfield",
    defaults: {
        anchor: "100%",
        padding: "10 10"
    },
    layout: {
    	type: 'table',
    	columns:2
    },
	
	
	items: [{
		fieldLabel: "主订单编号",
	    name: "superOrderId",
	    readOnly: true,
	    allowBlank: true
    }, {
		fieldLabel: "订单状态",
        name: "curState",
        xtype: "combobox",
        store: Ext.create("MIS.store.dict.DictQueryStore", {
        	dictcode: "subOrderState"
        }),
        mode: "local",
        displayField: 'name',
        valueField: "value",
        allowBlank: true,
        readOnly: true,
        editable:false
	}, {
		fieldLabel: "品牌名称",
        name: "brandId",
        xtype: "combobox",
        store: Ext.create("MIS.store.brand.BrandStore"),
        listeners: {
            select: function (combobox, record) {
                record = parseInt(combobox.getValue());
            	var seriesId = combobox.up("form").down("combobox[name='seriesId']");
            	seriesId.setValue("");
            	var seriesIdStore = seriesId.getStore();
            	seriesIdStore.proxy.extraParams.ofOrigin = record;
            	seriesIdStore.proxy.extraParams.isUse = 1;
            	seriesIdStore.reload();
            }
        },
        mode: "local",
        displayField: 'brandName',
        valueField: "brandId",
        allowBlank: true,
        readOnly: true,
        editable:false
	}, {
		fieldLabel: "系列名称",
        name: "seriesId",
        xtype: "combobox",
        store: Ext.create("MIS.store.series.SeriesStore"),
        listeners: {
            select: function (combobox, record) {
                record = parseInt(combobox.getValue());
            	var singleId = combobox.up("form").down("combobox[name='singleId']");
            	singleId.setValue("");
            	var singleIdStore = singleId.getStore();
            	singleIdStore.proxy.extraParams.ofOrigin = record;
            	singleIdStore.proxy.extraParams.isUse = 1;
            	singleIdStore.reload();
            }
        },
        mode: "local",
        displayField: 'seriesName',
        valueField: "seriesId",
        allowBlank: true,
        readOnly: true,
        editable:false
    }, {
		fieldLabel: "单品名称",
        name: "singleId",
        xtype: "combobox",
        store: Ext.create("MIS.store.singleproduct.SingleproductStore"),
        mode: "local",
        displayField: 'singleName',
        valueField: "singleId",
        allowBlank: true,
        readOnly: true,
        editable:false
    }, {
		fieldLabel: "合并后下单单价",
	    name: "orderPrice",
	    listeners : {
	       change : function(field,newValue,oldValue){
	    	   var transferPrice = Ext.ComponentQuery.query("subordermerge textfield[name=transferPrice]")[0].getValue();
	    	   var costPrice = Ext.ComponentQuery.query("subordermerge textfield[name=costPrice]")[0];
	    	   if(newValue == '')
	    		   newValue = 0;
	    	   if(transferPrice == '')
	    		   transferPrice = 0;
	    	   
	    	   costPrice.setValue(Number.parseFloat(transferPrice)+Number.parseFloat(newValue));
	       }
		},
	    allowBlank: true,
	    editable:true
    }, {
		fieldLabel: "合并后运费(平均)",
	    name: "transferPrice",
	    listeners : {
		       change : function(field,newValue,oldValue){
		    	   var orderPrice = Ext.ComponentQuery.query("subordermerge textfield[name=orderPrice]")[0].getValue();
		    	   var costPrice = Ext.ComponentQuery.query("subordermerge textfield[name=costPrice]")[0];
		    	   if(newValue == '')
		    		   newValue = 0;
		    	   if(orderPrice == '')
		    		   orderPrice = 0;
		    	   
		    	   costPrice.setValue(Number.parseFloat(orderPrice)+Number.parseFloat(newValue));
		       }
			},
	    allowBlank: true,
	    editable:true
    }, {
		fieldLabel: "合并后售价(单个)",
	    name: "sellingPrice",
	    allowBlank: true,
	    editable:true
    }, {
		fieldLabel: "合并后成本价(单个)",
	    name: "costPrice",
	    listeners : {
		       change : function(field,newValue,oldValue){
		    	   var orderPrice = Ext.ComponentQuery.query("subordermerge textfield[name=orderPrice]")[0].getValue();
		    	   var transferPrice = Ext.ComponentQuery.query("subordermerge textfield[name=transferPrice]")[0];
		    	   if(newValue == '')
		    		   newValue = 0;
		    	   if(orderPrice == '')
		    		   orderPrice = 0;
		    	   
		    	   transferPrice.setValue(Number.parseFloat(newValue)-Number.parseFloat(orderPrice));
		       }
		},
	    allowBlank: true,
	    editable:true
    }, {
	    name: "subOrderIds",
	    readOnly: true,
	    hidden: true
    }],
	
	buttons: [{
		text: "取消",
		handler: function(component){
			component.up("#subordermergewindow").close();
		}
	}, {
		text: "确认",
		handler: function(component){
			var subOrderMerge = component.up("subordermerge");
			//var extraData = component.up("subordermerge").extraData;
			
			debugger;
			var subOrderIds = subOrderMerge.down("textfield[name=subOrderIds]").getValue().trim(),
				curState = subOrderMerge.down("combobox[name=curState]").getValue(),
				orderPrice = subOrderMerge.down("textfield[name=orderPrice]").getValue().trim(),
				transferPrice = subOrderMerge.down("textfield[name=transferPrice]").getValue().trim(),
				sellingPrice = subOrderMerge.down("textfield[name=sellingPrice]").getValue().trim(),
				costPrice = subOrderMerge.down("textfield[name=costPrice]").getValue().trim();
			
			var params = {
					subOrderIds: subOrderIds,
					curState: curState,
					orderPrice: orderPrice,
					transferPrice: transferPrice,
					sellingPrice: sellingPrice,
					costPrice: costPrice
					
	        };
			
			Ext.Ajax.request({
				url: "/subOrder/merge",
				params: params,
				success: function(conn, request, option, eOpts){
					var result = Ext.JSON.decode(conn.responseText, true);
					if(result.resultCode != 0){
						Ext.MessageBox.alert("合并子订单失败, 原因:" + result.resultMessage);
					} else {
		                Ext.ComponentQuery.query("subordergrid")[0].store.reload();
		                Ext.ComponentQuery.query("subordergrid")[0].getView().getSelectionModel().deselectAll();
		                component.up("#subordermergewindow").close();
					}
				},
				failure: function (conn, request, option, eOpts) {
                    Ext.MessageBox.alert("服务器繁忙, 稍后重试!");
                }
				
			});
		}
	}]
	
});