/**
 * 描述: 管理字典Store
 * 作者: 
 */
Ext.define("MIS.common.DictManager", {
    singleton:true,

    dicts: ["foreignState", "transfer", "affirmState", "subOrderState", "unitDict", "country", "onOff", "sellandback",
            "transferCompany", "orderAddr", "orderStatus", "user.state", "currency", "qualityGuaranteePeriod", "periodOfValidityFilter"],

    /**
     * 缓存字典Store, 防止反复创建字典实例
     */
    dictStoreCache: {},
    /**
     * 缓存字典数据
     */
    dictCache: {},
    /**
     * 初始化字典项
     */
    initialize: function () {
      for (var i = this.dicts.length - 1; i >= 0; i--) {
        this.getDictStore(this.dicts[i]);
      }
    },
    /**
     * 获得字典Store
     */
    getDictStore: function (dictName) {
      var storeCache = this.dictStoreCache,
          dictCache = this.dictCache;
      if (storeCache[dictName]) {
        return storeCache[dictName];
      } else {
        var store = Ext.create("MIS.store.dict.DictQueryStore", {
          dictcode: dictName
        });
        storeCache[dictName] = store;
        store.load({
          callback: function (records) {
            dictCache[dictName] = [];
            if (records && records.length > 0) {
              for (var i = records.length - 1; i >= 0; i--) {
                var record = records[i];
                dictCache[dictName].push({
                  name: record.raw.name,
                  value: record.raw.value
                });
              }
              ;
            }
            this.loadDict = true;
          }
        });
        return store;
      }
    },
    /**
     * 获得字典值对应的名字
     */
    getDictItemName: function (dictName, dictItemValue) {
      var store = this.getDictStore(dictName),
          dictItemName = dictItemValue;
      if (dictItemName == '') {
        dictItemName = '';
      } else {
        dictItemName = "字典未定义[" + dictItemValue + "]";
      }
      var dict = this.dictCache[dictName];
      if (!dict) {
        this.getDictStore(dictName);
        dict = [];
      }
      for (var i = dict.length - 1; i >= 0; i--) {
        var item = dict[i];
        if (dictItemValue == item.value) {
          dictItemName = item.name;
        }
      }
      return dictItemName;
    },
    sleep: function (d) {
      for (var t = Date.now(); Date.now() - t <= d;);
    }
});
