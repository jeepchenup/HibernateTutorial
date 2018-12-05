package info.chen.model.xml_config;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {

    private int id;
    private String username;
    private String firstname;
    private String lastname;
    private Date register_date;
    private String description;

    //大文本
    private String content;

    // 二进制数据
    private Blob image;

    public User() {
    }

    public User(String username, String firstname, String lastname, Date date) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.register_date = date;
    }

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

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String register_date_str = "";
        if(register_date != null)
            register_date_str = sdf.format(register_date);
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", register_date=" + register_date_str +
                '}';
    }
}
