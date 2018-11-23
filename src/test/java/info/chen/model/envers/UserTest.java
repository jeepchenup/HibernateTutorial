package info.chen.model.envers;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class UserTest {

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setup() {
        entityManagerFactory = Persistence.createEntityManagerFactory("User_JPA");
    }

    @After
    public void teardown() {
        if(entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Test
    public void testBasicUsage() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(new User("chens24","Steven", "Chen", new Date()));
        entityManager.persist(new User("miaoc4","Colin", "Miao", new Date()));

        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, 2);
        user.setFirstname("(rescheduled)" + user.getFirstname());

        AuditReader reader = AuditReaderFactory.get(entityManager);
        User first_user = reader.find(User.class, 2, 1);
        System.out.println(user);
        User second_user = reader.find(User.class, 2, 2);
        System.out.println(second_user);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
