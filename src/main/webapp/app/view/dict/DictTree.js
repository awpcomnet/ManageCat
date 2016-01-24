/**
 * 描述: 字典类型树形结构
 * 作者: 
 */
Ext.define("MIS.view.dict.DictTree", {
    extend: "Ext.tree.Panel",
    alias: "widget.dicttree",

    title: "字典类别",

    root: {
        text: "字典类别",
        expanded: true,
        children: [{
            text: "系统字典",
            expanded: true,
            type: "0",
            hasChildren: true,
            leaf: true
        }, {
            text: "业务字典",
            expanded: true,
            type: "1",
            hasChildren: true,
            leaf: true
        }]
    }
});
