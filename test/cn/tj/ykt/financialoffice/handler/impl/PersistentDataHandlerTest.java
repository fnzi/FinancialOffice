package cn.tj.ykt.financialoffice.handler.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.system.cfg.Column;

public class PersistentDataHandlerTest {

    @Test
    public void test001() {
        MessageBroker messageBroker = new MessageBroker();
        List<String> l = new ArrayList<String>();
        l.add("20");
        l.add("20");

        List<List<String>> w = new ArrayList<List<String>>();
        w.add(l);
        messageBroker.setData(w);

        messageBroker.setTableName("sss");

        Map<String, Column> columns = new LinkedHashMap<String, Column>();
        columns.put("a", new Column("", "", "a"));
        columns.put("b", new Column("", "", "b"));
        messageBroker.setColumns(columns);

        PersistentDataHandler handler = new PersistentDataHandler();
        handler.process(messageBroker);
    }

    @Test
    public void test002() {
        List<String> datas = new ArrayList<String>();
        datas.add("");
        datas.add("0");
        datas.add("总计");
        datas.add("总计");
        int i = 0;
        for (String s : datas) {
            if ("".equals(s) || "总计".equals(s) || "合计".equals(s) || new BigDecimal(s).compareTo(BigDecimal.ZERO) == 0) {
                i = i + 1;
            }
        }

        if (datas.size() == i) {
            System.out.println("无效");
        } else {
            System.out.println("有效");
        }
    }
}
