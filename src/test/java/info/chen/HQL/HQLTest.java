package info.chen.HQL;

import info.chen.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class HQLTest {
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
    public void testHQL() {
        // 1. 创建 SQL
        Query query = session.createQuery("FROM Employee e WHERE e.salary > ?");

        // 2. 设置属性
        query.setFloat(0, 150000);

        // 3. 进行查询
        List<Employee> employeeList = query.list();
        System.out.println(employeeList.size());
    }

    @Test
    public void testHQLNamedParameter() {
        // 1. 创建 SQL
        Query query = session.createQuery("FROM Employee e WHERE e.salary > :salary");

        // 2. 设置属性
        query.setFloat("salary", 150000);

        // 3. 进行查询
        List<Employee> employeeList = query.list();
        System.out.println(employeeList.size());
    }

    @Test
    public void testHQLObjParameter() {
        Query query = session.createQuery("FROM Employee e WHERE e.salary > :salary1 AND e.salary < :salary2 AND e.department = :department");
        Department department = new Department();
        department.setId(4);
        query.setFloat("salary1", 80000).setFloat("salary2", 90000).setEntity("department", department);
        List<Employee> employeeList = query.list();
        System.out.println(employeeList.size());
    }
}
