var adjustGrid ={};

var loadUrl = ctx + '/doJson/adjustGridDataService';

Ext.onReady(function(){

   Ext.QuickTips.init();

    Ext.getDoc().on('contextmenu', function(e){
        e.stopEvent();
    });

    adjustGrid.loadFunc = function() {
        adjustGrid.searchStore.load({
        	params:{
        		start: 0,
        		limit: 5,
        		data_type: '30',
        		status: '31',
        		adjustId: '19',
        		reportId: 'MerchantSettleReport'
        	}
    	});
    }

    adjustGrid.searchStore = new Ext.data.Store({
        autoLoad : false , 
        url : loadUrl,
        baseParams : {
            start : 0,
            limit : 5,
            reportId : 'MerchantSettleReport',
            batchNo : '1'
        },
        reader : new Ext.data.JsonReader({
            root : 'data' , 
            totalProperty : 'total' , 
            fields : [
                  {name : 'batch_no' ,mapping : 'batch_no'},
                  {name : 'data_type' ,mapping : 'data_type'},
                      {name : 'settle_date' ,mapping : 'settle_date'},
                      {name : 'merchant_no' ,mapping : 'merchant_no'},
                      {name : 'merchant_name' ,mapping : 'merchant_name'},
                      {name : 'trade_amount' ,mapping : 'trade_amount'},
                      {name : 'merchant_settle_amount' ,mapping : 'merchant_settle_amount'},
                      {name : 'discount_earnings' ,mapping : 'discount_earnings'},
                      {name : 'consume_fee' ,mapping : 'consume_fee'},
                      {name : 'settle_fee' ,mapping : 'settle_fee'},
                      {name : 'card_type' ,mapping : 'card_type'},
                      {name : 'settle_type' ,mapping : 'settle_type'}
                      ]
        })
    });

    adjustGrid.loadFunc();

    adjustGrid.colModel = new Ext.grid.ColumnModel({
        columns: [
                  new Ext.grid.RowNumberer(),
                  { header: 'batch_no',  id : 'batch_no', dataIndex : 'batch_no', hidden:true},
                  { header: 'data_type',  id : 'data_type', dataIndex : 'data_type', hidden:true},
                   { header: '结算日期',  id : 'settle_date' , dataIndex : 'settle_date'},
                   { header: '商户编号',  id : 'merchant_no' , dataIndex : 'merchant_no'},
                   { header: '商户名称',  id : 'merchant_name' , dataIndex : 'merchant_name'},
                   { header: '交易金额',  id : 'trade_amount' , dataIndex : 'trade_amount'},
                   { header: '商户结算金额',  id : 'merchant_settle_amount' , dataIndex : 'merchant_settle_amount'},
                   { header: '折扣收益',  id : 'discount_earnings' , dataIndex : 'discount_earnings'},
                   { header: '消费手续费',  id : 'consume_fee' , dataIndex : 'consume_fee'},
                   { header: '结算手续费',  id : 'settle_fee' , dataIndex : 'settle_fee'},
                   { header: '卡类型',  id : 'card_type' , dataIndex : 'card_type'},
                   { header: '结算方式',  id : 'settle_type' , dataIndex : 'settle_type'}
               ],
         defaults: {
               sortable: true,
               menuDisabled: true,
               width: 120
         }
    });

adjustGrid.addAction = new Ext.Action({
    text : '调整',
    id:'add',
    disabled : false,
    iconCls : 'add',
    handler : function() {
        adjustGrid.win.show();
        adjustGrid.win.center();
        adjustGrid.form.form.reset();
    }
});

var batch_no = '';adjustGrid.delAction = new Ext.Action({
    text : '删除',
    id:'delete',
    disabled : false,
    iconCls : 'delete',
    handler : function() {
        if(batch_no === '') {
            Ext.MessageBox.alert('提示', '请选择一条待删除数据');
        } else {
            Ext.Ajax.request({
                url : ctx + '/doJson/adjustDeleteGridDataService.json',
                method : 'post',
                params :{
                    'reportId' :'MerchantSettleReport',
                    'batchNo' :batch_no
                },
                success : function(resp , opts){
                    var respObj = Ext.util.JSON.decode(resp.responseText);
                    if(respObj.success){
                        Ext.Msg.alert('提示',respObj.msg);
                        adjustGrid.loadFunc();
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

adjustGrid.searchAction = new Ext.Action({
    text : '查询',
    id:'search',
    disabled : false,
    iconCls : 'search',
    handler : function() {
    	adjustGrid.loadFunc();
    }
});

    adjustGrid.selectGird = new Ext.grid.GridPanel({

        region : 'center',
        stripeRows : true,
        autoDestroy : true,
        store : adjustGrid.searchStore,
        colModel : adjustGrid.colModel,
        sm : new Ext.grid.RowSelectionModel({
            singleSelect : true
        }),
        listeners : {
            'cellclick' : function(grid ,rowIndex , columnIndex, event){
                 if(grid.getSelectionModel().getSelected()){
                     batch_no = adjustGrid.searchStore.getAt(rowIndex).get('batch_no');
                 }
            }
        },
        tbar : [ 
            '-',
            adjustGrid.addAction,
            '-',
            adjustGrid.delAction,
            '-',
            adjustGrid.searchAction,
            '-'
        ],
        bbar : new Ext.PagingToolbar({
            store : adjustGrid.searchStore,
            displayInfo : true,
            pageSize : 50,
            doRefresh:function(){
                 return false;
            }
        })

    });

adjustGrid.form = new Ext.form.FormPanel({
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
            fieldLabel : 'reportId',
            id : 'reportId',
            name : 'reportId',
            hidden:true
        },
                 {
                     fieldLabel : '结算日期',
                     id : 'settle_date',
                     name : 'settle_date'
                 },
                 {
                     fieldLabel : '商户编号',
                     id : 'merchant_no',
                     name : 'merchant_no'
                 },
                 {
                     fieldLabel : '商户名称',
                     id : 'merchant_name',
                     name : 'merchant_name'
                 },
                 {
                     fieldLabel : '交易金额',
                     id : 'trade_amount',
                     name : 'trade_amount'
                 },
                 {
                     fieldLabel : '商户结算金额',
                     id : 'merchant_settle_amount',
                     name : 'merchant_settle_amount'
                 },
                 {
                     fieldLabel : '折扣收益',
                     id : 'discount_earnings',
                     name : 'discount_earnings'
                 },
                 {
                     fieldLabel : '消费手续费',
                     id : 'consume_fee',
                     name : 'consume_fee'
                 },
                 {
                     fieldLabel : '结算手续费',
                     id : 'settle_fee',
                     name : 'settle_fee'
                 },
                 {
                     fieldLabel : '卡类型',
                     id : 'card_type',
                     name : 'card_type'
                 },
                 {
                     fieldLabel : '结算方式',
                     id : 'settle_type',
                     name : 'settle_type'
                 }
        ]
    });

    adjustGrid.win = new Ext.Window({
        title : '调整',
        width : 400 , 
        height : 400 ,
        closable : false,
        autoScroll:true,
        modal:true,
        layout : 'fit',
        items : [adjustGrid.form],
        buttons : [{
            text : '保存',
            id : 'MSAccept',
            iconCls : 'accept' ,
            handler : function(){

                var f = adjustGrid.form;
                var w = adjustGrid.win;

                Ext.getCmp('reportId').setValue('MerchantSettleReport');

                if(f.form.isValid()){
                    f.form.submit({
                         waitMsg : '正在保存,请稍后...',
                         url : ctx + '/doJson/adjustAddGridDataService.json',
                         method : 'POST',
                         success : function(form, action) {
                             if(action.result.success){
                                 Ext.MessageBox.alert('操作结果', action.result.msg);
                                 adjustGrid.loadFunc();
                                 w.hide();
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
                adjustGrid.form.form.reset();
                adjustGrid.win.hide();
            }
        }]
    });

    adjustGrid.view = new Ext.Viewport({
        layout : 'border',
        items : [ adjustGrid.selectGird ]
    });

});
