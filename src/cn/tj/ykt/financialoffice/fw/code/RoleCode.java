package cn.tj.ykt.financialoffice.fw.code;

public enum RoleCode implements ICodeEnum {

    ADMIN("admin", "管理员"), USER("user", "用户"), REPORT("report", "报表用户");

    private String key;
    private String description;

    RoleCode(String key, String description) {
        this.key = key;
        this.description = description;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

}
