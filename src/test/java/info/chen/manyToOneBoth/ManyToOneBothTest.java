package info.chen.manyToOneBoth;

import com.sun.org.apache.xpath.internal.operations.Or;
import info.chen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
    public void testGet() {
        Customer customer = (Customer) session.get(Customer.class, 1);
        Iterator<Order> iterable = customer.getOrders().iterator();
        while (iterable.hasNext()) {
            System.out.println(iterable.next().getDescription());
        }
    }

    @Test
    public void testUpdate() {
        Customer customer = (Customer) session.get(Customer.class, 1);
        Order order = customer.getOrders().iterator().next();
        System.out.println(order.getDescription());
        order.setDescription("TIANMAO_1");
    }

    @Test
    public void testDelete() {// 级联删除
        Customer customer = (Customer) session.get(Customer.class, 1);
        // 因为 customer_id 作为外键被 orders 表中的记录引用着，不能被删除
        // 如果硬要删除需要配置级联删除 cascade="delete"
        session.delete(customer);
    }

    @Test
    public void testDeleteOrphan() {// 级联删除“孤儿”
        Customer customer = (Customer) session.get(Customer.class, 2);
        customer.getOrders().clear();
    }

    @Test
    public void testCascadeSave() {// 级联保存
        Customer customer = new Customer();
        customer.setName("Graces");

        Order order_1 = new Order();
        Order order_2 = new Order();

        order_1.setDescription("JD_3");
        order_2.setDescription("JD_4");

        order_1.setCustomer(customer);
        order_2.setCustomer(customer);

        customer.getOrders().add(order_1);
        customer.getOrders().add(order_2);

        // 设置 cascade="save-update"
        session.save(customer);
    }

    @Test
    public void testGetOrderBy() {
        // 在 set 标签添加 order-by 属性
        Customer customer = (Customer) session.get(Customer.class,4);
        Iterator<Order> orders = customer.getOrders().iterator();
        while(orders.hasNext()) {
            System.out.println(orders.next().getDescription());
        }
    }
}
