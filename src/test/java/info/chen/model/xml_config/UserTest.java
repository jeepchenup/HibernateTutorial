package info.chen.model.xml_config;

import info.chen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

    @Before
    public void setup() {
        System.out.println("start to setup ... ...");
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @After
    public void teardown() {
        System.out.println("start to clear garbage ... ...");
        if(sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void testQueryUser() {
        Session session = sessionFactory.openSession();
//        开启事务
        session.beginTransaction();
        List<User> userList = session.createQuery("from User").list();

        for(User user : userList) {
            System.out.println(user);
        }

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testInsertUser() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(new User("test1", "ABei","Dragon", new Date()));
        session.getTransaction().commit();
        session.close();
    }
}
