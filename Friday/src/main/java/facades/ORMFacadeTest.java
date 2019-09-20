package facades;

import entities.Orda;
import entities.OrderLine;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author Camilla
 */
public class ORMFacadeTest {

    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
        ORMFacadeImpl facade = ORMFacadeImpl.getCustomerFacade(emf);
        
        facade.createItemType("Hat", "Til hovedet", 3);
        facade.createItemType("Ur", "Til armen", 31);
        facade.createItemType("Cigar", "Til munden", 2);
        
        System.out.println("ITEMTYPE 1 " + facade.getItemType(1).getName());

        facade.createCustomer("Hans", "a@b");
        System.out.println("CUSTOMER: " + facade.getCustomer(1L).getName());
        facade.createOrder(facade.getCustomer(1L), new Orda());
        Orda order1 = facade.allOrdersByCustomer(facade.getCustomer(1L)).get(0);
        facade.createOrderLine(new OrderLine(facade.getItemType(1), 7), order1);
    }
}
