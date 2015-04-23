package cn.tj.ykt.financialoffice.fw.minilang;

import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.exception.SystemException;

/**
 * <pre>
 * 功能描述：split功能函数处理类
 * eg:split(yesterday('yyyy-MM-dd'), 8, 10)
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class SplitMethodDeal implements MethodDeal {

    @Override
    public String deal(List<String> args, Map<String, Object> context) {

        if (args.size() != 3) {
            throw new SystemException("参数不正确");
        }

        String target = args.get(0);
        String start = args.get(1).trim();
        String end = args.get(2).trim();

        String ret = target.substring(Integer.parseInt(start), Integer.parseInt(end));

        return ret;
    }

}
