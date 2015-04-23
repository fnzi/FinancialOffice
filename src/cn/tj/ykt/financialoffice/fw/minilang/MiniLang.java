package cn.tj.ykt.financialoffice.fw.minilang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import cn.tj.ykt.financialoffice.fw.exception.GenericException;
import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.helper.LogUtil;

public class MiniLang {

    private String module = MiniLang.class.getName();

    public static String METHOD_DATE_FORMAT = "date_format";
    public static String METHOD_NUMBER_FORMAT = "number_format";
    public static String METHOD_ROWCOL = "rowCol";
    public static String METHOD_ROW = "row";
    public static String METHOD_COL = "col";
    public static String METHOD_ADD = "add";
    public static String METHOD_SUBTRACT = "subtract";
    public static String METHOD_TO_CUSTOM = "to_custom";
    public static String METHOD_TO_DEPT = "to_dept";
    public static String METHOD_TO_NETWORKA3NOTE = "to_networka3note";
    public static String METHOD_TO_SPAREGOLDCODE = "to_sparegoldcode";
    public static String METHOD_LOOP = "loop";
    public static String METHOD_SCAN = "scan";
    public static String METHOD_INDEX_MOVE = "index_move";

    public static String METHOD_SPLIT = "split";
    public static String METHOD_BLANK = "blank";

    public static String METHOD_YESTERDAY = "yesterday";
    public static String METHOD_CELL = "cell";

    public static String METHOD_NEGATIVE = "negative";

    public static String METHOD_GET_NETWORK_NAME = "get_network_name";
    public static String METHOD_GET_NETWORK_ORGNID = "get_network_orgnid";

    private MiniLang() {
        // 初始化处理器
        try {
            DateFormatMethodDeal dateFormat = new DateFormatMethodDeal();
            methodDealMap.put(METHOD_DATE_FORMAT, dateFormat);
            NumberFormatMethodDeal numberFormat = new NumberFormatMethodDeal();
            methodDealMap.put(METHOD_NUMBER_FORMAT, numberFormat);
            RowColMethodDeal rowCol = new RowColMethodDeal();
            methodDealMap.put(METHOD_ROWCOL, rowCol);
            RowMethodDeal row = new RowMethodDeal();
            methodDealMap.put(METHOD_ROW, row);
            ColMethodDeal col = new ColMethodDeal();
            methodDealMap.put(METHOD_COL, col);
            AddMethodDeal add = new AddMethodDeal();
            methodDealMap.put(METHOD_ADD, add);
            SubtractMethodDeal subtract = new SubtractMethodDeal();
            methodDealMap.put(METHOD_SUBTRACT, subtract);
            ToCustomMethodDeal to_custom = new ToCustomMethodDeal();
            methodDealMap.put(METHOD_TO_CUSTOM, to_custom);
            ToDeptMethodDeal to_dept = new ToDeptMethodDeal();
            methodDealMap.put(METHOD_TO_DEPT, to_dept);
            ToSpareGoldCodeMethodDeal to_sparegoldcode = new ToSpareGoldCodeMethodDeal();
            methodDealMap.put(METHOD_TO_SPAREGOLDCODE, to_sparegoldcode);

            ToNetWorkA3Note to_networka3note = new ToNetWorkA3Note();
            methodDealMap.put(METHOD_TO_NETWORKA3NOTE, to_networka3note);

            LoopMethodDeal loop = new LoopMethodDeal();
            methodDealMap.put(METHOD_LOOP, loop);
            ScanMethodDeal scan = new ScanMethodDeal();
            methodDealMap.put(METHOD_SCAN, scan);
            YesterdayMethodDeal yesterday = new YesterdayMethodDeal();
            methodDealMap.put(METHOD_YESTERDAY, yesterday);
            BlankMethodDeal blank = new BlankMethodDeal();
            methodDealMap.put(METHOD_BLANK, blank);
            SplitMethodDeal split = new SplitMethodDeal();
            methodDealMap.put(METHOD_SPLIT, split);
            CellMethodDeal cell = new CellMethodDeal();
            methodDealMap.put(METHOD_CELL, cell);
            NegativeMethodDeal negative = new NegativeMethodDeal();
            methodDealMap.put(METHOD_NEGATIVE, negative);
            IndexMoveMethodDeal indexMove = new IndexMoveMethodDeal();
            methodDealMap.put(METHOD_INDEX_MOVE, indexMove);

            GetNetWorkName getNetWorkName = new GetNetWorkName();
            methodDealMap.put(METHOD_GET_NETWORK_NAME, getNetWorkName);
            GetNetWorkOrgnId getNetWorkOrgnId = new GetNetWorkOrgnId();
            methodDealMap.put(METHOD_GET_NETWORK_ORGNID, getNetWorkOrgnId);
        } catch (Exception e) {
            LogUtil.logError("minilang 初始化失败", module, e);
        }
    }

    private static MiniLang instance = new MiniLang();

    public static MiniLang getInstance() {
        return instance;
    }

    private Map<String, MethodDeal> methodDealMap = new HashMap<String, MethodDeal>();

    private MiniLangParse parse = new MiniLangParse();

    /**
     * 获取已经注册的minilang方法
     */
    public Set<String> getMiniLangMethods() {
        return methodDealMap.keySet();
    }

    public String exec(String method, Map<String, Object> context) {
        String ret = "";
        try {

            Stack<String> stack = new Stack<String>();

            /** 1.解析method ----> method info */
            method = method.trim();

            Queue<String> queue = parse.parse(method);

            String val;
            while ((val = queue.poll()) != null) {
                if (")".equals(val)) {
                    List<String> args = new ArrayList<String>();
                    String arg;
                    while ((arg = stack.pop()) != null) {
                        if ("(".equals(arg)) {
                            String methodName = stack.pop();
                            Collections.reverse(args);

                            // ---------参数打印-----------
                            for (String s : args) {
                                LogUtil.logDebug("arg: " + s.trim(), module);
                            }
                            LogUtil.logDebug("method: " + methodName);
                            /** 2.执行相应的处理 */

                            // 验证
                            if (!methodDealMap.keySet().contains(methodName)) {
                                throw new SystemException(method + "->minilang 方法错误，没有解析处理");
                            }

                            MethodDeal deal = methodDealMap.get(methodName);
                            ret = deal.deal(args, context);

                            stack.push(ret);
                            break;
                        } else {
                            args.add(arg);
                        }
                    }
                } else {
                    stack.push(val);
                }
            }

            // 最后结果
            ret = make(stack);
        } catch (GenericException e) {
            LogUtil.logError(e.getMessage(), module, e);
            throw e;
        } catch (Exception e) {
            LogUtil.logError(e.getMessage(), module, e);
            throw new SystemException(e.getMessage());
        }

        return ret;
    }

    private String make(Stack<String> stack) {
        StringBuffer sb = new StringBuffer();
        for (String s : stack) {
            if (s == null) {
                s = "";
            }
            sb.append(s);
        }
        return sb.toString();
    }
}
