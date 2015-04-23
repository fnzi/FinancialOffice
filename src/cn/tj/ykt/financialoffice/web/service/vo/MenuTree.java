package cn.tj.ykt.financialoffice.web.service.vo;

public class MenuTree {

    private int id;
    private String text;
    private boolean leaf;
    private String url;

    public MenuTree() {
    }

    public MenuTree(int id, String text, boolean leaf) {
        this.id = id;
        this.text = text;
        this.leaf = leaf;
    }

    public MenuTree(int id, String text, boolean leaf, String url) {
        this.id = id;
        this.text = text;
        this.leaf = leaf;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
