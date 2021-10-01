import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import service.core.ClientInfo;
import service.core.Constants;
import service.core.Quotation;
import service.core.QuotationService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class AFQServiceTest {
    private static Registry registry;
    private static ClientInfo clientInfo;
    @BeforeClass
    public static void setup() {
        QuotationService afqService = new AFQService();
        try {
            registry = LocateRegistry.createRegistry(1099);
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(afqService,0);
            registry.bind(Constants.AULD_FELLAS_SERVICE, quotationService);
            //Create mock of ClientInfo and set necessary fields
            clientInfo = mock(ClientInfo.class);
            clientInfo.gender = ClientInfo.MALE;
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }
    @Test
    public void connectionTest() throws Exception {
        QuotationService service = (QuotationService)
                registry.lookup(Constants.AULD_FELLAS_SERVICE);
        assertNotNull(service);
    }

    /*
    Test to check that generateQuotation returns corresponding quotation from the auldfellas service
     */
    @Test
    public void testGenerateQuotation(){
        //Initialise object of the auldfellas service class
        AFQService afqService = new AFQService() ;
        Quotation auldfellasQuotation = afqService.generateQuotation(clientInfo);
        Assert.assertNotNull(auldfellasQuotation);
    }
}