/**
 * 子订单详情
 */
Ext.define("MIS.view.order.SubOrderDetail", {
	extend: "Ext.form.Panel",
	alias: "widget.suborderdetail",
	
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
    	columns:3
    },
	
	
	items: [{
		fieldLabel: "主订单编号",
	    name: "superOrderId",
	    readOnly: true,
	    allowBlank: true
    }, {
		fieldLabel: "子订单编号",
	    name: "suborderId",
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
            	var seriesIdStore = singleId.getStore();
            	seriesIdStore.proxy.extraParams.ofOrigin = record;
            	seriesIdStore.reload();
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
		fieldLabel: "数量",
        name: "num",
        xtype: "numberfield",
        minValue: 1, 
        allowBlank: true,
        readOnly: true,
        editable:true
    }, {
		fieldLabel: "下单单价",
	    name: "orderPrice",
	    listeners : {
	       change : function(field,newValue,oldValue){
	    	   var transferPrice = Ext.ComponentQuery.query("suborderdetail textfield[name=transferPrice]")[0].getValue();
	    	   var costPrice = Ext.ComponentQuery.query("suborderdetail textfield[name=costPrice]")[0];
	    	   if(newValue == '')
	    		   newValue = 0;
	    	   if(transferPrice == '')
	    		   transferPrice = 0;
	    	   
	    	   costPrice.setValue(Number.parseFloat(transferPrice)+Number.parseFloat(newValue));
	       }
		},
	    allowBlank: true,
	    readOnly: true,
	    editable:true
    }, {
		fieldLabel: "运费(平均)",
	    name: "transferPrice",
	    listeners : {
		       change : function(field,newValue,oldValue){
		    	   var orderPrice = Ext.ComponentQuery.query("suborderdetail textfield[name=orderPrice]")[0].getValue();
		    	   var costPrice = Ext.ComponentQuery.query("suborderdetail textfield[name=costPrice]")[0];
		    	   if(newValue == '')
		    		   newValue = 0;
		    	   if(orderPrice == '')
		    		   orderPrice = 0;
		    	   
		    	   costPrice.setValue(Number.parseFloat(orderPrice)+Number.parseFloat(newValue));
		       }
			},
	    allowBlank: true,
	    readOnly: true,
	    editable:true
    }, {
		fieldLabel: "售价(单个)",
	    name: "sellingPrice",
	    allowBlank: true,
	    readOnly: true,
	    editable:true
    }, {
		fieldLabel: "成本价(单个)",
	    name: "costPrice",
	    listeners : {
		       change : function(field,newValue,oldValue){
		    	   var orderPrice = Ext.ComponentQuery.query("suborderdetail textfield[name=orderPrice]")[0].getValue();
		    	   var transferPrice = Ext.ComponentQuery.query("suborderdetail textfield[name=transferPrice]")[0];
		    	   if(newValue == '')
		    		   newValue = 0;
		    	   if(orderPrice == '')
		    		   orderPrice = 0;
		    	   
		    	   transferPrice.setValue(Number.parseFloat(newValue)-Number.parseFloat(orderPrice));
		       }
		},
	    allowBlank: true,
	    readOnly: true,
	    editable:true
    }, {
		fieldLabel: "下单总价",
	    name: "sumOrderPrice",
	    readOnly: true,
	    allowBlank: true
    }, {
		fieldLabel: "运费总价",
	    name: "sumTransferPrice",
	    readOnly: true,
	    allowBlank: true
    }, {
		fieldLabel: "成本总价",
	    name: "sumCostPrice",
	    readOnly: true,
	    allowBlank: true
    }, {
		fieldLabel: "售价总价",
	    name: "sumSellingPrice",
	    readOnly: true,
	    allowBlank: true
    }, {
		fieldLabel: "修改时间",
	    name: "updateDateFormat",
	    colspan: 3,
	    width: 270,
	    readOnly: true,
	    allowBlank: true
    }, {
		fieldLabel: "创建时间",
	    name: "createDateFormat",
	    colspan: 3,
	    width: 270,
	    readOnly: true,
	    allowBlank: true
    }],
	
	buttons: [{
		text: "关闭",
		handler: function(component){
			component.up("#suborderdetailwindow").close();
		}
	}]
	
});