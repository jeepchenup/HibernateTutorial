package info.chen.bidirectionalManyToMany;

import java.util.HashSet;
import java.util.Set;

public class Person {
    private Integer id;
    private String name;
    private Set<Address> addresses = new HashSet<Address>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
}
