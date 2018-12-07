package info.chen.bidirectionalOneToOneForeign;

import info.chen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class oneToOneForeignBothTest {

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

        Manager manager = new Manager();
        manager.setName("Lily");
        Department department = new Department();
        department.setName("FINANCE");

        manager.setDepartment(department);
        department.setManager(manager);

        session.save(manager);
        session.save(department);

        /*session.save(department);
        session.save(manager);*/
    }

    @Test
    public void testGet() {
        /*Department department = (Department) session.get(Department.class,1);
        System.out.println(department.getManager().getName());*/

        Manager manager = (Manager) session.get(Manager.class,1);
        System.out.println(manager.getName());
        System.out.println(manager.getDepartment().getName());
    }
}
