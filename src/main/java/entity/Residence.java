package entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "residence")
public class Residence {

    @Id
    private String id;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "number", nullable = false)
    private int number;

    @ManyToOne
    @JoinColumn(name = "fk_user", nullable = true)
    private User user;

    public Residence(String city, String street, int number, User user) {
        this.id = UUID.randomUUID().toString();
        this.city = city;
        this.street = street;
        this.number = number;
        this.user = user;
    }

    public Residence(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString(){
        return city + ", str. " + street + ", nr. " + number;
    }
}
