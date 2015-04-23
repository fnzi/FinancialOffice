/**
 * 用户管理
 */
var users ={};
var meth=0;
var getUsersUrl = ctx + '/doJson/userService';
var insertUrl = ctx + '/doJson/userService';
var deleteUrl = ctx + '/doJson/userService';
Ext.onReady(function(){
	Ext.QuickTips.init();
	
	Ext.getDoc().on("contextmenu", function(e){
		e.stopEvent();
	});

	/**
	 * 查询数据模型定义及绑定
	 */
	users.searchStore = new Ext.data.Store({
		autoLoad : false,
		url : getUsersUrl,
		baseParams : {
			start : 0,
			limit : 50
		},
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'total',
			fields : [
			          {name : 'uid', mapping : 'uid'}, //用户id
			          {name : 'username', mapping : 'username'}, //用户名
			          {name : 'password', mapping : 'password'}, //用户密码
			          {name : 'realname', mapping : 'realname'},
			          {name : 'rid', mapping : 'urid'}
			          ]
		})
	});
	/** 显示角色列表 **/
	users.loadRoleStore = new Ext.data.Store({
		url : ctx + '/doJson/roleService',
		autoLoad : true, //是否自动加载
		baseParams : {
			'meth':1,
			start : 0,
			limit : 50
		},
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',	//后台返回的JSON中标识记录总数键名
			root : 'data',	//后台返回的JSON中实际存放数据的键名
			id : 'id'
		},
		[
         { name: 'rid', mapping: 'rid' },
         { name: 'rname', mapping: 'rname'}
         ]
		)
	});
	/**
	 * 数据列名定义
	 */
	users.colModel = new Ext.grid.ColumnModel({
		columns: [
		          new Ext.grid.RowNumberer(),
		           { header : 'id' ,id : 'uid', dataIndex : 'uid', width: 150, hidden:true},
				   { header: "用户名",  id : 'username' , dataIndex : 'username'},
				   { header: "密码",  id : 'password' , dataIndex : 'password'},
		           { header: "真实姓名",  id : 'realname' , dataIndex : 'realname'},
				   { header: "所属角色",  id : 'rid' , dataIndex : 'rid',renderer: getRole}
		       ],
		 defaults: {
		       sortable: true,
		       menuDisabled: true,
		       width: 120
		 }
	});
	users.ridComboBox = new Ext.form.ComboBox({
		id : 'users.rid' , 
   	 	allowBlank : false ,
   	 	store:users.loadRoleStore,
//        store: new Ext.data.ArrayStore({
//            fields: ['userValue','userDisplay'],
//            data : [[[1],['操作员']],[[2],['管理员']],[[3],['财务人员']]]
//        }),
        forceSelection: true,
        fieldLabel : '角色类型',
        displayField:'rname',
        hiddenName : 'users.rid',
		valueField : 'rid',
        typeAhead: true,
        mode: 'local',
        forceSelection: true,
        triggerAction: 'all',
        selectOnFocus:true
       
    });
	/**
	 * form
	 */
	users.form = new Ext.form.FormPanel({
		labelAlign: 'right',
		labelWidth:90,
		region : 'center',
		frame:true,
		defaultType:'textfield',
		defaults : {
			width : 150
		},
		items : [
				{
				 	 fieldLabel : 'uid',
					 id : 'uid',
					 name : 'uid',
					 readOnly : false,
					 hidden:true
				},
				 {
			       	 fieldLabel : '用户名',
			    	 id : 'username',
			    	 name : 'username',
			    	 allowBlank : false,
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '密码',
			    	 id : 'password',
			    	 name : 'password',
			    	 inputType:'password',
			    	 allowBlank : false,
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '真实姓名',
			    	 id : 'realname',
			    	 name : 'realname',
			    	 allowBlank : false,
			    	 readOnly : false
				 },
				 users.ridComboBox
		]
	});
	
	users.win = new Ext.Window({
		title : '新增用户',
		width : 300 , 
		height : 300 ,
		closable : false,
		autoScroll:true,
		modal:true,
		layout : 'fit',
		items : [users.form],
		buttons : [{
			text : '保存',
			id : 'MSAccept',
			iconCls : 'accept' ,
			handler : function(){
				var username = Ext.getCmp("username").getValue();
				var password = Ext.getCmp("password").getValue();
				var realname = Ext.getCmp("realname").getValue();
				var rid = Ext.getCmp("users.rid").getValue();
				var uid = null;
				if(meth==3){
					 uid = Ext.getCmp("uid").getValue();
				}
				Ext.Ajax.request({
					url : insertUrl, 
					params:{
						'meth': meth,
						'username':username,
						'password':password,
						'realname':realname,
						'rid':rid,
						'uid':uid,
						 start : 0, 
						 limit : 50
					},
					success : function(resp , opts){
						var respObj = Ext.util.JSON.decode(resp.responseText);
						if(respObj.success){
							users.searchStore.baseParams = {
								'meth': 1,
								'username':username,
								 start : 0, 
								 limit : 50
							},
							users.searchStore.load();
							users.win.hide();
					   }else {
						Ext.Msg.alert('提示',respObj.msg);
						Ext.getCmp('MSAccept').disable();
						users.win.hide();
						return;	
					}
				}
		   });
		}
		},{
			text : '关闭',
			iconCls : 'cancel',
			handler : function(){
				users.win.hide();
			}
		}]
	});

	users.searchAction = new Ext.Action({
		text : '添加用户',
		id:'users.add',
		disabled : false,
		iconCls : 'add',
		handler : function() {
			meth=2;
			users.win.show();
			users.win.center();
			users.form.getForm().getEl().dom.reset();
			users.form.getForm().clearInvalid();
			Ext.getCmp('MSAccept').enable();
		}
	});
	users.editeAction = new Ext.Action({
		text : '修改用户',
		id:'users.edit',
		disabled : false,
		iconCls : 'edit',
		handler : function() {
			meth=3;
			var sr = Ext.getCmp('users.selectGird').getSelectionModel().getSelected();
			 if(!sr){
					Ext.Msg.alert('提示','请选择一条记录!');
					return;
			 }
			users.win.show();
			users.win.center();
			users.win.setTitle("修改用户");
			Ext.getCmp("username").setValue(sr.get('username'));
			Ext.getCmp("password").setValue(sr.get('password'));
			Ext.getCmp("realname").setValue(sr.get('realname'));
			Ext.getCmp("users.rid").setValue(sr.get('rid'));
			Ext.getCmp("uid").setValue(sr.get('uid'));
			Ext.getCmp('MSAccept').enable();
		}
	});
	users.deleteAction = new Ext.Action({
		text : '删除用户',
		id:'users.delete',
		disabled : false,
		iconCls : 'delete',
		handler : function() {
			var sr = Ext.getCmp('users.selectGird').getSelectionModel().getSelected();
			 if(!sr){
					Ext.Msg.alert('提示','请选择一条记录!');
					return;
			 }
			 var username=sr.get('username');
			 var uid=sr.get('uid');
			Ext.Ajax.request({
				url : deleteUrl, 
				params:{
					'meth': 4,
					'username':username,
					'uid':uid,
					 start : 0, 
					 limit : 50
				},
				success : function(resp , opts){
					var respObj = Ext.util.JSON.decode(resp.responseText);
					if(respObj.success){
						users.searchStore.baseParams = {
							'meth': 1,
							'username':username,
							 start : 0, 
							 limit : 50
						},
						users.searchStore.load();
				   }else {
					Ext.Msg.alert('提示',respObj.map.message);
					return;	
				}
			}
	   });
			
		}
	});
	users.searchUserAction = new Ext.Action({
		text : '查询',
		id:'users.search',
		iconCls : 'search',
		handler : function() {
			meth=1;
			var usern = Ext.getCmp("users.username").getValue();
			users.searchStore.baseParams = {
					'meth': 1,
					'username':usern,
					 start : 0, 
					 limit : 50
				},
				users.searchStore.load();
		}
	});
	
	
	/**
	 * 查询表单网格
	 */
	users.selectGird = new Ext.grid.GridPanel({
		margins : '5 5 5 5',
		id:'users.selectGird',
		title : '用户管理',
		region : 'center',
		stripeRows : true,
		autoDestroy : true,
		store : users.searchStore,
		colModel : users.colModel,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		tbar : [ 
		        users.searchAction,
		        users.editeAction,
		        users.deleteAction,
		        {xtype : 'tbtext' ,text: '用户名:'},
				{xtype : 'textfield', id : 'users.username', width : 130,
		        	regexText : '请输入正确的用户名',
					maxLength:20},
		        users.searchUserAction
		],
		bbar : new Ext.PagingToolbar({
			store : users.searchStore,
			displayInfo : true,
			pageSize : 50,
			doRefresh:function(){
        		 return false;
			}
		})
	});

	/**
	 * 查询呈现视图
	 */
	users.view = new Ext.Viewport({
		layout : 'border',
		items : [ users.selectGird ]
	});

});
function getRole(value){
		var str = "";
	switch (value) {
		case 1:
			str = "操作员";
			break;
		case 2:
			str = "管理员";
			break;
		case 3:
			str = "财务人员";
			break;
		default:
			break;
	}
	return str;

}