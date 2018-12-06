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
        Customer customer = new Customer();
        customer.setName("CHEN");

        Order order_1 = new Order();
        Order order_2 = new Order();

        order_1.setOrderDesc("JD_1");
        order_2.setOrderDesc("JD_2");

        order_1.setCustomer(customer);
        order_2.setCustomer(customer);

        // 3 条 INSERT 语句
//        session.save(customer);
//        session.save(order_1);
//        session.save(order_2);

        // 3 条 INSERT 语句，2 条 UPDATE 语句
        session.save(order_1);
        session.save(order_2);
        session.save(customer);
    }

    @Test
    public void testGet() {
        // 1. 若只查询多的一端信息（不包含一的一端信息）
        // 只会搜索多的一端表的信息，一的一端表信息是不会被搜索（懒加载的原因）
        Order order = (Order) session.get(Order.class,1);
        System.out.println(order.getOrderDesc());

//        session.close(); session // 关闭会导致 LazyInitializationException

        // 2. 需要用到关联对象的时候，才会进行相应的SQL查询
        Customer customer = order.getCustomer();
        // 是一个代理对象
        System.out.println(customer.getClass().getName());
        System.out.println(customer.getName());
    }

    @Test
    public void testUpdate() {
        Order order = (Order) session.get(Order.class,1);
        order.getCustomer().setName("Chen");
    }

    @Test
    public void testDelete() {
        Order order = (Order) session.get(Order.class,1);
        session.delete(order.getCustomer());
    }
}
