package info.chen.bidirectionalManyToMany;

import java.util.HashSet;
import java.util.Set;

public class Address {

    private Integer id;
    private String description;
    private Set<Person> persons = new HashSet<Person>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }
}
