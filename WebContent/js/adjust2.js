/**
 * 手工调整（审核）
 * 
 */
var adjust2 ={};

var loadUrl = ctx + '/doJson/getAdjustService';

Ext.onReady(function(){

	Ext.QuickTips.init();
	
	Ext.getDoc().on("contextmenu", function(e){
		e.stopEvent();
	});

	/**
	 * 业务查询-查询数据模型定义及绑定
	 */
	adjust2.searchStore = new Ext.data.Store({
		autoLoad : true , 
		url : loadUrl,
		baseParams : {
			'status':31,
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
	
	adjust2.searchStore.load();
	
	/**
	 * 数据列名定义
	 */
	adjust2.colModel = new Ext.grid.ColumnModel({
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
	 * 编辑
	 */
	adjust2.editAdjust2Action = new Ext.Action({
		text : '审核',
		id :'edit',
		disabled : false,
		iconCls : 'edit',
		handler : function() {
			var grid = Ext.getCmp("adjust2Grid");
			var sr = grid.getSelectionModel().getSelected();
			if (!sr) {
				Ext.Msg.alert('提示', '请选择一条记录！');
				return false;
			}
			var adjustId = sr.get('adjustId');
			
			Ext.Ajax.request({
				url: ctx + '/doJson/getSysAdjustByIdService',
				method: "post",
				params:{adjustId : adjustId},
				success: function (resp, opts){
					var respObj = Ext.util.JSON.decode(resp.responseText);
					if (respObj.total == 1) {
						// 回写表单的数据
						var form = Ext.getCmp("reviewForm");
						form.form.setValues(respObj.data[0]);
						// Ext.getCmp("adjustSystem").setValue(respObj.data[0].adjustSystem);
						adjust2.win.show();
						adjust2.win.center();
					}
				},
				failure:function (resp, opts){
					Ext.Msg.alert('提示', '请联系工程师！');
				}
			});
		}
	});
	
	/**
	 *查询表单网格
	 */
	adjust2.selectGird = new Ext.grid.GridPanel({
		id : 'adjust2Grid',
		margins : '5 5 5 5',
		title : '手工调整（审核）',
		region : 'center',
		stripeRows : true,
		split : true,
		autoDestroy : true,
		store : adjust2.searchStore,
		colModel : adjust2.colModel,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		listeners : {
			'cellclick' : function(grid ,rowIndex , columnIndex, event){
				if(grid.getSelectionModel().getSelected()){
					var sr = grid.getSelectionModel().getSelected();
					reportId = sr.get('reportId');
					adjustId = sr.get('adjustId');
					
					var adjustGrid=Ext.get('grid');
					adjustGrid.dom.src = ctx +'/doJsp/adjustGridService?menuBar=0&data_type=30&status=31&adjustId='+adjustId+'&reportId=' + reportId;
				}
			}
		},
		tbar : [ 
		        adjust2.editAdjust2Action
		],
		bbar : new Ext.PagingToolbar({
			store : adjust2.searchStore,
			displayInfo : true,
			pageSize : 50,
			doRefresh:function(){
        		 return false;
			}
		})
		
	});
	
	adjust2.form = new Ext.form.FormPanel({
		id: 'reviewForm',
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
			       	 fieldLabel : '调整业务系统名称',
			    	 id : 'adjustSystem',
			    	 name : 'adjustSystem',
			    	 readOnly : true
				 },
				 {
			       	 fieldLabel : '调整报表名称',
			    	 id : 'reportName',
			    	 name : 'reportName',
			    	 readOnly : true
				 },
				 {
			       	 fieldLabel : '业务日期',
			    	 id : 'reportTime',
			    	 name : 'reportTime',
			    	 readOnly : true
				 },
				 {
			       	 fieldLabel : '调整的业务',
			    	 id : 'adjustBusiness',
			    	 name : 'adjustBusiness',
			    	 readOnly : true
				 },
				 {
			       	 fieldLabel : '调整的原因',
			    	 id : 'adjustReason',
			    	 name : 'adjustReason',
			    	 readOnly : true
				 },
				 {
			       	 fieldLabel : '调整人',
			    	 id : 'adjuster',
			    	 name : 'adjuster',
			    	 readOnly : true
				 },
				 {
			       	 fieldLabel : '调整时间',
			    	 id : 'adjustTime',
			    	 name : 'adjustTime',
			    	 readOnly : true
				 },
				 {
			       	 fieldLabel : '调整前金额',
			    	 id : 'oldValue',
			    	 name : 'oldValue',
			    	 readOnly : true
				 },
				 {
			       	 fieldLabel : '调整后金额',
			    	 id : 'newValue',
			    	 name : 'newValue',
			    	 readOnly : true
				 },
				 {
			       	 fieldLabel : '收款/付款',
			    	 id : 'flag',
			    	 name : 'flag',
			    	 readOnly : true
				 },
				 {
			       	 fieldLabel : '审核人',
			    	 id : 'checker',
			    	 allowBlank:false,
			    	 name : 'checker'
				 },
				 {
			       	 fieldLabel : '审核信息',
			       	 allowBlank:false,
			    	 id : 'checkMsg',
			    	 name : 'checkMsg'
				 }
		]
	});
	
	adjust2.win = new Ext.Window({
		title : '手工调整（审核）',
		width : 400 , 
		height : 400 ,
		closable : false,
		autoScroll:true,
		modal:true,
		layout : 'fit',
		items : [adjust2.form],
		buttons : [{
			text : '保存',
			id : 'MSAccept',
			iconCls : 'accept' ,
			handler : function(){
				
				var f = adjust2.form;
				
                if(f.form.isValid()){
                    f.form.submit({
                         waitMsg : '正在保存,请稍后...',
                         url : ctx + '/doJson/addAdjustService?meth=edit&adjustId='+adjustId+'&reportId=' + reportId,
                         method : 'POST',
                         success : function(form, action) {
                             if(action.result.success){
                                 Ext.MessageBox.alert('操作结果', action.result.msg);
                                 adjust2.searchStore.load();
                                 Ext.getCmp('adjust2selectGird').load({url:ctx +'/doJsp/adjustGridService'});
                                 adjust2.win.hide();
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
				adjust2.win.hide();
			}
		}]
	});
	
	
	/**
	 * 查询呈现视图
	 */
	adjust2.view = new Ext.Viewport({
		layout : 'border',
		items : [ adjust2.selectGird,
		          new Ext.Panel({
		        	    id:'adjust2selectGird',
						region : 'south',
						margins : '1 5 5 5',
						height : 300,
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
