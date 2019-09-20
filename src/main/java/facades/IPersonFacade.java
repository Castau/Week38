package facades;

import entities.Address;
import entities.Person;
import exception.PersonNotFoundException;
import java.util.List;
/**
 *
 * @author Camilla
 */
public interface IPersonFacade {

    public Person addPerson(String fName, String lName, String phone);

    public Person deletePerson(int id) throws PersonNotFoundException;

    public Person getPerson(int id) throws PersonNotFoundException;

    public List<Person> getAllPersons() throws PersonNotFoundException;

    public Person editPerson(Person p) throws PersonNotFoundException;
    
    public Address getPersonAddress(Address address) throws PersonNotFoundException;
}
