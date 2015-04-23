/**
 *  A3凭证导入
 * 
 */
var a3Diff ={};

var loadUrl = ctx + '';

Ext.onReady(function(){

	Ext.QuickTips.init();
	
	Ext.getDoc().on("contextmenu", function(e){
		e.stopEvent();
	});

	/**
	 * 业务查询-查询数据模型定义及绑定
	 */
	a3Diff.searchStore = new Ext.data.Store({
		autoLoad : true , 
		url : loadUrl,
		baseParams : {
			start : 0,
			limit : 50
		},
		reader : new Ext.data.JsonReader({
			root : 'data' , 
			totalProperty : 'total' , 
			fields : [
					  {name : 'aid', mapping : 'aid'},
			          {name : 'num', mapping : 'num'},
			          {name : 'summary', mapping : 'summary'},
			          {name : 'subject', mapping : 'subject'},
			          {name : 'debit', mapping : 'debit'}, // 借方
			          {name : 'credit', mapping : 'credit'} // 贷方
					  ]
		})
	});
	
	a3Diff.searchStore.load();
	
	/**
	 * 数据列名定义
	 */
	a3Diff.colModel = new Ext.grid.ColumnModel({
		columns: [
		          new Ext.grid.RowNumberer(),
		           { header : 'id', id : 'aid', dataIndex : 'aid', width: 150, hidden:true},
				   { header: "序号",  id : 'num', dataIndex : 'num'},
				   { header: "摘要",  id : 'summary', dataIndex : 'summary'},
		           { header: "科目",  id : 'subject', dataIndex : 'subject'},
		           { header: "借方",  id : 'debit', dataIndex : 'debit'},
		           { header: "贷方",  id : 'credit', dataIndex : 'credit'}
		       ],
		 defaults: {
		       sortable: true,
		       menuDisabled: true,
		       width: 120
		 }
	});
	

	a3Diff.searchAction = new Ext.Action({
		text : '导入',
		id:'disk',
		disabled : false,
		iconCls : 'disk',
		handler : function() {
			
		}
	});
	
	/**
	 *查询表单网格
	 */
	a3Diff.selectGird = new Ext.grid.GridPanel({
		
		margins : '5 5 5 5',
		title : 'A3凭证导入',
		region : 'center',
		stripeRows : true,
		autoDestroy : true,
		store : a3Diff.searchStore,
		colModel : a3Diff.colModel,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		tbar : [ 
				a3Diff.searchAction
		],
		bbar : new Ext.PagingToolbar({
			store : a3Diff.searchStore,
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
	a3Diff.view = new Ext.Viewport({
		layout : 'border',
		items : [ a3Diff.selectGird ]
	});
	
});