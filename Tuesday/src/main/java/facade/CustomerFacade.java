package facade;

import entities.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Camilla
 */
public class CustomerFacade {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

    public Customer getCustomer(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Customer.class, new Long(id));
        } finally {
            em.close();
        }
    }

    public List<Customer> getCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("Select e FROM Customer e");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Customer addCustomer(Customer cust) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cust);
            em.getTransaction().commit();
            return cust;
        } catch (Exception e) {
            em.getTransaction().rollback();

        } finally {
            em.close();
        }
        return null;
    }

    public void deleteCustomer(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("DELETE FROM Customer e WHERE e.id = :id").setParameter("id", id);
            query.executeUpdate();

        } finally {
            em.close();
        }
    }

    public Customer editCustomer(Customer cust) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(cust);
            em.getTransaction().commit();
            return cust;
        } finally {
            em.close();
        }

    }

}
