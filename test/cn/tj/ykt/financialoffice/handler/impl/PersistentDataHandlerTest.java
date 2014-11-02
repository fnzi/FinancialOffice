package cn.tj.ykt.financialoffice.handler.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import cn.tj.ykt.financialoffice.kernel.internal.message.MessageBroker;
import cn.tj.ykt.financialoffice.system.cfg.Column;
import cn.tj.ykt.financialoffice.test.BaseTest;

public class PersistentDataHandlerTest extends BaseTest {

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
}
