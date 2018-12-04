package info.chen.hibernate;

import info.chen.model.xml_config.User;
import info.chen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SessionCacheTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void setup() {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void tearDown() {
        // commit() 之前会进行 flush() 操作
        transaction.commit();
        if(session.isConnected()) {
            session.close();
        }
        sessionFactory.close();
    }

    @Test
    public void testFlush() {
        User user = (User) session.get(User.class, 1);
        user.setFirstname("Colin");
        user.setLastname("Miao");
        user.setUsername("miaoc4");
        session.flush();
        System.out.println("finish session flush");
    }

    @Test
    public void testRefresh() {
        User user = (User) session.get(User.class, 1);
        System.out.println(user);

        // 这里使用了 mysql 作为数据库
        // 因为 mysql 的默认事务隔离级别是可重读
        // 所以为了测试 refresh 效果，我们通过修改 hibernate.cfg.xml 中的配置
        // connection.isolation = 2，将事务隔离级别设置为读已提交
        session.refresh(user);
        User user_modify = (User) session.get(User.class, 1);
        System.out.println(user_modify);
    }

    @Test
    public void testClear() {
        User user = (User) session.get(User.class, 1);
        System.out.println(user);

        session.clear();
        User userAgain = (User) session.get(User.class, 1);
        System.out.println(user);
    }
}
