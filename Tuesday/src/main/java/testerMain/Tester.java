package testerMain;

import entities.Address;
import entities.Customer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Camilla
 */
public class Tester {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        Persistence.generateSchema("pu", null);
        EntityManager em = emf.createEntityManager();    
        
        Customer customer1 = new Customer("Alfred","Hattemager");
        customer1.addHobby("Heste");
        customer1.addHobby("Frøer");
        customer1.addHobby("Fiskeri");
//        customer1.addAddress(new Address("Smørumnedre", "Hattemagervej 13", customer1));
//        customer1.addAddress(new Address("Smørumnedre", "Hattemagervej 14", customer1));
        customer1.addAddress(new Address("Smørumnedre", "Hattemagervej 13"));
        customer1.addAddress(new Address("Sønderomme", "Urmagervej 44"));
        customer1.addPhone("12345678", "Fastnet");
        customer1.addPhone("87456321", "Mobil");
        
        Customer customer2 = new Customer("Rigmor","Ursen");
        customer2.addHobby("Fiskeri");
        customer2.addHobby("Førstehjælp");
        customer2.addHobby("Madlavning");
//        customer2.addAddress(new Address("Sønderomme", "Urmagervej 43", customer2));
//        customer2.addAddress(new Address("Sønderomme", "Urmagervej 44", customer2));
        customer2.addAddress(new Address("Sønderomme", "Urmagervej 43"));
        customer2.addAddress(new Address("Sønderomme", "Urmagervej 44"));
        customer2.addPhone("18273645", "Fastnet");
        customer2.addPhone("81726354", "Mobil");
        
        
        try {
            em.getTransaction().begin();
            em.persist(customer1);
            em.persist(customer2);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
}
