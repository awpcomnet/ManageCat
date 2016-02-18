(function (global, $, undefined) {
    $(global.document).ready(function () {
        /** 
         * 设定背景属性
         */
        $(".bground").css({height: $(global).height()});
        $(global).resize(function () {
            $(".bground").css({height: $(global).height()});
        });

        /**
         * 文本框事件
         */
        $(".input__field").focus(function () {
            var $this = $(this);
            $this.removeClass("textfield--error");
            $this.addClass("textfield--normal");
        });

        /**
         * 登录按钮点击事件
         */
        $(".submit").click(function () {
            login();
        });

        /**
         * 验证码事件
         */
        var captchaImageElem = $(".captchaImage");
        captchaImageElem.poshytip({
            content: "看不清, 点击切换",
            showTimeout: 1000
        });
        captchaImageElem.click(function (event) {
            $(this).find("img").attr("src", "/authc/captcha?random=" + Math.random());
        });

        /**
         * loginBox的Enter登录事件
         */
        global.document.onkeydown = function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];         
            if(e && e.keyCode==13){ // enter 键
                 login();
            }
        };
        
        /**
         * 获得参数
         */
        var checkoutParam = function (textfield, errorMessage) {
            var elem  = $(".input__field[name='"+ textfield +"']"),
                value = elem.val();
                
            if (!value) {
                alertify.error(errorMessage);
                elem.removeClass("textfield--normal");
                elem.addClass("textfield--error");
            }
            
            return value;
        };
        
        /**
         * 登陆函数
         */
        var login = function () {
            var username, password, captcha, isRememberMe;
            
            if(!(username = checkoutParam("username", "用户名不能为空!")))
                return;
            if(!(password = checkoutParam("password", "密码不能为空!")))
                return;
            if(!(captcha = checkoutParam("captcha", "验证码不能为空!")))
                return;

            isRememberMe = $(".rememberme").prop("checked") ? 1 : 0;
            
            $.ajax({
                type: "POST",
                url: "/authc/login",
                data: {
                    username: username,
                    password: password,
                    captcha: captcha,
                    rememberme: isRememberMe
                },
                success: function (text) {
                    var result = text
                    if (result && result.resultCode == 0) {
                        global.location.href = "/";
                    } else {
                        var msg = result && result.resultMessage ? result.resultMessage : "登录请求错误";
                        alertify.error(msg);
                    }
                },
                fail: function (text) {
                    alertify.error("登录请求失败");
                }
            });
        };
    }); 
})(this, $);
