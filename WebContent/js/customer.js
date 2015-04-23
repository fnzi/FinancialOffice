/**
 * 客户代码管理
 */
var customer ={};
var meth;
var getCustomerUrl = ctx + '/doJson/customerService';
var insertUrl = ctx + '/doJson/customerService';
var deleteUrl = ctx + '/doJson/customerService';

Ext.onReady(function(){
	Ext.QuickTips.init();
	
	Ext.getDoc().on("contextmenu", function(e){
		e.stopEvent();
	});

	/**
	 * 查询数据模型定义及绑定
	 */
	customer.searchStore = new Ext.data.Store({
		autoLoad : false,
		url : getCustomerUrl,
		baseParams : {
			start : 0,
			limit : 50
		},
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'total',
			fields : [
			          {name : 'cmid', mapping : 'cmid'}, //id
			          {name : 'mername', mapping : 'mername'}, //商家名称
			          {name : 'code', mapping : 'code'}, //商家编号
			          {name : 'custom', mapping : 'custom'} //A3客户代码
			          ]
		})
	});
	
	/**
	 * 数据列名定义
	 */
	customer.colModel = new Ext.grid.ColumnModel({
		columns: [
		          new Ext.grid.RowNumberer(),
		           { header : 'cmid' ,id : 'cmid', dataIndex : 'cmid', width: 150, hidden:true},
				   { header: "商家编号",  id : 'mername' , dataIndex : 'mername'},
				   { header: "商家名称",  id : 'code' , dataIndex : 'code'},
				   { header: "A3客户代码",  id : 'custom' , dataIndex : 'custom'}
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
	customer.form = new Ext.form.FormPanel({
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
				  	 fieldLabel : 'cmid',
					 id : 'cmid',
					 name : 'cmid',
					 readOnly : false,
					 hidden:true
				},
				 {
			       	 fieldLabel : '商家编号',
			    	 id : 'code',
			    	 name : 'code',
			    	 allowBlank : false,
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '商家名称',
			    	 id : 'name',
			    	 name : 'name',
			    	 allowBlank : false,
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : 'A3客户代码',
			    	 id : 'custom',
			    	 name : 'custom',
			    	 allowBlank : false,
			    	 readOnly : false
				 }
		]
	});
	
	customer.win = new Ext.Window({
		title : '新增客户代码',
		width : 300 , 
		height : 300 ,
		closable : false,
		autoScroll:true,
		modal:true,
		layout : 'fit',
		items : [customer.form],
		buttons : [{
			text : '保存',
			id : 'MSAccept',
			iconCls : 'accept' ,
			handler : function(){
				var code = Ext.getCmp("code").getValue();
				var name = Ext.getCmp("name").getValue();
				var custom = Ext.getCmp("custom").getValue();
				var id = null;
				if(meth==3){
					 id = Ext.getCmp("cmid").getValue();
				}
				Ext.Ajax.request({
					url : insertUrl, 
					params:{
						'meth': meth,
						'name':name,
						'code':code,
						'custom':custom,
						'id':id,
						 start : 0, 
						 limit : 50
					},
					success : function(resp , opts){
						var respObj = Ext.util.JSON.decode(resp.responseText);
						if(respObj.success){
							customer.searchStore.baseParams = {
								'meth': 1,
								'name':name,
								 start : 0, 
								 limit : 50
							},
							customer.searchStore.load();
							customer.win.hide();
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
				customer.win.hide();
			}
		}]
	});

	customer.addAction = new Ext.Action({
		text : '添加客户代码',
		id:'customer.add',
		disabled : false,
		iconCls : 'add',
		handler : function() {
			meth=2;
			customer.win.show();
			customer.win.center();
			customer.form.getForm().getEl().dom.reset();
			customer.form.getForm().clearInvalid();
			Ext.getCmp('MSAccept').enable();
		}
	});
	
	customer.editeAction = new Ext.Action({
		text : '修改客户代码',
		id:'customer.edit',
		disabled : false,
		iconCls : 'edit',
		handler : function() {
			meth=3;
			var sr = Ext.getCmp('customer.selectGird').getSelectionModel().getSelected();
			if(!sr){
					Ext.Msg.alert('提示','请选择一条记录!');
					return;
			 }
			customer.win.show();
			customer.win.center();
			customer.win.setTitle("修改客户代码");
			
			Ext.getCmp("name").setValue(sr.get('mername'));
			Ext.getCmp("code").setValue(sr.get('code'));
			Ext.getCmp("custom").setValue(sr.get('custom'));
			Ext.getCmp("cmid").setValue(sr.get('cmid'));
			Ext.getCmp('MSAccept').enable();
		}
	});
	
	customer.deleteAction = new Ext.Action({
		text : '删除客户代码',
		id:'customer.delete',
		disabled : false,
		iconCls : 'delete',
		handler : function() {
			var sr = Ext.getCmp('customer.selectGird').getSelectionModel().getSelected();
			 if(!sr){
					Ext.Msg.alert('提示','请选择一条记录!');
					return;
			 }
			 var name=sr.get('mername');
			 var code=sr.get('code');
			 var custom=sr.get('custom');
			 var id=sr.get('cmid');
			Ext.Ajax.request({
				url : deleteUrl, 
				params:{
					'meth': 4,
					'name':name,
					'code':code,
					'custom':custom,
					'id':id,
					 start : 0, 
					 limit : 50
				},
				success : function(resp , opts){
					var respObj = Ext.util.JSON.decode(resp.responseText);
					if(respObj.success){
						customer.searchStore.baseParams = {
							'meth': 1,
							'name':name,
							 start : 0, 
							 limit : 50
						},
						customer.searchStore.load();
				   }else {
					Ext.Msg.alert('提示',respObj.map.message);
					return;	
				}
			}
	   });
		}
	});
	
	customer.searchRoleAction = new Ext.Action({
		text : '查询',
		id:'customer.search',
		iconCls : 'search',
		handler : function() {
			meth=1;
			var name = Ext.getCmp("customer.name").getValue();
			customer.searchStore.baseParams = {
					'meth': 1,
					'name':name,
					 start : 0, 
					 limit : 50
				},
			
				customer.searchStore.load();
		}
	});
	
	

	/**
	 *查询表单网格
	 */
	customer.selectGird = new Ext.grid.GridPanel({
		margins : '5 5 5 5',
		id:'customer.selectGird',
		title : '客户代码管理',
		region : 'center',
		stripeRows : true,
		autoDestroy : true,
		store : customer.searchStore,
		colModel : customer.colModel,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		tbar : [ 
		        customer.addAction,
		        customer.editeAction,
		        customer.deleteAction,
		        {xtype : 'tbtext' ,text: '商家名称:'},
				{xtype : 'textfield', id : 'customer.name', width : 130,
		        	regexText : '请输入正确的商家名称',
					maxLength:20},
		        customer.searchRoleAction
		],
		bbar : new Ext.PagingToolbar({
			store : customer.searchStore,
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
	customer.view = new Ext.Viewport({
		layout : 'border',
		items : [ customer.selectGird ]
	});

});