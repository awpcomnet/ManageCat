/**
 * 描述：系列数据仓库(所有)
 * 作者：王航
 */
Ext.define("MIS.store.series.SeriesAllStore", {
	extend: "Ext.data.Store",
	
	model: "MIS.model.series.Series",
	
	proxy: {
		type: "ajax",
		url: "/Series/queryAll",
		reader: {
			type: "json",
			root: "results"
		},
        extraParams: {
        	seriesName: "",
        	seriesEname: "",
        	seriesId:"",
        	isUse: "1",
        	ofOrigin: "",
        	startTime: "",
        	endTime: ""
        }
	}
	
});