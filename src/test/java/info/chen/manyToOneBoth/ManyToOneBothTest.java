package info.chen.manyToOneBoth;

import com.sun.org.apache.xpath.internal.operations.Or;
import info.chen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class ManyToOneBothTest {
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

        Customer customer = new Customer();
        customer.setName("CHEN");

        Order order_1 = new Order();
        Order order_2 = new Order();

        order_1.setDescription("JD_1");
        order_2.setDescription("JD_2");

        order_1.setCustomer(customer);
        order_2.setCustomer(customer);

        customer.getOrders().add(order_1);
        customer.getOrders().add(order_2);

        // 3 条 INSERT 2 条 UPDATE
        /*session.save(customer);
        session.save(order_1);
        session.save(order_2);*/

        // 3 条 INSERT 4 条 UPDATE
        /*session.save(order_1);
        session.save(order_2);
        session.save(customer);*/

        // 通过添加 inverse = true，反转给多的一端
        // 能够减少不必要的 update 方法
        // 3 条 INSERT
        session.save(customer);
        session.save(order_1);
        session.save(order_2);

        // 3 条 INSERT 2 条 UPDATE
        /*session.save(order_1);
        session.save(order_2);
        session.save(customer);*/
    }

    @Test
    public void testGet() throws InterruptedException {
        Customer customer = (Customer) session.get(Customer.class, 1);
        Iterator<Order> iterable = customer.getOrders().iterator();
//        System.out.println(customer.getOrders().size());
        if(iterable.hasNext()) {
            Order order = iterable.next();
//            Order order1 = iterable.next();
            System.out.println(order.getDescription());
        }
    }
}
