package info.chen.bidirectionalManyToMany;

import info.chen.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class bidirectionalManyToManyTest {

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
        if(session.isConnected()){
            session.close();
        }
        sessionFactory.close();
    }

    @Test
    public void testSave() {
        Person person = new Person();
        person.setName("Steven Chen");

        Address address_1 = new Address();
        address_1.setDescription("Zhejiang");

        Address address_2 = new Address();
        address_2.setDescription("Shanghai");

        Set<Address> addresses = person.getAddresses();
        addresses.add(address_1);
        addresses.add(address_2);

        address_1.getPersons().add(person);
        address_2.getPersons().add(person);

        session.save(person);
        session.save(address_1);
        session.save(address_2);
    }
}
