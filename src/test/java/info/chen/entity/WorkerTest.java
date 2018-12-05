package info.chen.entity;

import info.chen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WorkerTest {

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
    public void testComponent() {
        Worker worker = new Worker();
        worker.setName("Grace");

        Pay pay = new Pay();
        pay.setMonthPay(12000);
        pay.setYearPay(180000);
        pay.setVocationDays(15);

        worker.setPay(pay);
        session.save(worker);
    }
}
