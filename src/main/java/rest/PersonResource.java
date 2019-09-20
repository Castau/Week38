package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Address;
import entities.Person;
import exception.PersonNotFoundException;
import facades.IPersonFacade;
import utils.EMF_Creator;
import facades.PersonFacadeImpl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Camilla
 */
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final IPersonFacade FACADE = PersonFacadeImpl.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/data")
    @Produces({MediaType.APPLICATION_JSON})
    public String data() {
        EntityManager em = EMF.createEntityManager();
        List<Person> personlist = new ArrayList<>();
        personlist.add(new Person("Rigmor", "NoggenFogger", "12345678", new Address("Hattemagervej 13", "3600", "Hillerød")));
        personlist.add(new Person("Baltazar", "Zacharias", "87654321", new Address("Hattemagervej 13", "3600", "Hillerød")));
        personlist.add(new Person("Ulfred", "Satyr", "33333333", new Address("Kongevej 1", "4000", "Roskilde")));
        personlist.add(new Person("Ursula", "Johansen", "22334455", new Address("Kongevej 4", "4000", "Roskilde")));

        try {
//            em.getTransaction().begin();
//            Query query1 = em.createNativeQuery(
//                    "truncate table person.PERSON; "
//                    + "DELETE FROM person.ADDRESS WHERE `id` > 0; "
//                    + "ALTER TABLE person.ADDRESS AUTO_INCREMENT = 1;");
//            query1.executeUpdate();
//            em.getTransaction().commit();

            em.getTransaction().begin();
            Query query1 = em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;");
            query1.executeUpdate();
            em.getTransaction().commit();
            
            em.getTransaction().begin();
            Query query2 = em.createNativeQuery("truncate table person.PERSON;");
            query2.executeUpdate();
            em.getTransaction().commit();
            
            em.getTransaction().begin();
            Query query3 = em.createNativeQuery("truncate table person.ADDRESS;");
            query3.executeUpdate();
            em.getTransaction().commit();
            
            em.getTransaction().begin();
            Query query4 = em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1; ");
            query4.executeUpdate();
            em.getTransaction().commit();
            
//            em.getTransaction().begin();
//            Query query1 = em.createNativeQuery("truncate table person.PERSON;");
//            query1.executeUpdate();
//            em.getTransaction().commit();
//            
//            em.getTransaction().begin();
//            Query query2 = em.createNativeQuery("DELETE FROM person.ADDRESS WHERE `id` > 0;");
//            query2.executeUpdate();
//            em.getTransaction().commit();
//            
//            em.getTransaction().begin();
//            Query query3 = em.createNativeQuery("ALTER TABLE person.ADDRESS AUTO_INCREMENT = 1;");
//            query3.executeUpdate();
//            em.getTransaction().commit();
            
            em.getTransaction().begin();
            em.getTransaction().commit();
            for (Person p : personlist) {
                em.getTransaction().begin();
                em.persist(p);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
        return "{\"msg\": \"startdata created\"}";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersonsDTO() throws PersonNotFoundException {
        PersonsDTO persons = new PersonsDTO(FACADE.getAllPersons());
//        Map all = new HashMap();
//        all.put("all", persons);
        return GSON.toJson(persons);
    }

    @Path("/databaseid/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonDTObyID(@PathParam("id") int id) throws PersonNotFoundException {
        PersonDTO person = new PersonDTO(FACADE.getPerson(id));
        return GSON.toJson(person);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPerson(String p) {
        PersonDTO personDTO = GSON.fromJson(p, PersonDTO.class);
        Person person = FACADE.addPerson(personDTO.getfName(), personDTO.getlName(), personDTO.getPhone());
        PersonDTO responseDTO = new PersonDTO(person);
        return Response.ok(responseDTO).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPerson(String p) throws PersonNotFoundException {
        PersonDTO personDTO = GSON.fromJson(p, PersonDTO.class);
        Person person = FACADE.getPerson(personDTO.getId());
        person.editPerson(personDTO);
        PersonDTO responseDTO = new PersonDTO(FACADE.editPerson(person));
        return Response.ok(responseDTO).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{id}")
    public String deletePerson(@PathParam("id") int id) throws PersonNotFoundException {
        FACADE.deletePerson(id);
        return "{\"msg\": \"removed person\"}";
    }

    @Path("/fail")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String fail() {
        System.out.println(17 / 0);
        return "";
    }
}
