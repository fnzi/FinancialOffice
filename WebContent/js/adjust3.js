/**
 * 手工调整（财务核算）
 * 
 */
var adjust3 ={};

var loadUrl = ctx + '/doJson/getAdjustService';
var loadA3Url = ctx + '/doJson/getAdjustA3Service';

var generateA3Url = ctx + '/doJson/generateAdjustA3Service';
var importA3DbUrl = ctx + '/doJson/importAdjustA3Service';

Ext.onReady(function(){
	
	var gtid = '';

	Ext.QuickTips.init();
	
	Ext.getDoc().on("contextmenu", function(e){
		e.stopEvent();
	});

	/**
	 * 业务查询-查询数据模型定义及绑定
	 */
	adjust3.searchStore = new Ext.data.Store({
		autoLoad : true , 
		url : loadUrl,
		baseParams : {
			'status':'32',
			'status1':'33',
			start : 0,
			limit : 50
		},
		reader : new Ext.data.JsonReader({
			root : 'data' , 
			totalProperty : 'total' , 
			fields : [
					  {name : 'adjustId' ,mapping : 'adjustId'},//调整id
			          {name : 'reportTime' ,mapping : 'reportTime'},//业务日期
			          {name : 'adjustSystem' ,mapping : 'adjustSystem'},//调整业务系统名称
			          {name : 'adjustBusiness' ,mapping : 'adjustBusiness'},//调整的业务
			          {name : 'reportId' ,mapping : 'reportId'},//reportId
			          {name : 'adjustReason' ,mapping : 'adjustReason'},//调整的原因
			          {name : 'adjuster' ,mapping : 'adjuster'},//调整人
			          {name : 'adjustTime' ,mapping : 'adjustTime'},//调整时间
			          {name : 'oldValue' ,mapping : 'oldValue'},//调整前金额
			          {name : 'newValue' ,mapping : 'newValue'},//调整后金额
			          {name : 'flag' ,mapping : 'flag'},//收款/付款
			          {name : 'status' ,mapping : 'status'}, //状态
			          
			          {name : 'checker' ,mapping : 'checker'},//审核人
			          {name : 'checkMsg' ,mapping : 'checkMsg'}//审核信息
					  ]
		})
	});
	
	/**
	 * a3数据模型定义及绑定
	 */
	adjust3.a3Store = new Ext.data.Store({
		autoLoad : false , 
		url : loadA3Url,
		baseParams : {
			start : 0,
			limit : 5
		},
		reader : new Ext.data.JsonReader({
			root : 'data' , 
			totalProperty : 'total' , 
			fields : [
			          {name : 'gtid' ,mapping : 'gtid'},
			          {name : 'tr_note' ,mapping : 'tr_note'},//摘要
			          {name : 'ac_code' ,mapping : 'ac_code'},//科目
			          {name : 'tr_de' ,mapping : 'tr_de'},//借方
			          {name : 'tr_cr' ,mapping : 'tr_cr'},//贷方
			          {name : 'adjustId' ,mapping : 'adjustId'},//调整id
			          {name : 'status' ,mapping : 'status'}//状态
				]
		})
	});
	
	adjust3.searchStore.load();
	
	/**
	 * 数据列名定义
	 */
	adjust3.colModel = new Ext.grid.ColumnModel({
		columns: [
		          new Ext.grid.RowNumberer(),
		           { header : '调整id' ,id : 'adjustId' , dataIndex : 'adjustId',width: 150,hidden:true},
		           { header: "业务日期",  id : 'reportTime' , dataIndex : 'reportTime', width:150},
		           { header : '调整业务系统名称' ,id : 'adjustSystem' , dataIndex : 'adjustSystem',width: 150},
				   { header: "调整的业务",  id : 'adjustBusiness' , dataIndex : 'adjustBusiness'},
				   { header: "reportId",  id : 'reportId' , dataIndex : 'reportId',hidden:true},
				   { header: "调整的原因",  id : 'adjustReason' , dataIndex : 'adjustReason'},
		           { header: "调整人",  id : 'adjuster' , dataIndex : 'adjuster'},
		           { header : '调整时间' ,id : 'adjustTime' , dataIndex : 'adjustTime',width: 150},
				   { header: "调整前金额",  id : 'oldValue' , dataIndex : 'oldValue'},
				   { header: "调整后金额",  id : 'newValue' , dataIndex : 'newValue'},
		           { header: "收款/付款",  id : 'flag' , dataIndex : 'flag', width:150},
		           { header: "状态",  id : 'status' , dataIndex : 'status', width:150,renderer: adjustStatus},
		           
		           { header: "审核人",  id : 'checker' , dataIndex : 'checker', width:150},
		           { header: "审核信息",  id : 'checkMsg' , dataIndex : 'checkMsg', width:150}
		       ],
		 defaults: {
		       sortable: true,
		       menuDisabled: true,
		       width: 120
		 }
	});
	
	/**
	 * a3数据列名定义
	 */
	adjust3.a3colModel = new Ext.grid.ColumnModel({
		columns: [
		          new Ext.grid.RowNumberer(),
		          { header: "gtid",  id : 'gtid' , dataIndex : 'gtid',hidden:true},
				   { header: "摘要",  id : 'tr_note' , dataIndex : 'tr_note', width:240},
				   { header: "科目",  id : 'ac_code' , dataIndex : 'ac_code'},
		           { header: "借方",  id : 'tr_de' , dataIndex : 'tr_de'},
		           { header: '贷方' ,id : 'tr_cr' , dataIndex : 'tr_cr'},
		           { header: '调整id' ,id : 'adjustId' , dataIndex : 'adjustId',hidden:true},
		           { header: '状态' ,id : 'status' , dataIndex : 'status',hidden:true}
		       ],
		 defaults: {
		       sortable: true,
		       menuDisabled: true,
		       width: 120
		 }
	});

	var adjustId = '';
	adjust3.editAction = new Ext.Action({
		text : '生成凭证',
		id:'add',
		disabled : false,
		iconCls : 'add',
		handler : function() {
			if(adjustId === '') {
	            Ext.MessageBox.alert('提示', '请选择一条需生成凭证数据');
	        } else {
	        	 Ext.Ajax.request({
	                url : generateA3Url,
	                method : 'post',
	                params :{
	                    'adjustId' :adjustId
	                },
	                success : function(resp , opts){
	                    var respObj = Ext.util.JSON.decode(resp.responseText);
	                    if(respObj.success){
	                        Ext.Msg.alert('提示',respObj.msg);

	                        adjust3.a3Store.baseParams = {
           						'start':0,
           						'limit':5,
           						'adjustId':adjustId
           					};
	                        adjust3.searchStore.load();
           					adjust3.a3Store.load();
           					adjust3.editAction.disable();
	                    }else{
	                        Ext.Msg.alert('提示',respObj.msg);
	                        return;
	                    }
	                },
	                failure: function(resp , opts) {
	                    Ext.Msg.alert('提示','操作失败');
	                    return;
	                }
	            });

	        }
		}
	});
	
	adjust3.adjustAction = new Ext.Action({
		text : '调整凭证',
		id:'edit',
		disabled : false,
		iconCls : 'edit',
		handler : function() {
			if(gtid === '') {
	            Ext.MessageBox.alert('提示', '请选择一条需调整凭证数据');
	        } else {
	        	var index = adjust3.a3Store.find('gtid',gtid);
	        	adjust3.a3form.getForm().reset();
	        	
	        	Ext.getCmp('gtid').setValue(adjust3.a3Store.getAt(index).data.gtid);
	        	Ext.getCmp('tr_note').setValue(adjust3.a3Store.getAt(index).data.tr_note);
	        	Ext.getCmp('ac_code').setValue(adjust3.a3Store.getAt(index).data.ac_code);
	        	Ext.getCmp('tr_de').setValue(adjust3.a3Store.getAt(index).data.tr_de);
	        	Ext.getCmp('tr_cr').setValue(adjust3.a3Store.getAt(index).data.tr_cr);
	        	Ext.getCmp('adjustId').setValue(adjust3.a3Store.getAt(index).data.adjustId);
	        	Ext.getCmp('status').setValue(adjust3.a3Store.getAt(index).data.status);
	        	
	        	adjust3.a3win.show();
				adjust3.a3win.center();
	        }
		}
	});
	
	adjust3.importAction = new Ext.Action({
		text : '导入',
		id:'import',
		disabled : false,
		iconCls : 'disk',
		handler : function() {
			if(adjustId === '') {
	            Ext.MessageBox.alert('提示', '请选择一条需导入的数据');
	            return;
	        } else {
	        	if(adjust3.a3selectGird.getStore().getCount()<1){
	        		Ext.MessageBox.alert('提示', '没有可导入数据');
	        		return;
	        	}
	        	 Ext.Ajax.request({
		                url : importA3DbUrl,
		                method : 'post',
		                params :{
		                    'adjustId' :adjustId
		                },
		                success : function(resp , opts){
		                    var respObj = Ext.util.JSON.decode(resp.responseText);
		                    if(respObj.success){
		                        Ext.Msg.alert('提示',respObj.msg);

		                        adjust3.a3Store.baseParams = {
	           						'start':0,
	           						'limit':5,
	           						'adjustId':adjustId
	           					};
	           					adjust3.a3Store.load();
		                    }else{
		                        Ext.Msg.alert('提示',respObj.msg);
		                        return;
		                    }
		                },
		                failure: function(resp , opts) {
		                    Ext.Msg.alert('提示','操作失败');
		                    return;
		                }
		            });
	        }
		}
	});
	
	/**
	 *查询表单网格
	 */
	adjust3.selectGird = new Ext.grid.GridPanel({
		
		margins : '0',
		title : '手工调整（财务核算）',
		region : 'north',
		height : 180,
		stripeRows : true,
		split : true,
		autoDestroy : true,
		store : adjust3.searchStore,
		colModel : adjust3.colModel,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		listeners : {
			'cellclick' : function(grid, rowIndex, columnIndex, event) {
				if (grid.getSelectionModel().getSelected()) {
					
					var sr = grid.getSelectionModel().getSelected();
					adjustId = sr.get('adjustId');

					var reportId = sr.get('reportId');
					if(sr.get('status')=='33'){
						adjust3.editAction.disable();
					}else{
						adjust3.editAction.enable();
					}
					var adjustGrid=Ext.get('grid');
					adjustGrid.dom.src = ctx +'/doJsp/adjustGridService?menuBar=0&data_type=30&status=32&adjustId='+adjustId+'&reportId=' + reportId;
					
					adjust3.a3Store.baseParams = {
						'start':0,
						'limit':5,
						'adjustId':adjustId
					};
					adjust3.a3Store.load();
				}
			}
		},
		bbar : new Ext.PagingToolbar({
			store : adjust3.searchStore,
			displayInfo : true,
			pageSize : 50,
			doRefresh:function(){
        		 return false;
			}
		})
		
	});
	
	/**
	 * a3查询表单网格
	 */
	adjust3.a3selectGird = new Ext.grid.GridPanel({
		
		margins : '0',
		title : 'a3凭证',
		region : 'center',
		stripeRows : true,
		split : true,
		autoDestroy : true,
		store : adjust3.a3Store,
		colModel : adjust3.a3colModel,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		listeners : {
			'cellclick' : function(grid, rowIndex, columnIndex, event) {
				if (grid.getSelectionModel().getSelected()) {
					
					var sr = grid.getSelectionModel().getSelected();
					gtid = sr.get('gtid');
				}
			}
		},
		tbar : [ 
				'-',
				adjust3.editAction,
				'-',
				adjust3.adjustAction,
				'-',
				adjust3.importAction,
				'-'
		],
		bbar : new Ext.PagingToolbar({
			store : adjust3.a3Store,
			displayInfo : true,
			pageSize : 50,
			doRefresh:function(){
        		 return false;
			}
		})
		
	});
	
	adjust3.form = new Ext.form.FormPanel({
		labelAlign: 'right',
		labelWidth:120,
		region : 'north',
		frame:true,
		defaultType:'textfield',
		defaults : {
			width : 200
		},
		items : [
				 {
			       	 fieldLabel : '调整报表',
			    	 id : 'adjustReport',
			    	 name : 'adjustReport',
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '调整列',
			    	 id : 'adjustColumn',
			    	 name : 'adjustColumn',
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '业务日期',
			    	 id : 'reportTime',
			    	 name : 'reportTime',
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '调整业务系统名称',
			    	 id : 'adjustSystem',
			    	 name : 'adjustSystem',
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '调整的业务',
			    	 id : 'adjustBusiness',
			    	 name : 'adjustBusiness',
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '调整的原因',
			    	 id : 'adjustReason',
			    	 name : 'adjustReason',
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '调整人',
			    	 id : 'adjuster',
			    	 name : 'adjuster',
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '调整时间',
			    	 id : 'adjustTime',
			    	 name : 'adjustTime',
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '调整前金额',
			    	 id : 'oldValue',
			    	 name : 'oldValue',
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '调整后金额',
			    	 id : 'newValue',
			    	 name : 'newValue',
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '收款/付款',
			    	 id : 'flag',
			    	 name : 'flag',
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '审核人',
			    	 id : 'checker',
			    	 name : 'checker'
				 },
				 {
			       	 fieldLabel : '审核信息',
			    	 id : 'checkMsg',
			    	 name : 'checkMsg'
				 }
		]
	});
	
	adjust3.win = new Ext.Window({
		title : '手工调整（财务核算）',
		width : 400 , 
		height : 400 ,
		closable : false,
		autoScroll:true,
		modal:true,
		layout : 'fit',
		items : [adjust3.form],
		buttons : [{
			text : '保存',
			id : 'MSAccept',
			iconCls : 'accept' ,
			handler : function(){

		}
		},{
			text : '关闭',
			iconCls : 'cancel',
			handler : function(){
				adjust3.win.hide();
			}
		}]
	});
	
	// a3
	adjust3.a3form = new Ext.form.FormPanel({
		labelAlign: 'right',
		labelWidth:120,
		region : 'north',
		frame:true,
		defaultType:'textfield',
		defaults : {
			width : 200
		},
		items : [
				 {
			       	 fieldLabel : 'gtid',
			    	 id : 'gtid',
			    	 name : 'gtid',
			    	 hidden : true
				 },
				 {
			       	 fieldLabel : '摘要',
			    	 id : 'tr_note',
			    	 name : 'tr_note',
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '科目',
			    	 id : 'ac_code',
			    	 name : 'ac_code',
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '借方',
			    	 id : 'tr_de',
			    	 name : 'tr_de',
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '贷方',
			    	 id : 'tr_cr',
			    	 name : 'tr_cr',
			    	 readOnly : false
				 },
				 {
			       	 fieldLabel : '调整id',
			    	 id : 'adjustId',
			    	 name : 'adjustId',
			    	 hidden : true
				 },
				 {
			       	 fieldLabel : '状态',
			    	 id : 'status',
			    	 name : 'status',
			    	 hidden : true
				 }
		]
	});
	
	adjust3.a3win = new Ext.Window({
		title : '凭证调整',
		width : 400 , 
		height : 400 ,
		closable : false,
		autoScroll:true,
		modal:true,
		layout : 'fit',
		items : [adjust3.a3form],
		buttons : [{
			text : '保存',
			id : 'a3.MSAccept',
			iconCls : 'accept' ,
			handler : function(){

				var f = adjust3.a3form;
				if(f.form.isValid()){
			          f.form.submit({
			                waitMsg : '正在提交,请稍后...',
			                url : ctx + '/doJson/editAdjustA3Service.json',
			                method : 'POST',
			                success : function(form, action) {
			                       if(action.result.success){
			                    	   Ext.MessageBox.alert('操作', action.result.msg);
			                    	   
			                    	   adjust3.a3win.hide();
			                    	   
			                    	   adjust3.a3Store.baseParams = {
			           						'start':0,
			           						'limit':5,
			           						'adjustId':adjustId
			           					};
			           					adjust3.a3Store.load();
			                       } else {
			                    	   Ext.MessageBox.alert('操作', action.result.msg);
			                       }
			                },
			                failure : function(form, action) {
			                	Ext.MessageBox.alert('操作', '操作失败，请重试');
			            }});
			    };
		}
		},{
			text : '关闭',
			iconCls : 'cancel',
			handler : function(){
				adjust3.a3win.hide();
			}
		}]
	});
	
	/**
	 * 查询呈现视图
	 */
	adjust3.view = new Ext.Viewport({
		layout : 'border',
		items : [ adjust3.selectGird,
		          adjust3.a3selectGird,
		          new Ext.Panel({
						region : 'south',
						margins : '0',
						height : 180,
						split : true,
						autoDestroy : true,
						stripeRows : true,
						html : '<iframe id="grid" scrolling="auto" frameborder="0" width="100%" height="100%" src="'+ ctx +'/doJsp/adjustGridService"></iframe>'
					})
		    ]
	});
function adjustStatus(value){
   		var str = "";
		switch (value) {
			case "31":
				str = "初始插入";
				break;
			case "32":
				str = "审核之后";
				break;
			case "33":
				str = "生成凭证之后";
				break;
			case "39":
				str = "初始化删除";
				break;
			default:
				break;
		}
		return str;

	}
});
