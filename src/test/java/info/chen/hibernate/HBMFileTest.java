package info.chen.hibernate;

import info.chen.model.xml_config.User;
import info.chen.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

public class HBMFileTest {

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
    public void testDynamicUpdate() {
        // 需要在 HBM 文件上面添加 dynamic-update="true"
        User user = (User) session.get(User.class,1);

        user.setUsername("(dynamic) " + user.getUsername());
    }

    @Test
    public void testDynamicInsert() {
        User user = new User();
        user.setUsername("chens24");
        user.setFirstname("STEVEN");
        user.setLastname("CHEN");
        user.setRegister_date(new Date());

        session.save(user);
    }

    @Test
    public void testPropertyUpdate() {
        User user = (User) session.get(User.class, 32768);
        System.out.println(user.getDescription());
    }


    @Test
    public void testInsertBlob() throws IOException {
        User user = new User();
        user.setUsername("miaoc4");
        user.setFirstname("COLIN");
        user.setLastname("MIAO");
        user.setRegister_date(new Date());
        user.setContent("fff");

        InputStream is = new FileInputStream("Koala.jpg");
        Blob image = Hibernate.getLobCreator(session)
                              .createBlob(is, is.available());

        user.setImage(image);

        session.save(user);
    }

    @Test
    public void testReadBlob() throws SQLException, IOException {
        User user = (User) session.get(User.class, 1);
        Blob image = user.getImage();
        System.out.println(image.getBinaryStream().available());
    }
}
