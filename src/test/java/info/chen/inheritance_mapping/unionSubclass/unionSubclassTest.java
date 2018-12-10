package info.chen.inheritance_mapping.unionSubclass;

import info.chen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class unionSubclassTest {
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
    public void teardown() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    @Test
    public void testSave() {
        Person person = new Person();
        person.setName("Grace");
        person.setAge(20);

        Student student = new Student();
        student.setSchoolName("Zhejiang University");
        student.setName("Andy");
        student.setAge(18);

        session.save(student);
        session.save(person);
    }
}
