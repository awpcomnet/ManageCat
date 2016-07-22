Ext.define('MIS.Application', {
    name: 'MIS',

    extend: 'Ext.app.Application',
    
    requires: [
               "Ext.data.Request",
               "Ext.window.MessageBox",
               
               "MIS.store.dict.DictQueryStore",
               "MIS.common.DictManager",
               "MIS.common.CurrentUser",
               "MIS.common.MonthField"
               
    ],

    views: [
            
    ],

    controllers: [
                  "MIS.controller.mainviewport.MainNavigation",
                  "MIS.controller.catalog.CatalogController",
                  //"MIS.controller.order.OrderController",
                  //"MIS.controller.order.SubOrderController",
                  "MIS.controller.dict.DictController",
                  "MIS.controller.brand.BrandController",
                  "MIS.controller.series.SeriesController",
                  "MIS.controller.singleproduct.SingleController",
                  "MIS.controller.check.CheckController",//下单清单
                  "MIS.controller.shipped.ShippedHeadController",//邮寄清单
                  "MIS.controller.storage.StorageController",//仓库清单
                  "MIS.controller.selled.SelledController", //售出清单
                  "MIS.controller.output.OutputController", //导出控制
                  "MIS.controller.mailuser.MailUserController", //邮件用户
                  "MIS.controller.user.UserController", //用户管理
                  "MIS.controller.permission.PermissionController", //权限管理
                  "MIS.controller.role.RoleController", //角色管理
                  "MIS.controller.batch.BatchController" //批次号管理
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
        
        Ext.override(Ext.selection.Model,{  
            onStoreLoad:function(store, records, successful, eOpts){  
                var me = this,  
                    length = me.selected.getCount( );  
                  
                //如果没有选中的记录，则不需要进行任何的操作  
                if(length===0)return;  
                  
                //遍历selected并更新其中的记录  
                me.selected.eachKey(function(key,item){  
                    var model = store.getById(key);  
                      
                    //如果获取到了model就更新，否则从selected中移除  
                    if(model){  
                        me.selected.add(model);//add时会覆盖掉原来的值  
                    }else{  
                        me.selected.removeAtKey(key);  
                    }  
                })  
                  
            }  
        });
    },

    launch: function () {
    	Ext.getBody().unmask();
    	
//      Ext.create('Ext.form.Panel', {
//      title: 'Upload a Photo',
//      width: 400,
//      bodyPadding: 10,
//      frame: true,
//      renderTo: Ext.getBody(),
//      items: [{
//          xtype: 'filefield',
//          name: 'file',
//          fieldLabel: 'Photo',
//          labelWidth: 50,
//          msgTarget: 'side',
//          allowBlank: false,
//          anchor: '100%',
//          buttonText: 'Select Photo...'
//      }],
//
//      buttons: [{
//          text: 'Upload',
//          handler: function() {
//              var form = this.up('form').getForm();
//              if(form.isValid()){
//                  form.submit({
//                      url: '/upload/read',
//                      waitMsg: 'Uploading your photo...',
//                      success: function(fp, o) {
//                          Ext.Msg.alert('Success', 'Your photo "' + o.result.file + '" has been uploaded.');
//                      }
//                  });
//              }
//          }
//      }]
//  });
    }
});
