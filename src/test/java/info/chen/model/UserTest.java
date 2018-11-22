package info.chen.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserTest {

    private SessionFactory sessionFactory;

    @Before
    public void setup() {
        System.out.println("start to setup ... ...");
        sessionFactory = new Configuration().configure().buildSessionFactory();
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
}
