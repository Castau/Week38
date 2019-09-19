package rest;

import dto.PersonDTO;
import entities.Person;
import entities.RenameMe;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import utils.EMF_Creator;

/**
 *
 * @author Camilla
 */
//@Disabled
public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static final String TEST_DB = "jdbc:mysql://localhost:3307/person_test";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.CREATE);
        httpServer = startServer();
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        httpServer.shutdownNow();
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
    public void tomtest() {

    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/person").then().statusCode(200);
    }

    @Test
    public void testGetAllPersonsDTO() {
        List<PersonDTO> persons;
        persons = given()
                .contentType("application/json")
                .when().get("/person").then().extract().body()
                .jsonPath().getList("all", PersonDTO.class);

        assertThat(persons, containsInAnyOrder(
                new PersonDTO(new Person("Rigmor", "NoggenFogger", "12345678")),
                new PersonDTO(new Person("Baltazar", "Zacharias", "87654321")),
                new PersonDTO(new Person("Ulfred", "Satyr", "33333333")),
                new PersonDTO(new Person("Ursula", "Johansen", "22334455"))));
    }

    @Test
    public void testGetPersonDTObyID() {
        given()
                .contentType("application/json")
                .get("/person/databaseid/1").then()
                .assertThat().statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", equalTo(1))
                .body("fName", equalTo("Rigmor"))
                .body("lName", equalTo("NoggenFogger"))
                .body("phone", equalTo("12345678"));
    }
    
    @Test
    public void testGetPersonDTObyID_ERROR() {
        given()
                .contentType("application/json")
                .get("/person/databaseid/99").then()
                .assertThat().statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("code", equalTo(404))
                .body("message", equalTo("No person with provided id found"));
    }

    @Test
    public void testAddPerson() {
        given()
                .contentType("application/json")
                .body(new PersonDTO("Ursula", "Johansen", "22334455")).post("/person").then()
                .assertThat().statusCode(HttpStatus.OK_200.getStatusCode())
                .body("fName", equalTo("Ursula"))
                .body("lName", equalTo("Johansen"))
                .body("phone", equalTo("22334455"));
    }
    
    @Test
    public void testAddPerson_ERROR() {
        given()
                .contentType("application/json")
                .body("").post("/person").then()
                .assertThat().statusCode(HttpStatus.INTERNAL_SERVER_ERROR_500.getStatusCode())
                .body("code", equalTo(500))
                .body("message", equalTo("Internal Server Error"));
    }


    @Test
    public void testEditPerson() {
        given()
                .contentType("application/json")
                .body(new PersonDTO(new Person(2, "Anduin", "Zacharias", "87654321"))).when().put("/person").then()
                .assertThat().statusCode(HttpStatus.OK_200.getStatusCode())
                .body("fName", equalTo("Anduin"))
                .body("lName", equalTo("Zacharias"))
                .body("phone", equalTo("87654321"))
                .body("id", equalTo(2));
    }
    
    @Test
    public void testEditPerson_ERROR() {
        given()
                .contentType("application/json")
                .body("").when().put("/person").then()
                .assertThat().statusCode(HttpStatus.INTERNAL_SERVER_ERROR_500.getStatusCode())
                .body("code", equalTo(500))
                .body("message", equalTo("Internal Server Error"));
    }

    @Test
    public void testDeletePerson() {
        given()
                .contentType("application/json")
                .when().delete("/person/delete/4").then()
                .assertThat().statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("removed person"));
    }
    
    @Test
    public void testDeletePerson_ERROR() {
        given()
                .contentType("application/json").when()
                .delete("/person/delete/99").then().assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("code", equalTo(404))
                .body("message", equalTo("Could not delete, provided id does not exist"));
    }
}
