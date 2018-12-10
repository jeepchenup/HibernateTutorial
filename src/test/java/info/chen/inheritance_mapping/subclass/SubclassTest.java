package info.chen.inheritance_mapping.subclass;

import info.chen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SubclassTest {
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

    @Test
    public void testGet() {
        Person person = (Person) session.get(Person.class,2);
        System.out.println(person.getName());


        Student student = (Student) session.get(Student.class,1);
        System.out.println(student.getSchoolName());
    }

    @Test
    public void testUpdate() {
        Person person = (Person) session.get(Person.class,2);
        person.setAge(16);

        Student student = (Student) session.get(Student.class,1);
        student.setSchoolName("Zhejiang University");
    }
}
