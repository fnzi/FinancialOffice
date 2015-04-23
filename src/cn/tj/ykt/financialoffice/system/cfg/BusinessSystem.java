package cn.tj.ykt.financialoffice.system.cfg;

/**
 * <pre>
 * 功能描述：业务系统实体描述
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class BusinessSystem {

	/** 业务系统编号 */
	private String id;
	/** 业务系统名称 */
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

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BusinessSystem) {
			BusinessSystem bs = (BusinessSystem) obj;
			if (bs.getId().equals(id)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
