package facades;

import utils.EMF_Creator;
import entities.Person;
import exception.PersonNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

/**
 *
 * @author Camilla
 */
public class PersonFacadeImplTest {

    private static EntityManagerFactory emf;
    private static PersonFacadeImpl facade;

    public PersonFacadeImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = PersonFacadeImpl.getPersonFacade(emf);
    }
    
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        List<Person> personlist = new ArrayList<>();
        personlist.add(new Person("Rigmor", "NoggenFogger", "12345678"));
        personlist.add(new Person("Baltazar", "Zacharias", "87654321"));
        personlist.add(new Person("Ulfred", "Satyr", "33333333"));
        personlist.add(new Person("Ursula", "Johansen", "22334455"));

        try {
            em.getTransaction().begin();
            Query query = em.createNativeQuery("truncate table person_test.PERSON;");
            query.executeUpdate();
            em.getTransaction().commit();
            
            for (Person p : personlist) {
                em.getTransaction().begin();
                em.persist(p);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
    @Test
    public void addPersonTest() {
        Person exp = new Person("Hat", "Hatsen", "22222222");
        Person res = facade.addPerson("Hat", "Hatsen", "22222222", null);
        assertEquals(exp, res);
    }

    @Test
    public void addPersonTest_ERROR() {
        Throwable exp = new IllegalArgumentException();
        Throwable res = assertThrows(IllegalArgumentException.class, () -> {
            facade.addPerson(null, null, null, null);
        });
        assertEquals(exp.getCause(), res.getCause());
    }

    @Test
    public void getPersonTest() throws PersonNotFoundException {
        Person exp = new Person("Baltazar", "Zacharias", "87654321");
        Person res = facade.getPerson(2);
        assertEquals(exp, res);
    }

    @Test
    public void getPersonTest_ERROR() {
        Throwable exp = new PersonNotFoundException("TEST");
        Throwable res = assertThrows(PersonNotFoundException.class, () -> {
            facade.getPerson(6);
        });
        assertEquals(exp.getCause(), res.getCause());
    }

    @Test
    public void getAllPersonsTest() throws PersonNotFoundException {
        List<Person> exp = new ArrayList<>();
        exp.add(new Person("Rigmor", "NoggenFogger", "12345678"));
        exp.add(new Person("Baltazar", "Zacharias", "87654321"));
        exp.add(new Person("Ulfred", "Satyr", "33333333"));
        exp.add(new Person("Ursula", "Johansen", "22334455"));
        List<Person> res = facade.getAllPersons();
        assertEquals(exp, res);
    }

    @Test
    public void editPersonTest() throws PersonNotFoundException {
        Person exp = new Person("Baltazar", "Zacharias", "87654321");
        exp.setId(2);
        exp.setFirstName("Test");
        Person res = facade.editPerson(exp);
        assertEquals(exp, res);
    }
    
}
