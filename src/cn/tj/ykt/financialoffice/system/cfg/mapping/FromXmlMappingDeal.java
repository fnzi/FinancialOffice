package cn.tj.ykt.financialoffice.system.cfg.mapping;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.tj.ykt.financialoffice.fw.exception.SystemException;
import cn.tj.ykt.financialoffice.fw.util.StringUtil;
import cn.tj.ykt.financialoffice.system.cfg.A3Pluginer;
import cn.tj.ykt.financialoffice.system.cfg.BusinessSystem;
import cn.tj.ykt.financialoffice.system.cfg.CatchDataer;
import cn.tj.ykt.financialoffice.system.cfg.Column;
import cn.tj.ykt.financialoffice.system.cfg.Configuration;
import cn.tj.ykt.financialoffice.system.cfg.Configurations;
import cn.tj.ykt.financialoffice.system.cfg.CreateTabler;
import cn.tj.ykt.financialoffice.system.cfg.ExportQueryData;
import cn.tj.ykt.financialoffice.system.cfg.Gl_Trntm;
import cn.tj.ykt.financialoffice.system.cfg.Header;
import cn.tj.ykt.financialoffice.system.cfg.QueryCondition;
import cn.tj.ykt.financialoffice.system.cfg.Viewer;

/**
 * <pre>
 * 功能描述：解析基于XML的配置文件
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class FromXmlMappingDeal implements MappingDeal {

    @Override
    public Configurations execute(Map<String, Object> param) {

        Configurations configs = new Configurations();
        try {
            InputStream fileinputstream = (InputStream) param.get("fileinputstream");

            if (fileinputstream == null) {
                throw new SystemException("file inputstream 必须赋值");
            }

            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = db.parse(fileinputstream);

            Element root = document.getDocumentElement();

            // 读入配置信息
            NodeList configList = root.getElementsByTagName("configuration");
            for (int i = 0; i < configList.getLength(); i++) {

                Node configNode = configList.item(i);

                Configuration config = getConfiguration(configNode);

                NodeList configChildNode = configNode.getChildNodes();
                for (int j = 0; j < configChildNode.getLength(); j++) {
                    Node ccnode = configChildNode.item(j);
                    if (ccnode.getNodeName().equals("description")) {
                        config.setDescription(ccnode.getFirstChild().getNodeValue());
                    } else if (ccnode.getNodeName().equals("system")) {

                        BusinessSystem system = new BusinessSystem();
                        // 读取属性
                        NamedNodeMap nodeProp = ccnode.getAttributes();
                        for (int k = 0; k < nodeProp.getLength(); k++) {
                            Attr attr = (Attr) nodeProp.item(k);
                            if (attr.getNodeName().equals("id")) {
                                system.setId(attr.getNodeValue());
                            } else if (attr.getNodeName().equals("name")) {
                                system.setName(attr.getNodeValue());
                            }
                        }
                        config.setSystem(system);
                    } else if (ccnode.getNodeName().equals("viewer")) {
                        config.setViewer(getViewer(ccnode));
                    } else if (ccnode.getNodeName().equals("createTabler")) {
                        config.setCreateTabler(getCreateTabler(ccnode));
                    } else if (ccnode.getNodeName().equals("catchDataer")) {
                        config.setCatchDataer(getCatchDataer(ccnode));
                    } else if (ccnode.getNodeName().equals("a3Pluginer")) {
                        config.setA3Pluginer(getA3Pluginer(ccnode));
                    }
                }

                configs.getCfgs().put(config.getId(), config);
            }

            // 读取日报下载配置
            NodeList querydatas = root.getElementsByTagName("querydatas");
            for (int i = 0; i < querydatas.getLength(); i++) {
                Node configNode = querydatas.item(i);

                NodeList nodeList = configNode.getChildNodes();
                for (int j = 0; j < nodeList.getLength(); j++) {
                    Node ccnode = nodeList.item(j);
                    if (ccnode.getNodeName().equals("querydata")) {
                        configs.getExportQueryData().add(getQueryData(ccnode));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return configs;
    }

    private ExportQueryData getQueryData(Node ccnode) {

        ExportQueryData exportQueryData = new ExportQueryData();

        NamedNodeMap nodeProp = ccnode.getAttributes();
        for (int j = 0; j < nodeProp.getLength(); j++) {
            Attr attr = (Attr) nodeProp.item(j);
            if (attr.getNodeName().equals("reportId")) {
                exportQueryData.setReportId(attr.getNodeValue());
            }
        }

        // 获取节点集合
        NodeList columnsNodeList = ccnode.getChildNodes();

        for (int j = 0; j < columnsNodeList.getLength(); j++) {
            // 获取节点
            Node columnNode = columnsNodeList.item(j);

            if (columnNode.getNodeName().equals("cron")) {
                exportQueryData.setCron(columnNode.getFirstChild().getNodeValue());
            } else if (columnNode.getNodeName().equals("username")) {
                exportQueryData.setUsername(getText(columnNode));
            } else if (columnNode.getNodeName().equals("password")) {
                exportQueryData.setPassword(getText(columnNode));
            } else if (columnNode.getNodeName().equals("querys")) {
                NamedNodeMap querys = columnNode.getAttributes();
                String key = "";
                String value = "";
                for (int k = 0; k < querys.getLength(); k++) {
                    Attr attr = (Attr) querys.item(k);
                    if (attr.getNodeName().equals("key")) {
                        key = attr.getNodeValue();
                    } else if (attr.getNodeName().equals("value")) {
                        value = attr.getNodeValue();
                    }
                }
                exportQueryData.getQuerys().put(key, value);
            }
        }

        return exportQueryData;
    }

    private Configuration getConfiguration(Node node) {
        Configuration config = new Configuration();
        // 读取属性
        NamedNodeMap nodeProp = node.getAttributes();
        for (int i = 0; i < nodeProp.getLength(); i++) {
            Attr attr = (Attr) nodeProp.item(i);
            if (attr.getNodeName().equals("id")) {
                config.setId(attr.getNodeValue());
            } else if (attr.getNodeName().equals("name")) {
                config.setName(attr.getNodeValue());
            } else if (attr.getNodeName().equals("enable")) {
                config.setEnable(attr.getNodeValue());
            } else if (attr.getNodeName().equals("order")) {
                config.setOrder(attr.getNodeValue());
            }
        }
        return config;
    }

    private Viewer getViewer(Node node) {
        Viewer viewer = new Viewer();

        NodeList nodeList = node.getChildNodes();
        int order = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node ccnode = nodeList.item(i);
            if (ccnode.getNodeName().equals("queryCondition")) {
                NamedNodeMap nodeProp = ccnode.getAttributes();
                QueryCondition queryCondition = new QueryCondition();
                queryCondition.setOrder(String.valueOf(++order));
                for (int j = 0; j < nodeProp.getLength(); j++) {
                    Attr attr = (Attr) nodeProp.item(j);
                    if (attr.getNodeName().equals("type")) {
                        queryCondition.setType(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("name")) {
                        queryCondition.setName(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("mapping")) {
                        queryCondition.setMapping(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("format")) {
                        queryCondition.setFormat(attr.getNodeValue());
                    }
                }
                viewer.getQueryConditions().add(queryCondition);
            } else if (ccnode.getNodeName().equals("sumColumns")) {
                String sumColumns = getText(ccnode);
                if (sumColumns != null && !sumColumns.equals("")) {
                    viewer.setSumColumns(split(sumColumns));
                }
            } else if (ccnode.getNodeName().equals("groupColumns")) {
                String groupColumns = getText(ccnode);
                if (groupColumns != null && !groupColumns.equals("")) {
                    viewer.setGroupColumns(split(groupColumns));
                }
            } else if (ccnode.getNodeName().equals("orderColumns")) {
                String orderColumns = getText(ccnode);
                if (orderColumns != null && !orderColumns.equals("")) {
                    viewer.setOrderColumns(split(orderColumns));
                }
            } else if (ccnode.getNodeName().equals("hasNum")) {
                String hasNum = getText(ccnode);
                if (hasNum != null && !hasNum.equals("")) {
                    viewer.setHasNum(Boolean.valueOf(hasNum));
                } else {
                    /** 当没配置，默认为没有序号 */
                    viewer.setHasNum(false);
                }
            } else if (ccnode.getNodeName().equals("hiddenColumns")) {
                String hiddenColumns = getText(ccnode);
                if (hiddenColumns != null && !hiddenColumns.equals("")) {
                    viewer.setHiddenColumns(split(hiddenColumns));
                }
            } else if (ccnode.getNodeName().equals("max")) {
                viewer.setMax(Integer.parseInt(ccnode.getFirstChild().getNodeValue()));
            }
        }

        return viewer;
    }

    private CreateTabler getCreateTabler(Node node) {
        CreateTabler createTabler = new CreateTabler();

        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node ccnode = nodeList.item(i);
            if (ccnode.getNodeName().equals("columns")) {
                // 获取节点集合
                NodeList columnsNodeList = ccnode.getChildNodes();

                for (int j = 0; j < columnsNodeList.getLength(); j++) {
                    // 获取节点
                    Node columnNode = columnsNodeList.item(j);

                    if (columnNode.getNodeName().equals("column")) {

                        Column column = new Column();
                        // type 默认值处理
                        column.setType("");
                        // 获取节点属性
                        NamedNodeMap nodeProp = columnNode.getAttributes();
                        for (int k = 0; k < nodeProp.getLength(); k++) {

                            Attr attr = (Attr) nodeProp.item(k);
                            if (attr.getNodeName().equals("type")) {
                                column.setType(attr.getNodeValue());
                            } else if (attr.getNodeName().equals("name")) {
                                column.setName(attr.getNodeValue());
                            } else if (attr.getNodeName().equals("mapping")) {
                                column.setMapping(attr.getNodeValue());
                            } else if (attr.getNodeName().equals("defaultValue")) {
                                column.setDefaultValue(attr.getNodeValue());
                            }
                        }

                        createTabler.getColumns().put(column.getMapping(), column);
                    }
                }
            } else if (ccnode.getNodeName().equals("tableName")) {
                String tableName = ccnode.getFirstChild().getNodeValue();
                createTabler.setTableName(tableName);
            }
        }

        return createTabler;
    }

    private CatchDataer getCatchDataer(Node node) {
        CatchDataer catchDataer = new CatchDataer();

        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node ccnode = nodeList.item(i);
            if (ccnode.getNodeName().equals("authUrl")) {
                catchDataer.setAuthUrl(ccnode.getFirstChild().getNodeValue());
            } else if (ccnode.getNodeName().equals("authUsername")) {
                catchDataer.setAuthUsername(ccnode.getFirstChild().getNodeValue());
            } else if (ccnode.getNodeName().equals("authPassword")) {
                catchDataer.setAuthPassword(ccnode.getFirstChild().getNodeValue());
            } else if (ccnode.getNodeName().equals("downFileUrl")) {
                catchDataer.setDownFileUrl(ccnode.getFirstChild().getNodeValue());
            } else if (ccnode.getNodeName().equals("header")) {

                Header header = new Header();
                // 获取节点属性
                NamedNodeMap nodeProp = ccnode.getAttributes();
                for (int k = 0; k < nodeProp.getLength(); k++) {
                    Attr attr = (Attr) nodeProp.item(k);
                    if (attr.getNodeName().equals("sort_index")) {
                        header.setSort_index(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("description")) {
                        header.setDescription(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("readonly")) {
                        header.setReadonly(attr.getNodeValue());
                    }
                }
                catchDataer.getHeaders().add(header);
            }
        }
        return catchDataer;
    }

    private A3Pluginer getA3Pluginer(Node node) {
        A3Pluginer a3Pluginer = new A3Pluginer();
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node ccnode = nodeList.item(i);
            if (ccnode.getNodeName().equals("funcId")) {
                a3Pluginer.setFuncId(ccnode.getFirstChild().getNodeValue());
            } else if (ccnode.getNodeName().equals("url")) {
                a3Pluginer.setUrl(ccnode.getFirstChild().getNodeValue());
            } else if (ccnode.getNodeName().equals("gl_trntm")) {
                NamedNodeMap nodeProp = ccnode.getAttributes();
                Gl_Trntm gl_trntm = new Gl_Trntm();
                for (int j = 0; j < nodeProp.getLength(); j++) {
                    Attr attr = (Attr) nodeProp.item(j);
                    if (attr.getNodeName().equals("Tr_note")) {
                        gl_trntm.setTr_note(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("Ac_code")) {
                        gl_trntm.setAc_code(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("Tr_cr")) {
                        gl_trntm.setTr_cr(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("Tr_de")) {
                        gl_trntm.setTr_de(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("Tr_custom")) {
                        gl_trntm.setTr_custom(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("Tr_dept")) {
                        gl_trntm.setTr_dept(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("isLoop")) {
                        gl_trntm.setIsLoop(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("Tr_proj")) {
                        gl_trntm.setTr_proj(attr.getNodeValue());
                    }
                }
                a3Pluginer.getGl_Trntm().add(gl_trntm);
            } else if (ccnode.getNodeName().equals("gl_trntm_adjust")) {
                NamedNodeMap nodeProp = ccnode.getAttributes();
                Gl_Trntm gl_trntm = new Gl_Trntm();
                for (int j = 0; j < nodeProp.getLength(); j++) {
                    Attr attr = (Attr) nodeProp.item(j);
                    if (attr.getNodeName().equals("Tr_note")) {
                        gl_trntm.setTr_note(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("Ac_code")) {
                        gl_trntm.setAc_code(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("Tr_cr")) {
                        gl_trntm.setTr_cr(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("Tr_de")) {
                        gl_trntm.setTr_de(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("Tr_custom")) {
                        gl_trntm.setTr_custom(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("Tr_dept")) {
                        gl_trntm.setTr_dept(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("isLoop")) {
                        gl_trntm.setIsLoop(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("Tr_proj")) {
                        gl_trntm.setTr_proj(attr.getNodeValue());
                    }
                }
                a3Pluginer.getGl_Trntm_Adjust().add(gl_trntm);
            }
        }
        return a3Pluginer;
    }

    /**
     * <pre>
     * [,]断开字符串，并清理[/t/n]特殊字符
     * </pre>
     */
    private List<String> split(String str) {
        List<String> ret = new ArrayList<String>();
        String[] strs = str.split(",");
        for (String s : strs) {
            String t = StringUtil.replaceBlank(s);
            if (!"".equals(t)) {
                ret.add(t);
            }
        }
        return ret;
    }

    /**
     * <pre>
     * 获取标签中的值
     * </pre>
     */
    private String getText(Node ccnode) {
        String ret = "";
        if (ccnode != null && ccnode.getFirstChild() != null) {
            ret = ccnode.getFirstChild().getNodeValue();
        }
        return ret;
    }
}
