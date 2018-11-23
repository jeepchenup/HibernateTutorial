package info.chen.model.envers;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "user")
@Table(name = "user")
@Audited
public class User {

    private int id;
    private String username;
    private String firstname;
    private String lastname;
    private Date regist_date;

    public User() {
    }

    public User(String username, String firstname, String lastname, Date regist_date) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.regist_date = regist_date;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    public Date getRegist_date() {
        return regist_date;
    }

    public void setRegist_date(Date regist_date) {
        this.regist_date = regist_date;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", regist_date=" + regist_date +
                '}';
    }
}
