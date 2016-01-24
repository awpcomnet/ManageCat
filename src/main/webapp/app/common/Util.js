/**
 * Created by jiangbin on 2015/11/3.
 */
/**********************
 MIS.common.util  拓展公用函数
 ***********************/
Ext.define("MIS.common.Util", {
  singleton: true,
  requires: [
    "Ext.Ajax",
    "Ext.JSON",
    "Ext.getStore"
  ],
  /*
   *  ajax 请求操作
   * */
  ajaxExtCall: function (url, obj_param,async ,successCallback, errorCallback) {
    Ext.Ajax.request({
      url: url,
      params: obj_param,
      async:async,//ASYNC 是否异步( TRUE 异步 , FALSE 同步)
      success: function (conn, request, options, eOpts) {
        var result = Ext.JSON.decode(conn.responseText, true);
        if (successCallback)successCallback(result.results, result.resultCode,result.resultMessage);
      },
      failure: function (conn, request, options, eOpts) {
        var result = Ext.JSON.decode(conn.responseText, true);
        if (errorCallback) errorCallback(result.resultCode,result.resultMessage);
      }

    });
  },
  convert2TreeData: function (source) {
    var tmp = {}, parent, n;
    for (n in source) {
      var item = source[n];
      if (item.id == item.parentId) {
        parent = item.id;
      }
      if (!tmp[item.id]) {
        tmp[item.id] = {};
      }
      tmp[item.id].name = item.name;
      tmp[item.id].id = item.id;
      tmp[item.id].parentId = item.parentId;
      var j = 0;//判断
      for (var i = 0; i < source.length; i++) {
        if (tmp[item.id].id != source[i].parentId) {
          j++;
          continue;
        }
      }
      if (j == source.length) {
        tmp[item.id].leaf = true;
      } else {
        tmp[item.id].expanded = true;
      }
      if (!("children" in tmp[item.id]))tmp[item.id].children = [];
      if (item.id != item.parentId) {
        if (tmp[item.parentId]) {
          tmp[item.parentId].children.push(tmp[item.id]);
        }
        else {
          tmp[item.parentId] = {children: [tmp[item.id]]};
        }
      }
    }
    return {children: [tmp[parent]]};
  },
  /*
   * 数字转数组
   * */
  numToArray: function (numValue) {
    var numString = String(numValue);
    var array = [];
    for (var i = 0; i < numString.length; i++) {
      var temp = numString.substring(i, i + 1);
      array.push(parseInt(temp));
    }
    return array;
  },
  /*
  * params obj
  * */
  refreshStoreDataByParams:function(storeId,params){
    var store= Ext.getStore(storeId);
    store.load({
      params:params
    });
  }
});