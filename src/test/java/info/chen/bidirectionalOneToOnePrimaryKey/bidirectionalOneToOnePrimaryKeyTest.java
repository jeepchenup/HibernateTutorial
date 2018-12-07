package info.chen.bidirectionalOneToOnePrimaryKey;

import info.chen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class bidirectionalOneToOnePrimaryKeyTest {
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
        transaction.commit();
        if (session.isConnected()) {
            session.close();
        }
        sessionFactory.close();
    }

    @Test
    public void testSave() {

        /*Manager manager = new Manager();
        manager.setName("Chen");
        Department department = new Department();
        department.setName("Development");

        manager.setDepartment(department);
        department.setManager(manager);

        session.save(manager);
        session.save(department);*/

        Manager manager = new Manager();
        manager.setName("Bob");
        Department department = new Department();
        department.setName("PERSONNEL");

        manager.setDepartment(department);
        department.setManager(manager);

        session.save(department);
        session.save(manager);
    }

    @Test
    public void testGet() {
        Manager manager = (Manager) session.get(Manager.class,3);
        System.out.println(manager.getName());
        System.out.println(manager.getDepartment());
    }

    @Test
    public void testDelete() {
        Manager manager = (Manager) session.get(Manager.class,4);
        Department department = (Department) session.get(Department.class,4);
        session.delete(department);
        session.delete(manager);
    }

}
