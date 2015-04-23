package cn.tj.ykt.financialoffice.fw.minilang;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 功能描述：minilang 方法执行描述接口
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public interface MethodDeal {

    public String deal(List<String> args, Map<String, Object> context);
}
