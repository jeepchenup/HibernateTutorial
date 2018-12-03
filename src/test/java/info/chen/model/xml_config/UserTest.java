package info.chen.model.xml_config;

import info.chen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class UserTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void setup() {
        System.out.println("start to setup ... ...");
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void teardown() {
        System.out.println("comming.. ..");
        transaction.commit();
        session.close();
        System.out.println("start to clear garbage ... ...");
        if(sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void testQueryUser() {
        List<User> userList = session.createQuery("from User").list();

        for(User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void testInsertUser() {
        session.save(new User("chens24", "Steven","Chen", new Date()));
    }

    @Test
    public void testSessionCache() {// 一级缓存
        User user_1 = (User) session.get(User.class, 1);
        System.out.println(user_1);

        User user_2 = (User) session.get(User.class,1);
        System.out.println(user_2);
    }

    @Test
    public void testSessionFlush() {
        User user = (User) session.get(User.class, 1);
        user.setFirstname("GGG");
//        session.createCriteria(User.class).list();
//        System.out.println(user);
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setFirstname("Colin");
        user.setLastname("Miao");
        user.setUsername("miaoc4");
        user.setId(40);// 在save方法之前设置的 ID 是无效的
        user.setRegister_date(new Date());

        session.save(user);
        // Hibernate 不允许改变持久对象的 ID
//        user.setId(40); // 这里会抛出 HibernateException

        System.out.println(user);
    }

    @Test
    public void testPersist() {

    }
}
