LoginPanel = function() {
  
    var win, f;
    var buildForm = function() {
        f = new Ext.form.FormPanel( {
            bodyStyle : 'padding-top:10px;padding-bottom:12px',
            defaultType : 'textfield',
            labelAlign : 'right',
            labelWidth : 55,
            labelPad : 0,
            frame : true,
            defaults : {
            allowBlank : false,
            width : 158
            },
            keys:[{
                key:13,
                fn:function(){
                     document.getElementById("login").click();
                },
                scope:this
            }],
            items : [ {
                    id : 'username',
                    cls : 'user',
                    name : 'username',
                    fieldLabel : '帐号',
                    blankText : '帐号不能为空'
                }, {
                	id : 'pwd',
                    cls : 'key',
                    name : 'pwd',
                    fieldLabel : '密码',
                    blankText : '密码不能为空',
                    inputType : 'password'
                }]
        });
    };
    var buildWin = function() {
        win = new Ext.Window( {
            el : 'wins',
            title : '登录系统',
            width : 265,
            height : 153,
            layout : 'column',
            collapsible : true,
            draggable: false,
            defaults : {
                border : false
            },
            items : {
                columnWidth : 1,
                items : f
            },
            buttonAlign : 'left',
            buttons : [ {
                    width : 115,
                    id : 'login',
                    text : '登录',
                    handler : login
                }, {
                	width : 115,
                    text : '重置',
                    handler : reset
                }]
        });
    };
    var login = function(){
     if(f.form.isValid()){
          f.form.submit( {
                waitMsg : '正在登陆验证,请稍后...',
                url : ctx + '/doJson/loginService',
                method : 'POST',
                success : function(form, action) {
                       if(action.result.success){
                    	  var userrid=action.result.data;
                            window.location.href = ctx + '/toPage/main.do?userrid='+ userrid;
                       } else {
                    	   Ext.MessageBox.alert('登陆失败', action.result.msg);
                       }
                },
                failure : function(form, action) {
                    reset();
                    if (action.failureType == Ext.form.Action.SERVER_INVALID)
                        Ext.MessageBox.alert('登陆失败', action.result.msg);
                    }
            });
    }};
    var reset = function() {
        f.form.reset();
    };

    return {
        init : function() {
            Ext.QuickTips.init();
            Ext.form.Field.prototype.msgTarget = 'side';
            buildForm();
            buildWin();
            win.show();

            Ext.getCmp('username').focus(false,100);
        }
    }
}();
Ext.onReady(LoginPanel.init, LoginPanel);

