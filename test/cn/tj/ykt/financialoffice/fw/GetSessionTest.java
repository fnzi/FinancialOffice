package cn.tj.ykt.financialoffice.fw;

import cn.tj.ykt.financialoffice.test.BaseTest;

public class GetSessionTest extends BaseTest {

    // @Test
    // public void test() {
    // HttpSession session = new MockHttpSession();
    // JspService vs = (JspService) SpringUtil.getBean("loginService");
    // vs.setSession(session);
    //
    // User user = new User();
    // user.setUsername("sys");
    // vs.setSessionUser(user);
    // System.out.println(vs.getSessionUser().getUsername());
    //
    //
    // HttpSession session1 = new MockHttpSession();
    // JspService vs1 = (JspService) SpringUtil.getBean("loginService");
    // vs1.setSession(session1);
    //
    // User user1 = new User();
    // user1.setUsername("abc");
    // vs1.setSessionUser(user1);
    // System.out.println(vs1.getSessionUser().getUsername());
    //
    //
    //
    // System.out.println(vs.getSessionUser().getUsername());
    // }

    // @Test
    // public void test002() {
    // for (int i = 0; i < 20; i++) {
    // TestThread tt = new TestThread() {
    // @Override
    // public void run() {
    // CheckSessionService css1 = (CheckSessionService)
    // SpringUtil.getBean("checkSessionService");
    // css1.setMap("id", "1fnzi");
    // Map<String, Object> param = new HashMap<String, Object>();
    // param.put("threadId", "thread1");
    // css1.execute(param);
    // }
    // };
    // tt.start();
    //
    // TestThread2 tt2 = new TestThread2() {
    // @Override
    // public void run() {
    // CheckSessionService css1 = (CheckSessionService)
    // SpringUtil.getBean("checkSessionService");
    // css1.setMap("id", "2sys");
    // Map<String, Object> param = new HashMap<String, Object>();
    // param.put("threadId", "thread2");
    // css1.execute(param);
    // }
    // };
    // tt2.start();
    // }
    // try {
    // Thread.sleep(1000 * 30);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // }
    //
    // @Test
    // public void test003() {
    // for (int i = 0; i < 20; i++) {
    // TestThread tt = new TestThread() {
    // @Override
    // public void run() {
    // CheckSessionService css1 = (CheckSessionService)
    // SpringUtil.getBean("checkSessionService");
    // css1.setMap1("id", "1fnzi");
    // Map<String, Object> param = new HashMap<String, Object>();
    // param.put("threadId", "thread1");
    // css1.execute(param);
    // }
    // };
    // tt.start();
    //
    // TestThread2 tt2 = new TestThread2() {
    // @Override
    // public void run() {
    // CheckSessionService css1 = (CheckSessionService)
    // SpringUtil.getBean("checkSessionService");
    // css1.setMap1("id", "2sys");
    // Map<String, Object> param = new HashMap<String, Object>();
    // param.put("threadId", "thread2");
    // css1.execute(param);
    // }
    // };
    // tt2.start();
    // }
    // try {
    // Thread.sleep(1000 * 30);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // }
    //
    // /**
    // * @author rennver
    // */
    // @Test
    // public void test004() {
    // TestThread tt = new TestThread() {
    // @Override
    // public void run() {
    // for (int i = 0; i < 20; i++) {
    // CheckSessionService css1 = (CheckSessionService)
    // SpringUtil.getBean("checkSessionService");
    // synchronized (css1) {
    // css1.setMap1("id", "1fnzi");
    // Map<String, Object> param = new HashMap<String, Object>();
    // param.put("threadId", "thread1");
    // css1.execute(param);
    // }
    // }
    // }
    // };
    // tt.start();
    //
    // TestThread2 tt2 = new TestThread2() {
    // @Override
    // public void run() {
    // for (int i = 0; i < 20; i++) {
    // CheckSessionService css1 = (CheckSessionService)
    // SpringUtil.getBean("checkSessionService");
    // synchronized (css1) {
    // css1.setMap1("id", "2sys");
    // Map<String, Object> param = new HashMap<String, Object>();
    // param.put("threadId", "thread2");
    // css1.execute(param);
    // }
    // }
    // }
    // };
    // tt2.start();
    // try {
    // Thread.sleep(1000 * 30);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }
    // }
    //
//    @Test
    public void test005() {
        for (int i = 0; i < 20; i++) {
            new Thread() {
                @Override
                public void run() {
//                    HttpSession session = new MockHttpSession();
//                    session.setAttribute("id", "1fnzi");
//                    CheckSessionService css1 = (CheckSessionService) SpringUtil.getBean("checkSessionService");
//                    css1.setCurrentSession(session);
//                    Map<String, Object> param = new HashMap<String, Object>();
//                    param.put("threadId", "thread1");
//                    css1.execute(param);
                }
                
                
            }.start();

//            TestThread2 tt2 = new TestThread2() {
//                @Override
//                public void run() {
//                    HttpSession session = new MockHttpSession();
//                    session.setAttribute("id", "2sys");
//                    CheckSessionService css1 = (CheckSessionService) SpringUtil.getBean("checkSessionService");
//                    css1.setCurrentSession(session);
//                    Map<String, Object> param = new HashMap<String, Object>();
//                    param.put("threadId", "thread2");
//                    css1.execute(param);
//                }
//            };
//            tt2.start();
        }
        try {
            Thread.sleep(1000 * 30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

interface test001 {
    public void ttt();
    public void tttt();
}
