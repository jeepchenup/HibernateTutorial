package info.chen.model.jpa_config;

import info.chen.model.annotation.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;

public class UserTest {
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setup() {
        System.out.println("starting to config JPA ... ...");
        entityManagerFactory = Persistence.createEntityManagerFactory("User_JPA");
    }

    @After
    public void teardown() {
        System.out.println("starting to collect garbage ... ...");
        if(entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Test
    public void testQueryUser() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<User> users = entityManager.createQuery("from User_annotation", User.class).getResultList();
        for(User user : users) {
            System.out.println(user);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void testInsertUser() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(new User("username_jpa", "firstname_jpa", "lastname_jpa", new Date()));

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
