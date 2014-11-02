package cn.tj.ykt.financialoffice.system.cfg;

/**
 * <pre>
 * 功能描述：配置实体描述
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class Configuration {

    // 标示
    private String id;
    // 启用标示(1:启用，0:未启用)
    private String enable;
    // 描述
    private String description;
    // 报表名字
    private String name;
    // view系
    private Viewer viewer;
    // 创建table系
    private CreateTabler createTabler;
    // 抓取data系
    private CatchDataer catchDataer;
    // A3插件系
    private A3Pluginer a3Pluginer;

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Viewer getViewer() {
        return viewer;
    }

    public void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CreateTabler getCreateTabler() {
        return createTabler;
    }

    public void setCreateTabler(CreateTabler createTabler) {
        this.createTabler = createTabler;
    }

    public CatchDataer getCatchDataer() {
        return catchDataer;
    }

    public void setCatchDataer(CatchDataer catchDataer) {
        this.catchDataer = catchDataer;
    }

    public A3Pluginer getA3Pluginer() {
        return a3Pluginer;
    }

    public void setA3Pluginer(A3Pluginer a3Pluginer) {
        this.a3Pluginer = a3Pluginer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
