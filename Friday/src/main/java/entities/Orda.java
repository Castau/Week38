package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Camilla
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Orda.getOrdaByCustomer", query = "SELECT o FROM Orda o WHERE o.customer = :customer")})
public class Orda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Customer customer;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<OrderLine> orderlines = new ArrayList<>();

    public Orda() {
    }

    public void addOrder(OrderLine item) {
        this.orderlines.add(item);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderLine> getOrders() {
        return orderlines;
    }

    public void setOrders(List<OrderLine> orders) {
        this.orderlines = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

}
