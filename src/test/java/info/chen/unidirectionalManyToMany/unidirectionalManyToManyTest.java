package info.chen.unidirectionalManyToMany;

import info.chen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class unidirectionalManyToManyTest {
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
        if(session.isConnected()) {
            session.close();
        }
        sessionFactory.close();
    }

    @Test
    public void testSave() {
        Person person = new Person();
        person.setName("Steven");
        person.setGender("male");
        Address address = new Address();
        address.setDescription("China Zhejiang");

        person.getAddresses().add(address);

        session.save(address);
        session.save(person);
    }

    @Test
    public void testGet() {
        Person person = (Person) session.get(Person.class,1);
        System.out.println(person.getName());

        Set<Address> addresses = person.getAddresses();
        Iterator<Address> iterator = addresses.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next().getDescription());
        }
    }

    @Test
    public void testUpdate() {
        Person person = (Person) session.get(Person.class,1);

        Address address = new Address();
        address.setDescription("China Shanghai");

        person.getAddresses().add(address);

        session.save(address);
    }

    @Test
    public void testDelete() {
//        Person person = (Person) session.get(Person.class,1);
        // 单向多对多删除的时候，会直接将中间表中的数据删除
//        session.delete(person);

//        Address address = (Address) session.get(Address.class,3);
        // 会抛出异常
//        session.delete(address);
    }
}
