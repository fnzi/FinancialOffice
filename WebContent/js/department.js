/**
 * 客户代码管理
 */
var departments ={};
var meth;
var getDepartmentUrl = ctx + '/doJson/departmentService';
var insertUrl = ctx + '/doJson/departmentService';
var deleteUrl = ctx + '/doJson/departmentService';

Ext.onReady(function(){
	Ext.QuickTips.init();
	
	Ext.getDoc().on("contextmenu", function(e){
		e.stopEvent();
	});

	/**
	 * 查询数据模型定义及绑定
	 */
	departments.searchStore = new Ext.data.Store({
		autoLoad : false,
		url : getDepartmentUrl,
		baseParams : {
			start : 0,
			limit : 50
		},
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'total',
			fields : [
			          {name : 'id', mapping : 'id'}, //id
			          {name : 'name', mapping : 'name'}, //部门名称
			          {name : 'code', mapping : 'code'}, //部门编号
			          {name : 'department', mapping : 'department'} //A3部门代码
			          ]
		})
	});
	
	/**
	 * 数据列名定义
	 */
	departments.colModel = new Ext.grid.ColumnModel({
		columns: [
		          new Ext.grid.RowNumberer(),
		           { header : 'id' ,id : 'id', dataIndex : 'id', width: 150, hidden:true},
				   { header: "部门编号",  id : 'code' , dataIndex : 'code'},
				   { header: "部门名称",  id : 'name' , dataIndex : 'name'},
				   { header: "A3部门代码",  id : 'department' , dataIndex : 'department'}
		       ],
		 defaults: {
		       sortable: true,
		       menuDisabled: true,
		       width: 120
		 }
	});
	
	/**
	 * form
	 */
	departments.form = new Ext.form.FormPanel({
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
				  	 fieldLabel : 'id',
					 id : 'id',
					 name : 'id',
					 readOnly : false,
					 hidden:true
				},
				 {
			       	 fieldLabel : '部门编号',
			    	 id : 'code',
			    	 name : 'code',
			    	 allowBlank : true,
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '部门名称',
			    	 id : 'name',
			    	 name : 'name',
			    	 allowBlank : false,
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : 'A3部门代码',
			    	 id : 'department',
			    	 name : 'department',
			    	 allowBlank : false,
			    	 readOnly : false
				 }
		]
	});
	
	departments.win = new Ext.Window({
		title : '新增部门代码',
		width : 300 , 
		height : 300 ,
		closable : false,
		autoScroll:true,
		modal:true,
		layout : 'fit',
		items : [departments.form],
		buttons : [{
			text : '保存',
			id : 'MSAccept',
			iconCls : 'accept' ,
			handler : function(){
				var code = Ext.getCmp("code").getValue();
				var name = Ext.getCmp("name").getValue();
				var department = Ext.getCmp("department").getValue();
				var id = null;
				if(meth==3){
					 id = Ext.getCmp("id").getValue();
				}
				Ext.Ajax.request({
					url : insertUrl, 
					params:{
						'meth': meth,
						'name':name,
						'code':code,
						'department':department,
						'id':id,
						 start : 0, 
						 limit : 50
					},
					success : function(resp , opts){
						var respObj = Ext.util.JSON.decode(resp.responseText);
						if(respObj.success){
							departments.searchStore.baseParams = {
								'meth': 1,
								'name':name,
								 start : 0, 
								 limit : 50
							},
							departments.searchStore.load();
							departments.win.hide();
					   }else {
						Ext.Msg.alert('提示',respObj.msg);
						Ext.getCmp('MSAccept').disable();
						return;	
					}
				}
		   });
		}
		},{
			text : '关闭',
			iconCls : 'cancel',
			handler : function(){
				departments.win.hide();
			}
		}]
	});

	departments.addAction = new Ext.Action({
		text : '添加部门代码',
		id:'departments.add',
		disabled : false,
		iconCls : 'add',
		handler : function() {
			meth=2;
			departments.win.show();
			departments.win.center();
			departments.form.getForm().getEl().dom.reset();
			departments.form.getForm().clearInvalid();
			Ext.getCmp('MSAccept').enable();
		}
	});
	
	departments.editeAction = new Ext.Action({
		text : '修改部门代码',
		id:'departments.edit',
		disabled : false,
		iconCls : 'edit',
		handler : function() {
			meth=3;
			var sr = Ext.getCmp('departments.selectGird').getSelectionModel().getSelected();
			if(!sr){
					Ext.Msg.alert('提示','请选择一条记录!');
					return;
			 }
			departments.win.show();
			departments.win.center();
			departments.win.setTitle("修改部门代码");
			
			Ext.getCmp("name").setValue(sr.get('name'));
			Ext.getCmp("code").setValue(sr.get('code'));
			Ext.getCmp("department").setValue(sr.get('department'));
			Ext.getCmp("id").setValue(sr.get('id'));
			Ext.getCmp('MSAccept').enable();
		}
	});
	
	departments.deleteAction = new Ext.Action({
		text : '删除部门代码',
		id:'departments.delete',
		disabled : false,
		iconCls : 'delete',
		handler : function() {
			var sr = Ext.getCmp('departments.selectGird').getSelectionModel().getSelected();
			 if(!sr){
					Ext.Msg.alert('提示','请选择一条记录!');
					return;
			 }
			 var name=sr.get('name');
			 var code=sr.get('code');
			 var department=sr.get('department');
			 var id=sr.get('id');
			Ext.Ajax.request({
				url : deleteUrl, 
				params:{
					'meth': 4,
					'name':name,
					'code':code,
					'department':department,
					'id':id,
					 start : 0, 
					 limit : 50
				},
				success : function(resp , opts){
					var respObj = Ext.util.JSON.decode(resp.responseText);
					if(respObj.success){
						departments.searchStore.baseParams = {
							'meth': 1,
							'name':name,
							 start : 0, 
							 limit : 50
						},
						departments.searchStore.load();
				   }else {
					Ext.Msg.alert('提示',respObj.map.message);
					return;	
				}
			}
	   });
		}
	});
	
	departments.searchRoleAction = new Ext.Action({
		text : '查询',
		id:'departments.search',
		iconCls : 'search',
		handler : function() {
			meth=1;
			var name = Ext.getCmp("department.name").getValue();
			departments.searchStore.baseParams = {
					'meth': 1,
					'name':name,
					 start : 0, 
					 limit : 50
				},
			
				departments.searchStore.load();
		}
	});
	
	

	/**
	 *查询表单网格
	 */
	departments.selectGird = new Ext.grid.GridPanel({
		margins : '5 5 5 5',
		id:'departments.selectGird',
		title : '部门代码管理',
		region : 'center',
		stripeRows : true,
		autoDestroy : true,
		store : departments.searchStore,
		colModel : departments.colModel,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		tbar : [ 
		        departments.addAction,
		        departments.editeAction,
		        departments.deleteAction,
		        {xtype : 'tbtext' ,text: '部门名称:'},
				{xtype : 'textfield', id : 'department.name', width : 130,
		        	regexText : '请输入正确的部门名称',
					maxLength:20},
		        departments.searchRoleAction
		],
		bbar : new Ext.PagingToolbar({
			store : departments.searchStore,
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
	departments.view = new Ext.Viewport({
		layout : 'border',
		items : [ departments.selectGird ]
	});

});