package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Camilla
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Customer.getAll", query = "SELECT c FROM Customer c")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private List<Orda> orders = new ArrayList<>();

    public Customer() {
    }

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void addOrder(Orda order) {
        this.orders.add(order);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Orda> getOrders() {
        return orders;
    }

    public void setOrders(List<Orda> orders) {
        this.orders = orders;
    }
    
    
}
