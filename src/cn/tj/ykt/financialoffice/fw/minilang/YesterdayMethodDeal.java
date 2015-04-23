package cn.tj.ykt.financialoffice.fw.minilang;

import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.util.DateUtil;

public class YesterdayMethodDeal implements MethodDeal {

    @Override
    public String deal(List<String> args, Map<String, Object> context) {

        if (args.size() != 1) {
            throw new SystemException("minilang YesterdayMethodDeal 参数不正确");
        }

        String format = args.get(0);

        return DateUtil.getYesterday(format);
    }

}
