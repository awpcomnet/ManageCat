/**
 * 描述：字典项动态过滤
 * 作者：王航
 */
Ext.define("MIS.view.dict.DictFilterSearch", {
	extend: "Ext.form.Panel",
	alias: "widget.dictFilterPanel",
	
	defaultType: "textfield",
	defaults: {
		anchor: '100%'
	},
	
	items: [{
		margin: "6 5",
		xtype: "textfield",
		listeners : {
	       change : function(field,newValue,oldValue){
	    	   
		       // 找到store
	           var dictStore = Ext.ComponentQuery.query('dictgrid')[0].getStore();
	           	
	           //对store 进行过滤
	           dictStore.filterBy(function(record){
	        	   var name = record.raw.name,
	        	       code = record.raw.code;
	        	   //如果输入框为空，直接放回所有记录
		    	   if(newValue == '' || newValue == null)
		    		   return true;
		    	   
	        	   if((name.indexOf(newValue) >= 0) || (code.indexOf(newValue) >= 0)){
	        		   return true;
	        	   }
	        	   return false;
	           });
	       }
		}
	}]
});