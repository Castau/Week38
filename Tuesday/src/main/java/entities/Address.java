package entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Camilla
 */
@Entity
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String city;
    private String street;
//    @ManyToOne(cascade = CascadeType.PERSIST)
//    private Customer customer;
    //    @OneToOne(mappedBy = "address")
    //    private Customer customer;
    @ManyToMany(mappedBy = "addresses", cascade = CascadeType.PERSIST)
    private List<Customer> customers;

    public Address(String city, String street) {
        this.city = city;
        this.street = street;
    }

//    public Address(String city, String street, Customer customer) {
//        this.city = city;
//        this.street = street;
//        this.customer = customer;
//    }

    public Address() {
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }
    
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
    
    public Integer getId() {
        return id;
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

}
