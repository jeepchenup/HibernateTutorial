package info.chen.model.annotation;

import info.chen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserTest {

    private SessionFactory sessionFactory;

    @Before
    public void setup() {
        System.out.println("strating to config session ... ...");
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @After
    public void teardown() {
        System.out.println("strating to garbage collect ... ...");
        if(sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void testQueryUser() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<User> users = session.createQuery("from User_annotation").list();
        for(User user : users) {
            System.out.println(user);
        }

        session.getTransaction().commit();
        session.close();
    }


}
