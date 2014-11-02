package cn.tj.ykt.financialoffice.system.cfg.mapping;

import java.util.Map;

import cn.tj.ykt.financialoffice.system.cfg.Configurations;

/**
 * <pre>
 * 功能描述：解析配置文件描述接口
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public interface MappingDeal {

    public Configurations execute(Map<String, Object> param);
}
