package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Customer;
import facades.IormFacade;
import facades.ORMFacadeImpl;
import utils.EMF_Creator;
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
@Path("orm")
public class ORMResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final IormFacade FACADE = ORMFacadeImpl.getCustomerFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/data")
    @Produces({MediaType.APPLICATION_JSON})
    public String data() {
        EntityManager em = EMF.createEntityManager();
        return "{\"msg\": \"startdata created\"}";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllCustomers()  {
        List<Customer> customers = FACADE.getAllCustomers();
        return GSON.toJson(customers);
    }
}
