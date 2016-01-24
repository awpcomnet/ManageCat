Ext.define('MIS.Application', {
    name: 'MIS',

    extend: 'Ext.app.Application',
    
    requires: [
               "Ext.data.Request",
               "Ext.window.MessageBox",
               
               "MIS.store.dict.DictQueryStore",
               "MIS.common.DictManager"
               
    ],

    views: [
            
    ],

    controllers: [
                  "MIS.controller.mainviewport.MainNavigation",
                  "MIS.controller.catalog.CatalogController",
                  "MIS.controller.order.OrderController",
                  "MIS.controller.dict.DictController"
    ],

    stores: [
        // TODO: add stores here
    ],
    
    init: function () {
        Ext.getBody().mask("Loading...", "splash");
        MIS.common.DictManager.initialize();//初始化数据字典

        // 对Date的扩展，将 Date 转化为指定格式的String   
        // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
        // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
        // 例子：   
        // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
        // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
        Date.prototype.Format = function(fmt)   
        {
          var o = {   
            "M+" : this.getMonth()+1,                 //月份   
            "d+" : this.getDate(),                    //日   
            "h+" : this.getHours(),                   //小时   
            "m+" : this.getMinutes(),                 //分   
            "s+" : this.getSeconds(),                 //秒   
            "q+" : Math.floor((this.getMonth()+3)/3), //季度   
            "S"  : this.getMilliseconds()             //毫秒   
          };   
          if(/(y+)/.test(fmt))   
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
          for(var k in o)   
            if(new RegExp("("+ k +")").test(fmt))   
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
          return fmt;   
        }
        
        
        Ext.override(Ext.data.TreeStore, {
            load : function(options) {
                options = options || {};
                options.params = options.params || {};

                var me = this, node = options.node || me.tree.getRootNode(), root;

                // If there is not a node it means the user hasnt defined a rootnode
                // yet. In this case lets just
                // create one for them.
                if (!node) {
                    node = me.setRootNode( {
                        expanded : true
                    });
                }

                if (me.clearOnLoad) {
                    node.removeAll(false);
                }

                Ext.applyIf(options, {
                    node : node
                });
                options.params[me.nodeParam] = node ? node.getId() : 'root';

                if (node) {
                    node.set('loading', true);
                }
                return me.callParent( [ options ]);
            }
        });
    },

    launch: function () {
    	Ext.getBody().unmask();
    }
});
