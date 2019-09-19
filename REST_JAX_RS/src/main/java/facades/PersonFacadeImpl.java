package facades;

import entities.Person;
import exception.PersonNotFoundException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Camilla
 */
public class PersonFacadeImpl implements IPersonFacade {

    private static PersonFacadeImpl instance;
    private static EntityManagerFactory emf;

    private PersonFacadeImpl() {
    }

    public static PersonFacadeImpl getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacadeImpl();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public Person addPerson(String fName, String lName, String phone) {
        if (fName != null && !fName.isEmpty() && lName != null && !lName.isEmpty() && phone != null && !phone.isEmpty()) {
            EntityManager em = getEntityManager();
            try {
                em.getTransaction().begin();
                Person person = new Person(fName, lName, phone);
                em.persist(person);
                em.getTransaction().commit();
                return person;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new IllegalArgumentException("Could not delete");
            } finally {
                em.close();
            }
        } else {
            throw new IllegalArgumentException("Input error");
        }
    }

    @Override
    public Person deletePerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, id);
            em.remove(person);
            em.getTransaction().commit();
            return person;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersonNotFoundException ("Could not delete, provided id does not exist");
        } finally {
            em.close();
        }

    }

    @Override
    public Person getPerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, id);
            em.getTransaction().commit();
            if (person != null) {
                return person;
            } else {
                throw new PersonNotFoundException("No person with provided id found");
            }
        } finally {
            em.close();
        }
    }

    @Override
    public List<Person> getAllPersons() throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            List<Person> people = em.createNamedQuery("Person.getAll").getResultList();
            em.getTransaction().commit();
            if (people != null && !people.isEmpty()) {
                return people;
            } else {
                throw new PersonNotFoundException("No persons could be retrieved");
            }
        } finally {
            em.close();
        }
    }

    @Override
    public Person editPerson(Person person) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            person.setLastEdited(new Date());
            em.merge(person);
            em.getTransaction().commit();
            return person;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersonNotFoundException("Could not edit");
        } finally {
            em.close();
        }
    }

}