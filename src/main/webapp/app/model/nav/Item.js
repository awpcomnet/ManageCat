/**
 * 描述: 导航面板条目数据模型
 * 作者: 
 */
Ext.define("MIS.model.nav.Item", {
	extend: "Ext.data.Model",

	uses: [
		"MIS.model.nav.Root"
	],

	idProperty: "id",

	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "parentId",
		type: "int"	
	}, {
		name: "abbr",
		type: "string"
	}, {
		name: "name",
		type: "string"
	}, {
		name: "url",
		type: "string"
	}, {
		name: "description",
		type: "string"
	}, {
		name: "oder",
		type: "int"
	}, {
		name: "state",
		type: "int"
	}, {
		name: "icon",
		type: "string"
	}],

	belongsTo: {
		model: "MIS.model.nav.Root",
		foreignKey: "parentId"
	}
});

