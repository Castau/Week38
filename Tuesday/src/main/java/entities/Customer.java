package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Camilla
 */
@Entity
public class Customer implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="CUSTOMER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String firstName;
    private String lastName;
    
//    @OneToOne
//    private Address address;
//    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn
//    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Address> addresses = new ArrayList();
    
    @ElementCollection
    @CollectionTable(
            name = "hobbies"
    )
    @Column(name = "HOBBY")
    private List<String> hobbies = new ArrayList();
    
    @ElementCollection(fetch = FetchType.LAZY)
    @MapKeyColumn(name = "PHONE")
    @Column(name = "Description")
    private Map<String, String> phones = new HashMap();

    public Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
    }
    
    public void addHobby(String hobby){
        hobbies.add(hobby);
    }
    
    public String getHobbies(){
        return String.join(",", hobbies);
    }
    
    public int getId() {
        return id;
    }
    
    public void addPhone(String phoneNo, String description) {
        phones.put(phoneNo, description);
    }

    public String getPhoneDescription(String phoneNo) {
        return phones.get(phoneNo);
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}