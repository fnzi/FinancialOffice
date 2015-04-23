var tree={};
var main={};
var modifyPass = {};

var loadMenuTree = ctx + '/doJson/getMenuService.json';
var logout =  ctx ;
var modifyPassword = ctx + '/modifyPassword.do';

/**
*	Main Method
*/
Ext.override(Ext.Window, {      
    constrain:true,     
    constrainHeader:true  
}); 

Ext.onReady(function(){
	Ext.QuickTips.init();

	Ext.getDoc().on("contextmenu", function(e){
		e.stopEvent();
	});
	
	tree.treePanel = new Ext.tree.TreePanel({
		title : '当前用户 - '+userName,
		useArrows: false,
	    autoScroll: true,
	    animate: true,
	    enableDD: false,
	    containerScroll: true,
	    region : 'west' ,
	    border: true ,
	    margins : '0 3 0 2',
	    split : true,
	    width : 260 ,
	    lines:false,
	    collapsible: true,
	    rootVisible : false ,
	    collapseFirst:false,
	    loader : new Ext.tree.TreeLoader({
	    	dataUrl : loadMenuTree,
	    	baseParams : {  
	    		'userrid' : userRid
            }  
	    })
	});
	
	tree.treeRoot = new Ext.tree.AsyncTreeNode({
		draggable : false , 
		expanded : true,
		id : -100,
	    cls:'feeds-node',
		text : '用户菜单'
	});

	tree.treePanel.setRootNode(tree.treeRoot);
	
	main.tabPanel = new Ext.TabPanel({
		region : 'center',
		deferredRender: false,
		activeTab : 0,
		enableTabScroll : true,
		closable : false ,
		autoScroll : true , 
		items : [{
			title : '首页' ,
			html : '<table width = "100%" height="100%" ><tr height="60px" ><td></td></tr><tr><td align="center" valign="top">欢迎使用财务办公系统</td></tr></table>'
		}]
	});
	
	tree.treePanel.on('click' , function(node , event){
		if(node.attributes.url){
			var tab = main.tabPanel.getComponent(node.id);
			if(!tab){
				var url = ctx + node.attributes.url;
				if(node.attributes.url.substring(0,4) == 'http'){
					url = node.attributes.url;
				};
				main.tabPanel.add({
					title : node.text, 
					id : node.id , 
					closable  : true,
					html : '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+ url + '"></iframe>'
				});
			}
			main.tabPanel.setActiveTab(node.id);
		}else{
			if(node.isExpanded()){
				node.collapse();
			}else{
				node.expand();
			}
		}
	});
	
	tree.treePanel.on('expandnode', function(node){	      
		var iconCls ;
	    for (var i = 0, len = node.childNodes.length; i < len; i++) {  
	    	var curChild = node.childNodes[i]; 
	        iconCls = curChild.attributes.iconCls;
	        if(iconCls != 'undefined' && iconCls != null && iconCls != ''){
	        	curChild.getUI().getIconEl().src = ctx + curChild.attributes.iconCls;
	        }	        
	    }  
	});
	
	main.view = new Ext.Viewport({
		layout : 'border' ,
		items :[
			tree.treePanel, 
			main.tabPanel,
		        {
				region : 'north' ,
			    html : '<table width = "100%" height="85"  border="0" cellspacing="0" cellpadding="0" style="margin: 0 0 0 0;background-image:url('+ctx+'/images/title_02.jpg)">'+
				           '<tr>'+
				              '<td width = "80%" align="center">'+
				              		'<span>天津城市一卡通 财务办公系统</span>'+
				              '</td>'+
				              '<td align="right"   valign="middle"  style="font-size:12px">'+
				              
				              		'<table width = "100%" height="85"  border="0" cellspacing="0" cellpadding="0" >'+
				              		'<td align="right"><img src="'+ctx+'/images/modifyPass.gif" /></td><td align="left"><a href="#" onClick="modifyPassWord()"  >修改密码</a>&nbsp;</td>'+
				              		'<td align="right"><img src="'+ctx+'/images/exitSys.gif" /></td><td align="left"><a href="#" onClick = "loginOut()"   >退出系统&nbsp;&nbsp;&nbsp;&nbsp;</a>&nbsp;'+
				              '</td>' + 
				              '</td>'
				             + '</tr></table>'+
				        '</table>',
				height : 85
		        }]
	});
	
	
	modifyPass.dataForm = new Ext.form.FormPanel({
		align : 'center' ,
		labelAlign: 'right',
		labelWidth:80,
		frame:true,
		region : 'center',
		defaultType:'textfield',
		buttonAlign : 'left',
		items : [{
					xtype :'label',
					height : 5
				},{
					fieldLabel : '原密码',
					name : 'oldpassword',
					id : 'oldpassword',
					inputType: 'password',
					allowBlank:false,
					blankText:'原密码不能为空'
//					maxLength : 12,
//					regex : /^[0-9a-zA-Z]{4,12}$/ ,
//					regexText : '原密码只允许输入数字和字母,4-12位长',
//					minLength : 4	
				},{
					fieldLabel : '密码',
					name : 'password',
					id : 'password',
					inputType: 'password',
					allowBlank:false,
					blankText:'密码不能为空'
//					maxLength : 20,
					//regex : /^[0-9a-zA-Z]{4,12}$/ ,
//					regex : /^((?=.*\d)(?=.*[a-z])(?=.*[A-Z]).*)/,
//					regexText : '密码包含至少一个大写字母、至少一个小写字母和至少一个数字',
//					minLength : 8	
				},{
					fieldLabel : '确认密码',
					name : 'password2',
					id : 'password2',
					inputType: 'password',
					allowBlank:false,
					blankText:'确认密码不能为空'
					//regex : /^[0-9a-zA-Z]{4,12}$/ ,
//					regex : /^((?=.*\d)(?=.*[a-z])(?=.*[A-Z]).*)/,
//					regexText : '密码包含至少一个大写字母、至少一个小写字母和至少一个数字',
//					maxLength : 20,
//					minLength : 8
				}]
	});
	
	
	
	modifyPass.dataWin = new Ext.Window({
		title : '修改密码',
		modal:true,
		closeAction : 'hide',
		closable : false ,
		width : 280 ,
		items : [modifyPass.dataForm] ,
		buttons : [{
			text : '修改',
			handler : function(){
				if(!modifyPass.dataForm.get('password').validate() || !modifyPass.dataForm.get('password2').validate()){
					return ;
				}
				// 验证两次输入的密码
				if (modifyPass.dataForm.get('password').getValue().trim()!=modifyPass.dataForm.get('password2').getValue().trim()) {
					Ext.MessageBox.alert('系统提示', '两次输入的密码不一致！');
					return;
				}
				var oldpassword = modifyPass.dataForm.get('oldpassword').getValue();
				Ext.Ajax.request({
					url : modifyPassword,
					params :{
						'password' : modifyPass.dataForm.get('password').getValue(),
						'oldpassword' : oldpassword
					},
					success:function(resp, opts){
						var respObj = Ext.util.JSON.decode(resp.responseText);
						if (respObj.success){
							Ext.Msg.alert('提示', respObj.msg);
							modifyPass.dataWin.hide();
							toModifyPass = '';
						}else{
							if(respObj.msg == 'NoLogin'){
								Ext.Msg.alert('提示', '登录超时,请重新登录', function(){
									window.location.href = ctx ;
								});
							}else{
								Ext.Msg.alert('提示', respObj.msg);
							}
						}
					},
					failure: dealErrResp
				});
			}
		},{
			text : '取消',
			handler : function(){
				modifyPass.dataWin.hide();
			}
		}]
	});

});

function modifyPassWord(){
	modifyPass.dataForm.getForm().reset();
	modifyPass.dataWin.show();
}

function loginOut(){
	window.location.href = logout;
}
