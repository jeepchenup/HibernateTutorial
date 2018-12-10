package info.chen.inheritance_mapping.joinedSubclass;

import info.chen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JoinedSubclassTest {
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
        person.setAge(18);
        person.setName("Grace");

        session.save(person);

        /**
         * 使用 joined-subclass 会生成多张表（根据实际配置子类的数量）
         * 如果是在插入子类对象的时候，除了会对子表进行插入操作
         * 与此同时，还会对母表也进行插入的操作
         * 因为子表的主码引自母表的主码，也就是外键
         */
        Student student = new Student();
        student.setName("Bob");
        student.setAge(16);
        student.setSchoolName("Zhejiang University");
        session.save(student);
    }

    @Test
    public void testGet() {
        Person person = (Person) session.get(Person.class,1);
        System.out.println(person.getName());

        Student student = (Student) session.get(Student.class,2);
        System.out.println(student.getSchoolName());
    }

    @Test
    public void testUpdate() {
        /*Person person = (Person) session.get(Person.class,1);
        person.setAge(28);*/

        Student student = (Student) session.get(Student.class,2);
//        student.setAge(26);
        student.setSchoolName("Beijing University");
    }

    @Test
    public void testDelete() {
        Student student = new Student();
        student.setId(2);

        session.delete(student);
    }
}
