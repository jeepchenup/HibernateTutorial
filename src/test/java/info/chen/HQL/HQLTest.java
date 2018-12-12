package info.chen.HQL;

import info.chen.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
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

    @Test
    public void testHQLPageQuery() {
        /* 分页查询 */

        String SQL = "FROM Employee";
        Query query = session.createQuery(SQL);

        // 页数
        int pageNo = 3;
        // 每页显示记录数量
        int pageSize = 5;

        query.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize);
        List<Employee> employees = query.list();
        System.out.println(employees);
    }

    @Test
    public void testHQLNamedQuery() {
//        使用 query 标签来查询
        Query query = session.getNamedQuery("salaryEmployees");
        query.setFloat("minSalary", 80000)
                .setFloat("maxSalary",90000);

        List<Employee> employees = query.list();
        System.out.println(employees.size());
    }

    @Test
    public void testHQLFiledQuery() {
        // 1 ==> 只查询一列时
        Query query = session.createQuery("SELECT e.salary FROM Employee e WHERE e.salary > :salary AND e.department = :department");
        Department department = new Department();
        department.setId(7);
        query.setFloat("salary", 156000)
                .setEntity("department", department);

        List<Float> salaries = query.list();
//        System.out.println(query.list().getClass().getName());
        for(Float salary : salaries) {
            System.out.println(salary);
        }

        // 2 ==> 查询多列时
        query = session.createQuery("SELECT e.salary, e.department FROM Employee e WHERE e.salary > :salary AND e.department = :department");
        query.setFloat("salary", 156000)
                .setEntity("department", department);
        List<Object[]> objects = query.list();
        for (Object[] arrary : objects) {
            System.out.println(Arrays.asList(arrary));
        }

        // 3 ==> 封装结果集
        query = session.createQuery("SELECT new Employee(e.salary, e.department) FROM Employee e WHERE e.salary > :salary AND e.department = :department");
        query.setFloat("salary", 156000)
                .setEntity("department", department);
        List<Employee> employees = query.list();
        for(Employee employee : employees) {
            System.out.println(employee);
        }
    }

    @Test
    public void testHQLLeftJoinFetch() {
        // 迫切左外连接
        String HQL = "SELECT DISTINCT d FROM Department d LEFT JOIN FETCH d.employees";
        Query query = session.createQuery(HQL);
        List<Department> departments = query.list();
        System.out.println(departments);
        /*for(Department department : departments) {
            // 计算每个部门里面的人数
            System.out.println(department.getName() + " - " + department.getEmployees().size());
        }*/
    }

    @Test
    public void testHQLLeftJoin() {
        String HQL = "SELECT DISTINCT d FROM Department d LEFT JOIN d.employees";
        Query query = session.createQuery(HQL);
        /*List<Object[]> list = query.list();
        for(Object[] object : list) {
            System.out.println(Arrays.asList(object));
            break;
        }*/

        List<Department> departments = query.list();
        System.out.println(departments.size());
        System.out.println(departments);
        for(Department department : departments) {
            // LEFT JOIN 是不会会初始化集合对象，只有在使用的时候才会发送相应的 SQL 去查询
            System.out.println(department.getName() + " - " + department.getEmployees().size());
        }
    }

    /**
     * QBC Query By Criteria
     */
    @Test
    public void testQBCHelloWorld() {

        // 创建一个 criteria 对象
        Criteria criteria = session.createCriteria(Employee.class);

        // 添加查询条件
        criteria.add(Restrictions.eq("name", "Kyoichi"));

        // 执行查询
        List<Employee> employees = criteria.list();
        System.out.println(employees.size());
    }

    @Test
    public void testNativeSQL() {
        String SQL = "SELECT e.name, e.salary FROM employee e WHERE id = :id";
        Query query = session.createSQLQuery(SQL);
        query.setInteger("id", 5);

        List<Object[]> employees = query.list();
        System.out.println(employees.size());

        for(Object[] obj : employees) {
            System.out.println(Arrays.asList(obj));
        }
    }
}
