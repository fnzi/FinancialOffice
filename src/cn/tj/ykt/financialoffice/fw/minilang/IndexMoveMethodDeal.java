package cn.tj.ykt.financialoffice.fw.minilang;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.dao.GenericDao;
import cn.tj.ykt.financialoffice.fw.exception.ServiceException;
import cn.tj.ykt.financialoffice.fw.helper.SpringUtil;
import cn.tj.ykt.financialoffice.fw.util.StringUtil;
import cn.tj.ykt.financialoffice.system.cfg.Column;
import cn.tj.ykt.financialoffice.system.cfg.util.ConfigUtil;

/**
 * <pre>
 * 功能描述：index_move功能函数处理类
 * eg:index_move(K, -1) 在循环中，获取当前index值对应上下移动的值。
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class IndexMoveMethodDeal implements MethodDeal {

    @Override
    public String deal(List<String> args, Map<String, Object> context) {
        String str = "";

        if (context != null) {
            /** 1.1获取report **/
            String report = "";
            if (context.get("reportId") != null) {
                report = context.get("reportId").toString();
            } else {
                throw new ServiceException("没有找到对应的reportId");
            }

            /** 1.2 获取批次号 **/
            String batchNo = "";
            if (context.get("batchNo") != null) {
                batchNo = context.get("batchNo").toString();
            } else {
                throw new ServiceException("没有找到对应的batchNo");
            }

            /** 1.3 获取读取次数 **/
            int index = 0;
            if (context.get("index") != null) {
                index = Integer.parseInt(context.get("index").toString());
            } else {
                throw new ServiceException("没有找到对应的index");
            }

            /** 2.解析参数 **/
            String valCol = args.get(0).toString();
            int valNum = 0;
            if (!valCol.equals("")) {
                valNum = MiniLangUtil.letter2Num(valCol);
            } else {
                throw new ServiceException("没有找到对应的数据列（英文字母）");
            }

            String moveStr = args.get(1).toString();
            int move = Integer.parseInt(moveStr);

            List<String> colNum = new ArrayList<String>();
            colNum.add(Integer.toString(valNum));

            /** 3.获取当前报表的数据列 **/
            List<String> dataCol = new ArrayList<String>();
            Map<String, Column> column = ConfigUtil.tableColumns(report);
            List<String> list = new ArrayList<String>();
            if (column != null) {
                for (String key : column.keySet()) {
                    list.add(key);
                }
                for (int i = 0; i < colNum.size(); i++) {
                    int num = Integer.parseInt(colNum.get(i).toString());
                    if (list.get(num).toString() != null) {
                        dataCol.add(list.get(num).toString());
                    } else {
                        throw new ServiceException("对应的数据列不存在");
                    }
                }
            }

            /** 4.查询数据表 **/
            String query = StringUtil.list2String(dataCol);
            String tName = ConfigUtil.tableName(report);
            int headSize = ConfigUtil.headerSize(report);
            int sort = headSize + index + 1 + move;
            String sql = "select " + query + " from " + tName + " where batch_no = '" + batchNo + "' and sort_index = " + sort;
            GenericDao dao = (GenericDao) SpringUtil.getBean("genericDao");
            List<Object> result = (List<Object>) dao.findListBySQL(sql);
            if (result != null) {
                for (Object o : result) {
                        System.out.println("------------loop--------------" + (String)o);
                        return (String)o;
                }
            } else {
                throw new LoopException("数据不存在__");
            }

        } else {
            throw new ServiceException("参数context不存在");
        }

        return str;
    }

}
