/**
 * 描述: 导航面板条目根节点
 * 作者: 
 */
Ext.define("MIS.model.nav.Root", {
	extend: "Ext.data.Model",

	uses: [
		"MIS.model.nav.Item"
	],

	idProperty: "id",

	fields: [{
		name: "id",
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

	hasMany: {
		model: "MIS.model.nav.Item",
		foreignKey: "parentId",
		name: "subCatalogs"
	}
});
