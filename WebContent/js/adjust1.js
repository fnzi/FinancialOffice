/**
 * 手工调整（当事人）
 * 
 */
var adjust1 ={};

var loadUrl = ctx + '/doJson/getAdjustService';

Ext.onReady(function(){

	Ext.QuickTips.init();
	
	Ext.getDoc().on("contextmenu", function(e){
		e.stopEvent();
	});

	/**
	 * 业务查询-查询数据模型定义及绑定
	 */
	adjust1.searchStore = new Ext.data.Store({
		autoLoad : true , 
		url : loadUrl,
		baseParams : {
			'status':31,
			start : 0,
			limit : 5
		},
		reader : new Ext.data.JsonReader({
			root : 'data' , 
			totalProperty : 'total' , 
			fields : [
					  {name : 'adjustId' ,mapping : 'adjustId'},//调整id
			          {name : 'reportTime' ,mapping : 'reportTime'},//业务日期
			          {name : 'adjustSystem' ,mapping : 'adjustSystem'},//调整业务系统名称
			          {name : 'reportName' ,mapping : 'reportName'},//reportName
			          {name : 'adjustBusiness' ,mapping : 'adjustBusiness'},//调整的业务
			          {name : 'reportId' ,mapping : 'reportId'},//reportId
			          {name : 'adjustReason' ,mapping : 'adjustReason'},//调整的原因
			          {name : 'adjuster' ,mapping : 'adjuster'},//调整人
			          {name : 'adjustTime' ,mapping : 'adjustTime'},//调整时间
			          {name : 'oldValue' ,mapping : 'oldValue'},//调整前金额
			          {name : 'newValue' ,mapping : 'newValue'},//调整后金额
			          {name : 'flag' ,mapping : 'flag'},//收款/付款
			          {name : 'status' ,mapping : 'status'} //状态
				]
		})
	});
	
	adjust1.searchStore.load();
	
	/**
	 * 数据列名定义
	 */
	adjust1.colModel = new Ext.grid.ColumnModel({
		columns: [
		          new Ext.grid.RowNumberer(),
		           { header : '调整id' ,id : 'adjustId' , dataIndex : 'adjustId',width: 150,hidden:true},
		           { header: "业务日期",  id : 'reportTime' , dataIndex : 'reportTime', width:150},
		           { header : '调整业务系统名称' ,id : 'adjustSystem' , dataIndex : 'adjustSystem',width: 150},
		           { header: "调整报表名称",  id : 'reportName' , dataIndex : 'reportName',width:150},
				   { header: "调整的业务",  id : 'adjustBusiness' , dataIndex : 'adjustBusiness'},
				   { header: "reportId",  id : 'reportId' , dataIndex : 'reportId',hidden:true},
				   { header: "调整的原因",  id : 'adjustReason' , dataIndex : 'adjustReason'},
		           { header: "调整人",  id : 'adjuster' , dataIndex : 'adjuster'},
		           { header : '调整时间' ,id : 'adjustTime' , dataIndex : 'adjustTime',width: 150},
				   { header: "调整前金额",  id : 'oldValue' , dataIndex : 'oldValue'},
				   { header: "调整后金额",  id : 'newValue' , dataIndex : 'newValue'},
		           { header: "收款/付款",  id : 'flag' , dataIndex : 'flag', width:150},
		           { header: "状态",  id : 'status' , dataIndex : 'status', width:150, renderer: adjustStatus}
		       ],
		 defaults: {
		       sortable: true,
		       menuDisabled: true,
		       width: 120
		 }
	});
	
	adjust1.addAction = new Ext.Action({
		text : '调整',
		id:'add',
		disabled : false,
		iconCls : 'add',
		handler : function() {
			adjust1.win.show();
			adjust1.win.center();
			adjust1.form.form.reset();
		}
	});
	
	adjust1.searchAction = new Ext.Action({
	    text : '查询',
	    id:'search',
	    disabled : false,
	    iconCls : 'search',
	    handler : function() {
	    	adjust1.searchStore.load();
	    }
	});
	
	var adjustId = '';
	adjust1.deleteAction = new Ext.Action({
		text : '删除',
		id:'delete',
		disabled : false,
		iconCls : 'delete',
		handler : function() {
			if(adjustId === '') {
	            Ext.MessageBox.alert('提示', '请选择一条待删除数据');
	        } else {
	            Ext.Ajax.request({
	                url : ctx + '/doJson/updateStatusAdjustService.json',
	                method : 'post',
	                params :{
	                    'adjustId' :adjustId,
	                    'status' :'39'
	                },
	                success : function(resp , opts){
	                    var respObj = Ext.util.JSON.decode(resp.responseText);
	                    if(respObj.success){
	                        Ext.Msg.alert('提示',respObj.msg);
	                        adjust1.searchStore.load();
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
	adjust1.selectGird = new Ext.grid.GridPanel({
		
		margins : '5 5 1 5',
		title : '手工调整（当事人）',
		region : 'center',
		stripeRows : true,
		autoDestroy : true,
		split : true,
		store : adjust1.searchStore,
		colModel : adjust1.colModel,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		listeners : {
			'cellclick' : function(grid, rowIndex, columnIndex, event) {
				if (grid.getSelectionModel().getSelected()) {
					
					var sr = grid.getSelectionModel().getSelected();
					var reportId = sr.get('reportId');
					adjustId = sr.get('adjustId');
					
					var adjustGrid=Ext.get('grid');
					adjustGrid.dom.src = ctx +'/doJsp/adjustGridService?menuBar=1&data_type=30&status=31&adjustId='+adjustId+'&reportId=' + reportId;
				}
			}
		},
		tbar : [ 
				'-',
				adjust1.addAction,
		         '-',
		         adjust1.deleteAction,
				'-',
				adjust1.searchAction,
				'-'
		],
		bbar : new Ext.PagingToolbar({
			store : adjust1.searchStore,
			displayInfo : true,
			pageSize : 5,
			doRefresh:function(){
        		 return false;
			}
		})
		
	});

	/** 显示报表系统列表 **/
	adjust1.loadReportSysStore = new Ext.data.Store({
		url : ctx + '/doJson/getReportSysService',
		autoLoad : true, //是否自动加载
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',	//后台返回的JSON中标识记录总数键名
			root : 'data',	//后台返回的JSON中实际存放数据的键名
			id : 'id'
		},
		[
         { name: 'id', mapping: 'id' },
         { name: 'name', mapping: 'name'}
         ]
		)
	});
	
	/** 显示报表列表 **/
	adjust1.loadReportNameStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({   
			url : ctx + '/doJson/getReportNameService?p=1',
		}),		
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',	//后台返回的JSON中标识记录总数键名
			root : 'data',	//后台返回的JSON中实际存放数据的键名
		},
		[
         { name: 'reportId', mapping: 'reportId' },
         { name: 'reportName', mapping: 'reportName'}
         ]
		)
	});
	
	adjust1.form = new Ext.form.FormPanel({
		labelAlign: 'right',
		labelWidth:120,
		region : 'center',
		frame:true,
		defaultType:'textfield',
		defaults : {
			width : 200
		},
		items : [
				 {
					 allowBlank : false ,
			       	 fieldLabel : '调整业务系统名称',
			    	 id : 'adjustSystem',
			    	 name : 'adjustSystem',
			       	 xtype : 'combo',
			       	 emptyText:'请选择业务系统',
			       	 triggerAction: 'all',
			    	 mode: 'local',
			    	 store : adjust1.loadReportSysStore,
			    	 valueField: 'name',                 
			    	 displayField: 'name',
			    	 hiddenName : 'adjustSystem',
			    	 listeners: {
			    		 select : function(checked){
			    			 var index = adjust1.loadReportSysStore.find('name', checked.getValue());
			    			 var sid = adjust1.loadReportSysStore.getAt(index).get('id');
			    			 
			    			 adjust1.loadReportNameStore.proxy = new Ext.data.HttpProxy({
			    				 url : ctx + '/doJson/getReportNameService?sysId=' + sid,
			    			 });
			    			 adjust1.loadReportNameStore.load();
			    		 }
			    	 }
				 },
		         {
		        	 fieldLabel : '调整报表名称', // 下拉：显示报表名称（名称/id）
		        	 id : 'reportId',
		        	 name : 'reportId',
		        	 allowBlank : false ,
		        	 xtype : 'combo',
		        	 emptyText:'请选择报表',
		        	 mode: 'local',
		        	 triggerAction: 'all',
		        	 store : adjust1.loadReportNameStore,
		        	 valueField: 'reportId',                 
		        	 displayField: 'reportName',
		        	 hiddenName : 'reportId',
		        	 listeners: {
			    		 select : function(checked){
			    			 var index = adjust1.loadReportNameStore.find('reportId', checked.getValue());
			    			 Ext.getCmp("reportName").setValue(adjust1.loadReportNameStore.getAt(index).get('reportName'));
			    		 }
			    	 }
				 },
				 {
					 xtype: 'textfield', 
		        	 id : 'reportName',
		        	 name : 'reportName',
		        	 hidden: true,
		        	 hideLabel:true 
				 },
				 {
					 fieldLabel : '业务日期',
					 xtype: 'datefield',//日期类型
					 format:'Y-m-d',
					 id : 'reportTime',
					 name : 'reportTime'
				 },
				 {
			       	 fieldLabel : '调整的业务',
			    	 id : 'adjustBusiness',
			    	 name : 'adjustBusiness'
				 },
				 {
			       	 fieldLabel : '调整的原因',
			    	 id : 'adjustReason',
			    	 name : 'adjustReason'
				 },
				 {
			       	 fieldLabel : '调整人',
			    	 id : 'adjuster',
			    	 name : 'adjuster'
				 },
				 {
			       	 fieldLabel : '调整时间',
					 xtype: 'datefield', //日期类型
					 allowBlank : false ,
					 format:'Y-m-d',
			    	 id : 'adjustTime',
			    	 name : 'adjustTime'
				 },
				 {
			       	 fieldLabel : '调整前金额',
			    	 id : 'oldValue',
			    	 name : 'oldValue'
				 },
				 {
			       	 fieldLabel : '调整后金额',
			    	 id : 'newValue',
			    	 name : 'newValue'
				 },
				 {
			       	 fieldLabel : '收款/付款',
			    	 id : 'flag',
			    	 name : 'flag'
				 }
		]
	});
	
	adjust1.win = new Ext.Window({
		title : '手工调整（当事人）',
		width : 400 , 
		height : 400 ,
		closable : false,
		autoScroll:true,
		modal:true,
		layout : 'fit',
		items : [adjust1.form],
		buttons : [{
			text : '保存',
			id : 'MSAccept',
			iconCls : 'accept' ,
			handler : function(){
				
				var f = adjust1.form;
				
                if(f.form.isValid()){
                    f.form.submit({
                         waitMsg : '正在保存,请稍后...',
                         url : ctx + '/doJson/addAdjustService.json',
                         method : 'POST',
                         success : function(form, action) {
                             if(action.result.success){
                                 Ext.MessageBox.alert('操作结果', action.result.msg);
                                 adjust1.searchStore.load();
                                 adjust1.win.hide();
                             } else {
                                 Ext.MessageBox.alert('操作结果', action.result.msg);
                             }
                         },
                         failure : function(form, action) {
                            f.form.reset();
                         }
                    });
                }
		}
		},{
			text : '关闭',
			iconCls : 'cancel',
			handler : function(){
				adjust1.win.hide();
			}
		}]
	});
	
	
	/**
	 * 查询呈现视图
	 */
	adjust1.view = new Ext.Viewport({
		layout: "border",  
		items : [
			adjust1.selectGird,
			new Ext.Panel({
				region : 'south',
				margins : '1 5 5 5',
				height : 300,
				split : true,
				autoDestroy : true,
				stripeRows : true,
				html : '<iframe id="grid" scrolling="auto" frameborder="0" width="100%" height="100%" src="'+ ctx +'/doJsp/adjustGridService?adjustId="'+adjustId+'></iframe>'
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
