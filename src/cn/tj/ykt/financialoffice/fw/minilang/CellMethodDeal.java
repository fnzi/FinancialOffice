package cn.tj.ykt.financialoffice.fw.minilang;

import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.exception.SystemException;

/**
 * <pre>
 * 功能描述：cell功能函数处理类
 * eg:cell(A, 2)
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class CellMethodDeal implements MethodDeal {

    @Override
    public String deal(List<String> args, Map<String, Object> context) {

        if (args.size() != 2) {
            throw new SystemException("参数不正确");
        }

        String r = args.get(0);
        String c = args.get(1);

        int i = MiniLangUtil.letter2Num(r);
        int j = Integer.parseInt(c.trim());

        List<List<String>> data = (List<List<String>>) context.get("excelInData");

        if (data == null) {
            throw new SystemException("cell功能函数配置错误");
        }

        if (data.size() < j) {
            throw new SystemException("数据行不正确");
        }

        List<String> dataInLine = data.get(j-1);

        if (dataInLine.size() < i) {
            throw new SystemException("数据列不正确");
        }

        String ret = dataInLine.get(i);

        return ret;
    }

}
