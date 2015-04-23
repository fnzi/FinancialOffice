package cn.tj.ykt.financialoffice.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.system.cfg.Column;
import cn.tj.ykt.financialoffice.system.cfg.util.ConfigUtil;
import cn.tj.ykt.financialoffice.web.service.JspResult;
import cn.tj.ykt.financialoffice.web.service.JspService;

/**
 * <pre>
 * 功能描述：(当事人)调整jsp+js页面
 * 创建者：闫世峰
 * 修改者：许芳
 * </pre>
 */
@Service("adjustGridService")
public class AdjustGridService extends JspService {

    @Override
    public JspResult doExecute(Map<String, Object> param) {
        Map<String, Object> ret = new HashMap<String, Object>();

        String reportId = (String) param.get("reportId");
        /** 日报中的数据类型，调整为30 */
        String data_type = (String) param.get("data_type");
        /** 调整数据中的状态 */
        String status = (String) param.get("status");
        String adjustId = (String) param.get("adjustId");

        /**
         * 0:隐藏工具条 1:显示工具条
         */
        String menuBar = (String) param.get("menuBar");

        List<ScriptModel> data = new ArrayList<ScriptModel>();

        if (checkParam(reportId, data_type, status, adjustId)) {
            Map<String, Column> cols = ConfigUtil.showCols(reportId);
            for (String key : cols.keySet()) {
                Column c = cols.get(key);
                ScriptModel sm1 = new ScriptModel();
                sm1.setId(c.getMapping());
                sm1.setName(c.getName());
                data.add(sm1);
            }

            String jsscript = getJsScript(data, data_type, status, adjustId, reportId, menuBar);
            ret.put("jsscript", jsscript);
            ret.put("body", "");
            System.out.println(jsscript);
        } else {
            ret.put("body", "调整");
        }

        return new JspResult("adjustGrid", ret);
    }

    private Boolean checkParam(String... param) {
        for (String p : param) {
            if (p == null || p.equals("null")) {
                return false;
            }
        }
        return true;
    }

    private String getJsScript(List<ScriptModel> data, String data_type, String status, String adjustId, String reportId, String menuBar) {

        StringBuffer sb = new StringBuffer();

        sb.append("var adjustGrid ={};").append("\n");
        sb.append("\n");
        sb.append("var loadUrl = ctx + '/doJson/adjustGridDataService';").append("\n");
        sb.append("\n");
        sb.append("Ext.onReady(function(){").append("\n");
        sb.append("\n");
        sb.append("   Ext.QuickTips.init();").append("\n");
        sb.append("\n");
        sb.append("    Ext.getDoc().on('contextmenu', function(e){").append("\n");
        sb.append("        e.stopEvent();").append("\n");
        sb.append("    });").append("\n");
        sb.append("\n");

        sb.append("    adjustGrid.loadFunc = function() {").append("\n");
        sb.append("        adjustGrid.searchStore.load({").append("\n");
        sb.append("             params:{").append("\n");
        sb.append("                 start: 0,").append("\n");
        sb.append("                 limit: 5,").append("\n");
        sb.append("                 data_type: '").append(data_type).append("',").append("\n");
        sb.append("                 status: '").append(status).append("',").append("\n");
        sb.append("                 adjustId: '").append(adjustId).append("',").append("\n");
        sb.append("                 reportId: '").append(reportId).append("'").append("\n");
        sb.append("             }").append("\n");
        sb.append("        });").append("\n");
        sb.append("    }").append("\n");
        sb.append("\n");

        sb.append("    adjustGrid.searchStore = new Ext.data.Store({").append("\n");
        sb.append("        autoLoad : false , ").append("\n");
        sb.append("        url : loadUrl,").append("\n");
        sb.append("        baseParams : {").append("\n");
        sb.append("            start : 0,").append("\n");
        sb.append("            limit : 5,").append("\n");
        sb.append("            reportId : '").append(reportId).append("',").append("\n");
        sb.append("            batchNo : '1'").append("\n");
        sb.append("        },").append("\n");
        sb.append("        reader : new Ext.data.JsonReader({").append("\n");
        sb.append("            root : 'data' , ").append("\n");
        sb.append("            totalProperty : 'total' , ").append("\n");
        sb.append("            fields : [").append("\n");

        // 固定字段
        sb.append("                  {name : 'batch_no' ,mapping : 'batch_no'},").append("\n");
        sb.append("                  {name : 'data_type' ,mapping : 'data_type'},").append("\n");
        // field
        sb.append(getFields(data)).append("\n");

        sb.append("                      ]").append("\n");
        sb.append("        })").append("\n");
        sb.append("    });").append("\n");
        sb.append("\n");
        sb.append("        adjustGrid.loadFunc();").append("\n");
        sb.append("\n");
        sb.append("    adjustGrid.colModel = new Ext.grid.ColumnModel({").append("\n");
        sb.append("        columns: [").append("\n");
        sb.append("                  new Ext.grid.RowNumberer(),").append("\n");

        // 固定字段
        sb.append("                  { header: 'batch_no',  id : 'batch_no', dataIndex : 'batch_no', hidden:true},").append("\n");
        sb.append("                  { header: 'data_type',  id : 'data_type', dataIndex : 'data_type', hidden:true},").append("\n");
        // ColModel
        sb.append(getColModel(data)).append("\n");

        sb.append("               ],").append("\n");
        sb.append("         defaults: {").append("\n");
        sb.append("               sortable: true,").append("\n");
        sb.append("               menuDisabled: true,").append("\n");
        sb.append("               width: 120").append("\n");
        sb.append("         }").append("\n");
        sb.append("    });").append("\n");
        sb.append("\n");
        sb.append("adjustGrid.addAction = new Ext.Action({").append("\n");
        sb.append("    text : '调整',").append("\n");
        sb.append("    id:'add',").append("\n");
        sb.append("    disabled : false,").append("\n");
        sb.append("    iconCls : 'add',").append("\n");
        sb.append("    handler : function() {").append("\n");
        sb.append("        adjustGrid.win.show();").append("\n");
        sb.append("        adjustGrid.win.center();").append("\n");
        sb.append("        adjustGrid.form.form.reset();").append("\n");
        sb.append("    }").append("\n");
        sb.append("});").append("\n");
        sb.append("\n");
        sb.append("var batch_no = '';").append("\n");
        sb.append("adjustGrid.delAction = new Ext.Action({").append("\n");
        sb.append("    text : '删除',").append("\n");
        sb.append("    id:'delete',").append("\n");
        sb.append("    disabled : false,").append("\n");
        sb.append("    iconCls : 'delete',").append("\n");
        sb.append("    handler : function() {").append("\n");

        sb.append("        if(batch_no === '') {").append("\n");
        sb.append("            Ext.MessageBox.alert('提示', '请选择一条待删除数据');").append("\n");
        sb.append("        } else {").append("\n");
        sb.append("            Ext.Ajax.request({").append("\n");
        sb.append("                url : ctx + '/doJson/adjustDeleteGridDataService.json',").append("\n");
        sb.append("                method : 'post',").append("\n");
        sb.append("                params :{").append("\n");
        sb.append("                    'reportId' :'").append(reportId).append("',").append("\n");
        sb.append("                    'batchNo' :batch_no").append("\n");
        sb.append("                },").append("\n");
        sb.append("                success : function(resp , opts){").append("\n");
        sb.append("                    var respObj = Ext.util.JSON.decode(resp.responseText);").append("\n");
        sb.append("                    if(respObj.success){").append("\n");
        sb.append("                        Ext.Msg.alert('提示',respObj.msg);").append("\n");
        sb.append("                        adjustGrid.loadFunc();").append("\n");
        sb.append("                    }else{").append("\n");
        sb.append("                        Ext.Msg.alert('提示',respObj.msg);").append("\n");
        sb.append("                        return;").append("\n");
        sb.append("                    }").append("\n");
        sb.append("                },").append("\n");
        sb.append("                failure: function(resp , opts) {").append("\n");
        sb.append("                    Ext.Msg.alert('提示','操作失败');").append("\n");
        sb.append("                    return;").append("\n");
        sb.append("                }").append("\n");
        sb.append("            });").append("\n");
        sb.append("        }").append("\n");

        sb.append("    }").append("\n");
        sb.append("});").append("\n");
        sb.append("\n");

        sb.append("adjustGrid.searchAction = new Ext.Action({").append("\n");
        sb.append("    text : '查询',").append("\n");
        sb.append("    id:'search',").append("\n");
        sb.append("    disabled : false,").append("\n");
        sb.append("    iconCls : 'search',").append("\n");
        sb.append("    handler : function() {").append("\n");
        sb.append("        adjustGrid.loadFunc();").append("\n");
        sb.append("    }").append("\n");
        sb.append("});").append("\n");
        sb.append("\n");

        sb.append("    adjustGrid.selectGird = new Ext.grid.GridPanel({").append("\n");
        sb.append("        title : '调整日报数据',").append("\n");
        sb.append("        height:300,").append("\n");
        sb.append("        region : 'center',").append("\n");
        sb.append("        stripeRows : true,").append("\n");
        sb.append("        autoDestroy : true,").append("\n");
        sb.append("        store : adjustGrid.searchStore,").append("\n");
        sb.append("        colModel : adjustGrid.colModel,").append("\n");
        sb.append("        sm : new Ext.grid.RowSelectionModel({").append("\n");
        sb.append("            singleSelect : true").append("\n");
        sb.append("        }),").append("\n");
        sb.append("        listeners : {").append("\n");
        sb.append("            'cellclick' : function(grid ,rowIndex , columnIndex, event){").append("\n");
        sb.append("                 if(grid.getSelectionModel().getSelected()){").append("\n");
        sb.append("                     batch_no = adjustGrid.searchStore.getAt(rowIndex).get('batch_no');").append("\n");
        sb.append("                 }").append("\n");
        sb.append("            }").append("\n");
        sb.append("        },").append("\n");
        if (menuBar.equals("1")) {
            sb.append("        tbar : [ ").append("\n");
            sb.append("            '-',").append("\n");
            sb.append("            adjustGrid.addAction,").append("\n");
            sb.append("            '-',").append("\n");
            sb.append("            adjustGrid.delAction,").append("\n");
            sb.append("            '-',").append("\n");
            sb.append("            adjustGrid.searchAction,").append("\n");
            sb.append("            '-'").append("\n");
            sb.append("        ],").append("\n");
        }
        sb.append("        bbar : new Ext.PagingToolbar({").append("\n");
        sb.append("            store : adjustGrid.searchStore,").append("\n");
        sb.append("            displayInfo : true,").append("\n");
        sb.append("            pageSize : 50,").append("\n");
        sb.append("            doRefresh:function(){").append("\n");
        sb.append("                 return false;").append("\n");
        sb.append("            }").append("\n");
        sb.append("        })").append("\n");
        sb.append("\n");
        sb.append("    });").append("\n");
        sb.append("\n");
        sb.append("adjustGrid.form = new Ext.form.FormPanel({").append("\n");
        sb.append("        labelAlign: 'right',").append("\n");
        sb.append("        labelWidth:100,").append("\n");
        sb.append("        region : 'center',").append("\n");
        sb.append("        frame:true,").append("\n");
        sb.append("        defaultType:'textfield',").append("\n");
        sb.append("        defaults : {").append("\n");
        sb.append("            width : 150").append("\n");
        sb.append("        },").append("\n");
        sb.append("        items : [").append("\n");
        sb.append("        {").append("\n");
        sb.append("            fieldLabel : 'reportId',").append("\n");
        sb.append("            id : 'reportId',").append("\n");
        sb.append("            name : 'reportId',").append("\n");
        sb.append("            hidden:true").append("\n");
        sb.append("        },").append("\n");
        
        sb.append("        {").append("\n");
        sb.append("            fieldLabel : 'adjustId',").append("\n");
        sb.append("            id : 'adjustId',").append("\n");
        sb.append("            name : 'adjustId',").append("\n");
        sb.append("            value : '").append(adjustId).append("',").append("\n");
        sb.append("            hidden:true").append("\n");
        sb.append("        },").append("\n");
        
        sb.append(getForm(data)).append("\n");

        sb.append("        ]").append("\n");
        sb.append("    });").append("\n");
        sb.append("\n");
        sb.append("    adjustGrid.win = new Ext.Window({").append("\n");
        sb.append("        title : '调整',").append("\n");
        sb.append("        width : 300 , ").append("\n");
        sb.append("        height : 300 ,").append("\n");
        sb.append("        closable : false,").append("\n");
        sb.append("        autoScroll:true,").append("\n");
        sb.append("        modal:true,").append("\n");
        sb.append("        layout : 'fit',").append("\n");
        sb.append("        items : [adjustGrid.form],").append("\n");
        sb.append("        buttons : [{").append("\n");
        sb.append("            text : '保存',").append("\n");
        sb.append("            id : 'MSAccept',").append("\n");
        sb.append("            iconCls : 'accept' ,").append("\n");
        sb.append("            handler : function(){").append("\n");
        sb.append("\n");

        sb.append("                var f = adjustGrid.form;").append("\n");
        sb.append("                var w = adjustGrid.win;").append("\n");

        sb.append("\n");
        sb.append("                Ext.getCmp('reportId').setValue('").append(reportId).append("');").append("\n");

        sb.append("\n");
        sb.append("                if(f.form.isValid()){").append("\n");
        sb.append("                    f.form.submit({").append("\n");
        sb.append("                         waitMsg : '正在保存,请稍后...',").append("\n");
        sb.append("                         url : ctx + '/doJson/adjustAddGridDataService.json',").append("\n");
        sb.append("                         method : 'POST',").append("\n");
        sb.append("                         success : function(form, action) {").append("\n");
        sb.append("                             if(action.result.success){").append("\n");
        sb.append("                                 Ext.MessageBox.alert('操作结果', action.result.msg);").append("\n");
        sb.append("                                 adjustGrid.loadFunc();").append("\n");
        sb.append("                                 w.hide();").append("\n");
        sb.append("                             } else {").append("\n");
        sb.append("                                 Ext.MessageBox.alert('操作结果', action.result.msg);").append("\n");
        sb.append("                             }").append("\n");
        sb.append("                         },").append("\n");
        sb.append("                         failure : function(form, action) {").append("\n");
        sb.append("                            f.form.reset();").append("\n");
        sb.append("                         }").append("\n");
        sb.append("                    });").append("\n");
        sb.append("                }").append("\n");

        sb.append("\n");
        sb.append("        }").append("\n");
        sb.append("        },{").append("\n");
        sb.append("            text : '关闭',").append("\n");
        sb.append("            iconCls : 'cancel',").append("\n");
        sb.append("            handler : function(){").append("\n");
        sb.append("                adjustGrid.form.form.reset();").append("\n");
        sb.append("                adjustGrid.win.hide();").append("\n");
        sb.append("            }").append("\n");
        sb.append("        }]").append("\n");
        sb.append("    });").append("\n");

        sb.append("\n");
        sb.append("    adjustGrid.view = new Ext.Viewport({").append("\n");
        sb.append("        layout : 'anchor',").append("\n");
        sb.append("        items : [ adjustGrid.selectGird ]").append("\n");
        sb.append("    });").append("\n");
        sb.append("\n");
        sb.append("});").append("\n");

        return sb.toString();
    }

    private String getFields(List<ScriptModel> data) {
        StringBuffer sb = new StringBuffer();

        if (data.size() == 0) {
            throw new SystemException("配置错误，请检查调整页");
        }

        for (ScriptModel sm : data) {
            sb.append("                      {name : '").append(sm.getId()).append("' ,mapping : '").append(sm.getId()).append("'},").append("\n");
        }

        String ret = sb.toString();
        int len = ret.length();
        ret = ret.substring(0, len - 2);

        return ret;
    }

    private String getColModel(List<ScriptModel> data) {
        StringBuffer sb = new StringBuffer();

        if (data.size() == 0) {
            throw new SystemException("配置错误，请检查调整页");
        }

        for (ScriptModel sm : data) {
            sb.append("                   { header: '").append(sm.getName()).append("',  id : '").append(sm.getId()).append("' , dataIndex : '")
                    .append(sm.getId()).append("'},").append("\n");
        }

        String ret = sb.toString();
        int len = ret.length();
        ret = ret.substring(0, len - 2);

        return ret;
    }

    private String getForm(List<ScriptModel> data) {
        StringBuffer sb = new StringBuffer();

        if (data.size() == 0) {
            throw new SystemException("配置错误，请检查调整页");
        }

        for (ScriptModel sm : data) {
            sb.append("                 {").append("\n");
            sb.append("                     allowBlank : false, ").append("\n");
            sb.append("                     fieldLabel : '").append(sm.getName()).append("',").append("\n");
            sb.append("                     id : '").append(sm.getId()).append("',").append("\n");
            sb.append("                     name : '").append(sm.getId()).append("'").append("\n");
            sb.append("                 },").append("\n");
        }

        String ret = sb.toString();
        int len = ret.length();
        ret = ret.substring(0, len - 2);

        return ret;
    }
}

class ScriptModel {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}