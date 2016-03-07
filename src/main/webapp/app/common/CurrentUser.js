/**
 * 描述: 缓存当前登录用户信息
 * 作者: 
 */
Ext.define("MIS.common.CurrentUser", {
    singleton:true,
    
    url: "/authc/userInfo", 

    user: {},

    load: function (callback) {
        Ext.Ajax.request({
            url: this.url,
            success: function (conn, request, options, eOpts) {
                var result = Ext.JSON.decode(conn.responseText, true);
                if (result && result.resultCode == 0) {
                    this.user = result.results[0];
                }
                if (callback) {
                    callback(this.user);
                }
            },
            failure: function (conn, request, options, eOpts) {
            }
        });
    }
});
