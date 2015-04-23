/**
 *  日报导入状态
 * 
 */
var transmitReportStatus ={};

var loadUrl = ctx + '/doJson/getTransmitReportStatusService';

Ext.onReady(function(){

	var reportId = '';
	var rsid = '';
	
	Ext.QuickTips.init();
	
	Ext.getDoc().on('contextmenu', function(e){
		e.stopEvent();
	});

	/**
	 * 业务查询-查询数据模型定义及绑定
	 */
	transmitReportStatus.searchStore = new Ext.data.Store({
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
			          {name : 'rsid' ,mapping : 'rsid'},
					  {name : 'reportId' ,mapping : 'reportId'},//报表id
			          {name : 'reportName' ,mapping : 'reportName'},//报表名称
			          {name : 'status' ,mapping : 'status'},//导入状态
			          {name : 'reportTime' ,mapping : 'reportTime'}//操作时间
					  ]
		})
	});
	
	transmitReportStatus.searchStore.load();
	
	/**
	 * 数据列名定义
	 */
	transmitReportStatus.colModel = new Ext.grid.ColumnModel({
		columns: [
		          new Ext.grid.RowNumberer(),
		          { header : 'rsid' ,id : 'rsid' , dataIndex : 'rsid',width: 150,hidden:true},
		           { header : 'id' ,id : 'reportId' , dataIndex : 'reportId',width: 150,hidden:true},
				   { header: '报表名称',  id : 'reportName' , dataIndex : 'reportName'},
				   { header: '导入状态',  id : 'status' , dataIndex : 'status'},
		           { header: '导入时间',  id : 'reportTime' , dataIndex : 'reportTime', renderer:rendererForDate,width:150}
		       ],
		 defaults: {
		       sortable: true,
		       menuDisabled: true,
		       width: 120
		 }
	});
	

	transmitReportStatus.searchAction = new Ext.Action({
		text : '导入',
		id:'disk',
		disabled : false,
		iconCls : 'disk',
		handler : function() {
			if(reportId != '') {
				Ext.Ajax.request({
					url: ctx + '/doJson/importReportByManualService',
					method: "post",
					params:{reportId : reportId, rsid: rsid},
					success: function (resp, opts){
						var respObj = Ext.util.JSON.decode(resp.responseText);
						if (respObj.success) {
							Ext.Msg.alert('提示', respObj.msg);
							
							transmitReportStatus.searchStore.load();
						} else {
							Ext.Msg.alert('提示', respObj.msg);
						}
					},
					failure:function (resp, opts){
						Ext.Msg.alert('提示', '请联系工程师！');
					}
				});
			} else {
				Ext.Msg.alert('提示', '请选择导入记录');
			}
		}
	});
	
	/**
	 *查询表单网格
	 */
	transmitReportStatus.selectGird = new Ext.grid.GridPanel({
		
		margins : '5 5 5 5',
		title : '报表导入状态',
		region : 'center',
		stripeRows : true,
		autoDestroy : true,
		store : transmitReportStatus.searchStore,
		colModel : transmitReportStatus.colModel,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
        listeners : {
            'cellclick' : function(grid ,rowIndex , columnIndex, event){
                 if(grid.getSelectionModel().getSelected()){
                	 reportId = transmitReportStatus.searchStore.getAt(rowIndex).get('reportId');
                	 rsid = transmitReportStatus.searchStore.getAt(rowIndex).get('rsid');
                 }
            }
        },
		tbar : [ 
				transmitReportStatus.searchAction
		],
		bbar : new Ext.PagingToolbar({
			store : transmitReportStatus.searchStore,
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
	transmitReportStatus.view = new Ext.Viewport({
		layout : 'border',
		items : [ transmitReportStatus.selectGird ]
	});
	
});

function rendererForDate(value){
//	   var val = value.format('Y-m-d H:i:s'); 
//	   if( val == '00-1-11-28'){
//		   return '';
//	   }
	   return value;
};