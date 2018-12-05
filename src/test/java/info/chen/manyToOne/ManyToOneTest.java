package info.chen.manyToOne;

import info.chen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ManyToOneTest {
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
        /*Customer customer = new Customer();
        customer.setName("CHEN");

        Order order_1 = new Order();
        Order order_2 = new Order();

        order_1.setOrderDesc("JD_1");
        order_2.setOrderDesc("JD_2");

        order_1.setCustomer(customer);
        order_2.setCustomer(customer);

        session.save(customer);
        session.save(order_1);
        session.save(order_2);*/
    }
}
