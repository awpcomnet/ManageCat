/**
 * 描述：偏好模型
 * 作者：王航
 */
Ext.define("MIS.model.habit.Habit", {
	extend: "Ext.data.Model",
	
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "userId",
		type: "int"
	}, {
		name: "type",
		type: "string"
	}, {
		name: "habitKey",
		type: "string"
	}, {
		name: "habitValue",
		type: "string"
	}]
});

