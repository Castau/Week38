package facades;

import entities.Customer;
import entities.ItemType;
import entities.Orda;
import entities.OrderLine;
import java.util.List;

/**
 *
 * @author Camilla
 */
public interface IormFacade {

    public Customer createCustomer(String name, String email);

    public Customer getCustomer(Long id);

    public List<Customer> getAllCustomers();

    public ItemType createItemType(String name, String description, int price);

    public ItemType getItemType(long id);

    public Customer createOrder(Customer customer, Orda order);

    public Orda createOrderLine(OrderLine orderLine, Orda order);

    public List<Orda> allOrdersByCustomer(Customer customer);

    public int orderPrice(Long orderid);
}
