/**
 * 描述: 字典管理控制器
 * 作者: 
 */
Ext.define("MIS.controller.dict.DictController", {
    extend: "Ext.app.Controller",

    models: [
        "MIS.model.dict.Dict",
        "MIS.model.dict.DictItem"
    ],

    stores: [
        "MIS.store.dict.DictStore",
        "MIS.store.dict.DictItemStore"
    ],

    views: [
        "MIS.view.dict.DictView",
        "MIS.view.dict.DictGrid",
        "MIS.view.dict.DictTree",
        "MIS.view.dict.DictItemGrid",
        "MIS.view.dict.DictAdd",
        "MIS.view.dict.DictItemAdd",
        "MIS.view.dict.DictItemModify",
        "MIS.view.dict.DictModify",
        "MIS.view.dict.DictItemSearch"
    ],

    init: function () {
        this.control({
            "dictview dicttree": {
                itemclick: this.onTreeItemClick
            },

            "dictview dictgrid": {
                render: this.onDictGridRender,
                itemclick: this.onDictGridItemClick
            }
        });
    },

    onTreeItemClick: function (component, record, item, index, event, eOpts) {
        var grid = null;
        if (record.raw.hasChildren) {
            grid = component.up("dictview").down("dictgrid");
            grid.dictType = record.raw.type;

            grid.store.proxy.extraParams.type = record.raw.type;
            grid.store.reload();

        }
    },

    onDictGridRender: function (component, record, item, index, event, eOpts) {
        component.store.load();
    },

    /**
     * 字典选中
     */
    onDictGridItemClick: function (component, record, item, index, event, eOpts) {
        var id = record.raw.id,
            dictitemgrid = component.up("dictview").down("dictitemgrid");

        dictitemgrid.dictType = id;
        dictitemgrid.store.proxy.extraParams.typeId = id;
        dictitemgrid.store.reload();
    }
});
