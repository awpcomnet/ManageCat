/**
 * 描述: 主窗口界面头部
 * 作者: 
 */
Ext.define("MIS.view.mainviewport.MainHeader", {
    extend: "Ext.toolbar.Toolbar",
    alias: "widget.mainheader",
    
    height: 80,
    cls: "overflow-vis",
    
    items: [
//            {
//        xtype: "label",
//        //html: "<div id=\"logo\"><!--<img class=\"sys_logo\" src=\"/resources/component/header/logo.png\" />--><span class=\"logoTitle\">专业代购猫</span></div>"
//    }, {
//        xtype: "tbfill"
//    }, 
    {
        xtype: "label",
        layout: "fit",
        html: "<div class=\"userBox\"><span class=\"userImage\"><img  style=\"float:left;display:block;width:30%;\"  src=\"/resources/component/header/header.png\" /></span><div class=\"show\"><span class=\"username\"></span><br /><span class=\"currentDate\"></span></div>" +
        		"<div class=\"details\" style=\"\">"+
            "<div class=\"userInfo\"></div>" +
            //"<div class=\"changePass\"><div class=\"fakeButton\">修改密码</div></div>" +
            "<div class=\"logout\"><a href=\"/authc/logout\">退出登录</a></div>" +
            "</div></div>",
        listeners: {
            afterrender: function () {
                /**
                 * 将用户名, 组织机构填充
                 */
                MIS.common.CurrentUser.load(function (user) {
                	debugger;
                    var usernameBox = Ext.DomQuery.selectNode(".show .username");
                    usernameBox.innerHTML = user.realname;
                });
                
                /**
                 * 将时间日期填充
                 */
                var dateElem = Ext.DomQuery.selectNode(".userBox .currentDate");
                dateElem.innerHTML = new Date().Format("yyyy-MM-dd");
                
                var obj = document.getElementById("outid");
                debugger;
            }
        }
    }]
});
