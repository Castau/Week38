package facades;

import entities.Customer;
import entities.ItemType;
import entities.Orda;
import entities.OrderLine;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Camilla
 */
public class ORMFacadeImpl implements IormFacade {

    private static ORMFacadeImpl instance;
    private static EntityManagerFactory emf;

    private ORMFacadeImpl() {
    }

    public static ORMFacadeImpl getCustomerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ORMFacadeImpl();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public Customer createCustomer(String name, String email) {
        if (name != null && !name.isEmpty() && email != null && !email.isEmpty()) {
            Customer customer = new Customer(name, email);
            EntityManager em = getEntityManager();
            try {
                em.getTransaction().begin();
                em.persist(customer);
                em.getTransaction().commit();
                return customer;
            } finally {
                em.close();
            }
        } else {
            throw new IllegalArgumentException("createCustomer failed");
        }
    }

    @Override
    public Customer getCustomer(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Customer customer = em.find(Customer.class, id);
            em.getTransaction().commit();
            return customer;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            List<Customer> customers = em.createNamedQuery("Customer.getAll", Customer.class).getResultList();
            em.getTransaction().commit();
            return customers;
        } finally {
            em.close();
        }
    }

    @Override
    public ItemType createItemType(String name, String description, int price) {
        if (name != null && !name.isEmpty() && description != null && !description.isEmpty() && !(price <= 0)) {
            ItemType item = new ItemType(name, description, price);
            EntityManager em = getEntityManager();
            try {
                em.getTransaction().begin();
                em.persist(item);
                em.getTransaction().commit();
                return item;
            } finally {
                em.close();
            }
        } else {
            throw new IllegalArgumentException("createItemType failed");
        }
    }

    @Override
    public ItemType getItemType(long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            ItemType item = em.find(ItemType.class, id);
            em.getTransaction().commit();
            return item;
        } finally {
            em.close();
        }
    }

    @Override
    public Customer createOrder(Customer customer, Orda order) {
        if (customer != null && order != null) {
            customer.addOrder(order);
            order.setCustomer(customer);
            EntityManager em = getEntityManager();
            try {
                em.getTransaction().begin();
                em.merge(customer);
                em.getTransaction().commit();
                return customer;
            } finally {
                em.close();
            }
        } else {
            throw new IllegalArgumentException("createOrder failed");
        }
    }

    @Override
    public Orda createOrderLine(OrderLine orderLine, Orda order) {
        if (orderLine != null && order != null) {
            order.addOrder(orderLine);
            EntityManager em = getEntityManager();
            try {
                em.getTransaction().begin();
                em.merge(order);
                em.getTransaction().commit();
                return order;
            } finally {
                em.close();
            }
        } else {
            throw new IllegalArgumentException("createOrderLine failed");
        }
    }

    @Override
    public List<Orda> allOrdersByCustomer(Customer customer) {
        if (customer != null) {
            EntityManager em = getEntityManager();
            try {
                em.getTransaction().begin();
                List<Orda> orders = em.createNamedQuery("Orda.getOrdaByCustomer", Orda.class).setParameter("customer", customer).getResultList();
                em.getTransaction().commit();
                return orders;
            } finally {
                em.close();
            }
        } else {
            throw new IllegalArgumentException("allOrdersByCustomer failed");
        }
    }

    @Override
    public int orderPrice(Long orderid) {
        EntityManager em = getEntityManager();
        int totalprice = 0;
        try {
            em.getTransaction().begin();
            Orda order = em.find(Orda.class, orderid);
            em.getTransaction().commit();

            if (order.getOrders() != null && !order.getOrders().isEmpty()) {
                for (OrderLine item : order.getOrders()) {
                    if (item.getItem() != null && item.getQuantity() > 0) {
                        totalprice = totalprice + (item.getItem().getPrice() * item.getQuantity());
                    }
                }
            }
        } finally {
            em.close();
        }
        return totalprice;
    }
}
