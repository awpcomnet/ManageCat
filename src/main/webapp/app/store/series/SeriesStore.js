/**
 * 描述：系列数据仓库
 * 作者：王航
 */
Ext.define("MIS.store.series.SeriesStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.series.Series",
	
	proxy: {
		type: "ajax",
		url: "/Series/query",
		reader: {
			type: "json",
			root: "results",
			totalProperty: "meta.totalRecord"
		},
        extraParams: {
        	seriesId:"",
        	isUse: "",
        	ofOrigin: "",
        	startTime: "",
        	endTime: ""
        }
	}
	
});