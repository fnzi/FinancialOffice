var adjustGrid ={};

var loadUrl = ctx + '/doJson/adjustGridDataService';

Ext.onReady(function(){

   Ext.QuickTips.init();

    Ext.getDoc().on('contextmenu', function(e){
        e.stopEvent();
    });

    adjustGrid.loadFunc = function() {
        adjustGrid.searchStore.load();
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

    adjustGrid.selectGird = new Ext.grid.GridPanel({
    	title : '调整日报数据',
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
        bbar : new Ext.PagingToolbar({
            store : adjustGrid.searchStore,
            displayInfo : true,
            pageSize : 50,
            doRefresh:function(){
                 return false;
            }
        })

    });

    adjustGrid.view = new Ext.Viewport({
        layout : 'border',
        items : [ adjustGrid.selectGird ]
    });

});
