package cn.tj.ykt.financialoffice.fw.service;

import java.util.List;
import java.util.Map;

import cn.tj.ykt.financialoffice.handler.impl.GlTrnVo;

/**
 * <pre>
 * 功能描述：A3凭证导入服务接口
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public interface A3PluginService {

    public List<GlTrnVo> execute(Map<String, Object> context);
}
