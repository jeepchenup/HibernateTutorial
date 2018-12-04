package info.chen.hibernate;

import info.chen.model.xml_config.User;
import info.chen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

public class SessionMethodTest {

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
        if(session.isConnected())
            session.close();
        sessionFactory.close();
    }

    @Test
    public void testSave() {
        User user = new User();
        // 设置 ID 是无效的，Hibernate会根据 ID 生成策略来决定 ID 的值
        user.setId(40);
        user.setUsername("cs1");
        user.setFirstname("Grace");
        user.setLastname("Bob");
        user.setRegister_date(new Date());

        session.save(user);
        // 对于持久化对象，Hibernate 是不允许修改对象的 ID
        // 这里会抛出 HibernateException
        user.setId(40);
    }

    @Test
    public void testPersist() {
        User user = new User();
        user.setUsername("cs1");
        user.setFirstname("Grace");
        user.setLastname("Bob");
        user.setRegister_date(new Date());

        // 在执行 persist 之前，如果给临时状态的对象设置了 ID
        // 将会抛出 PersistentObjectException
//        user.setId(40);
        session.persist(user);
    }

    @Test
    public void testGet() {
        // 1. get 方法是立即加载，
        // 也就是说不管加载的对象有没有被引用都会立即去数据库里面查询
        User user = (User) session.get(User.class, 1);
        System.out.println(user);

        // 2. 再次获取 ID 为1 的 user 持久对象
        // user 已经在 session 中， 直接从session 中获取
        // 不会再发一次 SQL 语句
        User userAgain = (User) session.get(User.class, 1);
        System.out.println(user);


        // 3. 尝试获取一个不存在的 user 持久对象
        User userNotExisted = (User) session.get(User.class, 1000);
        System.out.println(userNotExisted);

        // 4. 在使用持久类之前关闭session
        User user_2 = (User) session.get(User.class, 2);
        session.close();
        System.out.println(user_2);
    }

    @Test
    public void testLoad() {
        // 1. 执行 load 方法，若不使用这个对象，则不会立即执行查询操作，
        // 而返回一个这个类的代理对象实例
        User user = (User) session.load(User.class, 1);
        // 生成的是一个代理对象
        System.out.println(user.getClass().getName());
        // 开始执行 SQL 查询
        System.out.println(user);

        // 2. 重复查询
        User userAgain = (User) session.load(User.class, 1);
        // 因为 session 中已经存在，不需要再次执行 SQL
        System.out.println(user);

        // 3. 尝试加载一个不存在的对象到内存中
        User userNotExisted = (User) session.load(User.class, 1000);
        // 这里会抛出 ObjectNotFoundException
        //        System.out.println("query id = 1000 , " + userNotExisted);

        // 4. 在使用持久类之前关闭session
        User user_2 = (User) session.load(User.class, 2);
        session.close();
        // 这里会抛出 LazyInitializationException
        System.out.println(user_2);
    }

    @Test
    public void testUpdate() {
        User user = (User) session.get(User.class, 1);

        session.close();
        // session 关闭之后，user变成了游离状态
        user.setUsername("miaoc4");

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        // 在同一个 session 中只能存在一个唯一的user，否则会抛出异常
//        User user_2 = (User) session.get(User.class, 1);

        session.update(user);
    }

    @Test
    public void testSaveOrUpdate_1() {
        // 测试 save
        User user = new User();
        user.setUsername("hy1");
        user.setFirstname("Haylee");
        user.setLastname("Hong");
        user.setRegister_date(new Date());

        session.saveOrUpdate(user);
    }

    @Test
    public void testSaveOrUpdate_2() {
        // 测试 update
        User user = new User();
        user.setUsername("ggg");
        user.setId(111);

        session.saveOrUpdate(user);

        System.out.println("----");

    }

    @Test
    public void testDelete() {
//        User user = new User();
//        user.setId(5);
//        session.delete(user);

        User user = (User) session.get(User.class, 4);
        session.delete(user);

        System.out.println(user);
    }

    @Test
    public void testEvict() {
        User user = (User) session.get(User.class, 1);
        user.setUsername("aaa");
        session.evict(user);
        System.out.println(user);
    }
}
