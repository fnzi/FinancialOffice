package cn.tj.ykt.financialoffice.fw.minilang;

import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.exception.SystemException;

/**
 * <pre>
 * 功能描述：输出占位符功能函数处理类
 * eg:blank(1)-->输出1个空格占位
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class BlankMethodDeal implements MethodDeal {

    @Override
    public String deal(List<String> args, Map<String, Object> context) {

        if (args.size() != 1) {
            throw new SystemException("参数不正确");
        }

        String c = " ";
        int len = Integer.parseInt(args.get(0).trim());
        String ret = "";

        for (int i = 0; i < len; i++) {
            ret = ret + c;
        }

        return ret;
    }

}
