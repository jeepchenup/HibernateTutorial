package info.chen.HQL;

import info.chen.util.HibernateUtil;
import org.hibernate.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

public class SecondCacheTest {

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
        if (session.isConnected()) {
            session.close();
        }
        sessionFactory.close();
    }

    @Test
    public void testClassSecondCache() {// 类级别的二级缓存

        Employee employee_1 = (Employee) session.get(Employee.class,1);
        System.out.println(employee_1.getName());

        session.close();
        session = sessionFactory.openSession();

        Employee employee_2 = (Employee) session.get(Employee.class,1);
        // 开启了 二级缓存之后，原来的事务隔离级别就不起作用了，需要手动调用session.refresh() 方法来同步
        session.refresh(employee_2);
        System.out.println(employee_2.getName());
    }

    @Test
    public void testCollectionSecondCache() {//集合级别的二级缓存
        Department department_1 = (Department) session.get(Department.class,1);
        System.out.println(department_1.getName());
        System.out.println(department_1.getEmployees().size());

        session.close();
        session = sessionFactory.openSession();

        Department department_2 = (Department) session.get(Department.class,1);
        System.out.println(department_2.getName());
        System.out.println(department_2.getEmployees().size());
        Set<Employee> employees = department_2.getEmployees();
        for(Employee employee : employees) {
            System.out.println(employee);
            break;
        }
    }

    @Test
    public void testEHCacheConfiguration() {
        Department department = (Department) session.get(Department.class,1);
        Set<Employee> employees = department.getEmployees();
        System.out.println(employees.size());
    }

    @Test
    public void testQueryCache_HQL() {// 查询缓存 - HQL
        Query query = session.createQuery("FROM Employee");

        query.setMaxResults(5);
        List<Employee> employees = query.setCacheable(true).list();
        System.out.println(employees.size());

        employees = query.list();
        System.out.println(employees.size());
    }

    @Test
    public void testQueryCache_QBC() {
        Criteria criteria = session.createCriteria(Employee.class);

        criteria.setMaxResults(5).setCacheable(true);

        List<Employee> employees = criteria.list();
        System.out.println(employees.size());

        employees = criteria.list();
        System.out.println(employees.size());
    }

}
