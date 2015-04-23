/**
 * 角色管理
 */
var role ={};
var meth;
var getRoleUrl = ctx + '/doJson/roleService';
var insertUrl = ctx + '/doJson/roleService';
var deleteUrl = ctx + '/doJson/roleService';
var loadMenuTreeUrl = ctx + '/doJson/roleAuthorityService?meth=1';
var loadRoleMenuUrl = ctx + '/doJson/roleAuthorityService?meth=2';
var saveRoleMenuUrl = ctx + '/doJson/roleAuthorityService?meth=3';

Ext.onReady(function(){
	Ext.QuickTips.init();
	
	Ext.getDoc().on("contextmenu", function(e){
		e.stopEvent();
	});

	/**
	 * 查询数据模型定义及绑定
	 */
	role.searchStore = new Ext.data.Store({
		autoLoad : true,
		url : getRoleUrl,
		baseParams : {
			'meth':1,
			start : 0,
			limit : 50
		},
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'total',
			fields : [
			          {name : 'rid', mapping : 'rid'}, //角色id
			          {name : 'rname', mapping : 'rname'} //角色名
			          ]
		})
	});
	
	/**
	 * 数据列名定义
	 */
	role.colModel = new Ext.grid.ColumnModel({
		columns: [
		          new Ext.grid.RowNumberer(),
		           { header : 'id' ,id : 'rid', dataIndex : 'rid', width: 150, hidden:true},
				   { header: "角色",  id : 'rname' , dataIndex : 'rname'}
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
	role.form = new Ext.form.FormPanel({
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
				  	 fieldLabel : 'rid',
					 id : 'rid',
					 name : 'rid',
					 readOnly : false,
					 hidden:true
				},
				 {
			       	 fieldLabel : '角色',
			    	 id : 'rname',
			    	 name : 'rname',
			    	 allowBlank : false,
			    	 readOnly : false
				 }
		]
	});
	
	role.win = new Ext.Window({
		title : '新增角色',
		width : 300 , 
		height : 300 ,
		closable : false,
		autoScroll:true,
		modal:true,
		layout : 'fit',
		items : [role.form],
		buttons : [{
			text : '保存',
			id : 'MSAccept',
			iconCls : 'accept' ,
			handler : function(){
				var rname = Ext.getCmp("rname").getValue();
				var rid = null;
				if(meth==3){
					 rid = Ext.getCmp("rid").getValue();
				}
				Ext.Ajax.request({
					url : insertUrl, 
					params:{
						'meth': meth,
						'rname':rname,
						'rid':rid,
						 start : 0, 
						 limit : 50
					},
					success : function(resp , opts){
						var respObj = Ext.util.JSON.decode(resp.responseText);
						if(respObj.success){
							role.searchStore.baseParams = {
								'meth': 1,
								'rname':rname,
								 start : 0, 
								 limit : 50
							},
							role.searchStore.load();
							role.win.hide();
					   }else {
						Ext.Msg.alert('提示',respObj.msg);
						Ext.getCmp('MSAccept').disable();
						role.win.hide();
						return;	
					}
				}
		   });
		}
		},{
			text : '关闭',
			iconCls : 'cancel',
			handler : function(){
				role.win.hide();
			}
		}]
	});

	role.addAction = new Ext.Action({
		text : '添加角色',
		id:'role.add',
		disabled : false,
		iconCls : 'add',
		handler : function() {
			meth=2;
			role.win.show();
			role.win.center();
			role.form.getForm().getEl().dom.reset();
			role.form.getForm().clearInvalid();
			Ext.getCmp('MSAccept').enable();
		}
	});
	
	role.editAction = new Ext.Action({
		text : '修改角色',
		id:'role.edit',
		disabled : true,
		iconCls : 'edit',
		handler : function() {
			meth=3;
			var sr = Ext.getCmp('role.selectGird').getSelectionModel().getSelected();
			if(!sr){
					Ext.Msg.alert('提示','请选择一条记录!');
					return;
			 }
			role.win.show();
			role.win.center();
			role.win.setTitle("修改角色");
			Ext.getCmp("rname").setValue(sr.get('rname'));
			Ext.getCmp("rid").setValue(sr.get('rid'));
			Ext.getCmp('MSAccept').enable();
		}
	});
	
	role.deleteAction = new Ext.Action({
		text : '删除角色',
		id:'role.delete',
		disabled : true,
		iconCls : 'delete',
		handler : function() {
			var sr = Ext.getCmp('role.selectGird').getSelectionModel().getSelected();
			 if(!sr){
					Ext.Msg.alert('提示','请选择一条记录!');
					return;
			 }
			 var rname=sr.get('rname');
			 var rid=sr.get('rid');
			Ext.Ajax.request({
				url : deleteUrl, 
				params:{
					'meth': 4,
					'rname':rname,
					'rid':rid,
					 start : 0, 
					 limit : 50
				},
				success : function(resp , opts){
					var respObj = Ext.util.JSON.decode(resp.responseText);
					if(respObj.success){
						role.searchStore.baseParams = {
							'meth': 1,
							'rname':rname,
							 start : 0, 
							 limit : 50
						},
						role.searchStore.load();
				   }else {
					Ext.Msg.alert('提示',respObj.map.message);
					return;	
				}
			}
	   });
		}
	});
	
	role.searchRoleAction = new Ext.Action({
		text : '查询',
		id:'role.search',
		iconCls : 'search',
		handler : function() {
			meth=1;
			var rname = Ext.getCmp("role.rname").getValue();
			role.searchStore.baseParams = {
					'meth': 1,
					'rname':rname,
					 start : 0, 
					 limit : 50
				},
			
				role.searchStore.load();
		}
	});
	
	

	/**
	 *查询表单网格
	 */
	role.selectGird = new Ext.grid.GridPanel({
		margins : '5 5 5 5',
		id:'role.selectGird',
		title : '角色管理',
		region : 'center',
		stripeRows : true,
		autoDestroy : true,
		store : role.searchStore,
		colModel : role.colModel,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		listeners : {
			'cellclick' : function(grid ,rowIndex , columnIndex, event){
				if(grid.getSelectionModel().getSelected()){
					role.editAction.enable();
					role.deleteAction.enable();
					role.menuTree.enable();
					clearTreeNodeCheck(role.menuTree.root , false);
					Ext.Ajax.request({
						url : loadRoleMenuUrl , 
						params : {
							'rid' : role.selectGird.getSelectionModel().getSelected().data.rid
						},
						success : function(resp , opts){
							var respObj = Ext.util.JSON.decode(resp.responseText);
							for(var i =0 ; i< respObj.data.length ; i++){
								mid = respObj.data[i];
								treeNode = role.menuTree.getNodeById(mid);
								treeNode.getUI().checkbox.checked = true;
								treeNode.attributes.checked = true;
								treeNode.getUI().checkbox.defaultChecked = true;
							}
						},
						failure : dealErrResp
					});
				}else{
					clearTreeNodeCheck(role.menuTree.root , false);
					role.editAction.disable();
					role.deleteAction.disable();
					role.menuTree.disable();
				}
			}
		},
		tbar : [ 
		        role.addAction,
		        role.editAction,
		        role.deleteAction,
		        {xtype : 'tbtext' ,text: '角色:'},
				{xtype : 'textfield', id : 'role.rname', width : 130,
		        	regexText : '请输入正确的角色',
					maxLength:20},
		        role.searchRoleAction
		],
		bbar : new Ext.PagingToolbar({
			store : role.searchStore,
			displayInfo : true,
			pageSize : 50,
			doRefresh:function(){
        		 return false;
			}
		})
	});
	/**
	 *角色权限管理树
	 */
	role.menuTree = new Ext.tree.TreePanel({
		title : '角色权限',
		useArrows: false,
	    autoScroll: true,
	    animate: true,
	    enableDD: false,
	    buttonAlign : 'left' ,
	    disabled : true ,
	    containerScroll: true,
	    region : 'east' ,
	    border: true ,
	    margins : '5 5 5 0',
	    split : true,
	    width : 400 ,
	    collapsible: false,
	    rootVisible : false ,
	    dataUrl : loadMenuTreeUrl,
	    root: {
			nodeType: 'async' , 
			id : -100
		},
	    buttons : [
		            {
		            	text : '保存' , 
		            	iconCls : 'disk',
		            	handler : function(){
		            		var node = role.menuTree.getChecked();
		    	            var checkid = new Array;// 存放选中id的数组
		    	            for (var i = 0; i < node.length; i++) {
		    	              	checkid.push(node[i].id);// 添加id到数组
		    	            }
		    	            Ext.Ajax.request({
		    					url : saveRoleMenuUrl,
		    					params :{
		    						'rid' : role.selectGird.getSelectionModel().getSelected().data.rid ,
		    						'mid' : checkid.toString()
		    					},
		    					success:function(resp, opts){
		    						var respObj = Ext.util.JSON.decode(resp.responseText);
		    						if (respObj.success){
		    							Ext.Msg.alert('提示', respObj.msg);
		    						}else {
		    							Ext.Msg.alert('提示',respObj.msg);
		    							Ext.getCmp('MSAccept').disable();
		    							return;	
		    						}
		    					},
		    					failure:dealErrResp
		    				});
		            	}
		            }
	            ]
	});
	
	role.menuTree.on('checkchange', function(node , checked) {

		node.expand();
		node.attributes.checked = checked;
		var cb = node.ui.checkbox;
	    if(cb){
	         cb.checked = (checked === undefined ? !cb.checked : checked);
	         cb.defaultChecked = checked;
	    }
		if(node.parentNode.id == -100){
			var childrens = node.childNodes;
			for(var i =0 ; i< childrens.length ; i++){
				var child = childrens[i];
				child.attributes.checked = checked;
				cb = child.ui.checkbox;
				if(cb){
				     cb.checked = (checked === undefined ? !cb.checked : checked);
				     cb.defaultChecked = checked;
				}
			}
		}else{
			var parent = node.parentNode;
			var childrens = parent.childNodes;
			if(checked == true){
				parent.attributes.checked = checked;
				cb = parent.ui.checkbox;
			    if(cb){
			         cb.checked = (checked === undefined ? !cb.checked : checked);
			         cb.defaultChecked = checked;
			    }
			}else{
				for(var i =0 ; i< childrens.length ; i++){
					var child = childrens[i];
					if(child.attributes.checked != checked){
						return;
					}
				}
				parent.attributes.checked = checked;
				cb = parent.ui.checkbox;
			    if(cb){
			         cb.checked = (checked === undefined ? !cb.checked : checked);
			         cb.defaultChecked = checked;
			    }
			}
		}

	}, role.menuTree);

	role.menuTree.on('load' , function(){
		role.menuTree.expandAll();
	});
	
	/**
	 * 查询呈现视图
	 */
	role.view = new Ext.Viewport({
		layout : 'border',
		items : [ role.selectGird,role.menuTree]
	});

});

function clearTreeNodeCheck(treeNode, checked){
	var i;
	if (treeNode.hasChildNodes())
	{
		for (i = 0; i < treeNode.childNodes.length; i++)
		{
			if (treeNode.childNodes[i].getUI().checkbox)
			{
				treeNode.childNodes[i].getUI().checkbox.checked = checked;
				treeNode.childNodes[i].attributes.checked = checked;
				treeNode.childNodes[i].getUI().checkbox.defaultChecked = checked;
			}
		}

		for (i = 0; i < treeNode.childNodes.length; i++)
		{
			clearTreeNodeCheck(treeNode.childNodes[i], checked);
		}
	}
}