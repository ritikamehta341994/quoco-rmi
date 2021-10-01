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
public class DDQServiceTest {
    private static Registry registry;
    @BeforeClass
    public static void setup() {
        QuotationService ddqService = new DDQService();
        try {
            registry = LocateRegistry.createRegistry(1099);
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(ddqService,0);
            registry.bind(Constants.DODGY_DRIVERS_SERVICE, quotationService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }
    @Test
    public void connectionTest() throws Exception {
        QuotationService service = (QuotationService)
                registry.lookup(Constants.DODGY_DRIVERS_SERVICE);
        assertNotNull(service);
    }

    /*
    Test to check that generateQuotation returns corresponding quotation from the dodgydriver service
     */
    @Test
    public void testGenerateQuotation(){
        //Initialise object of the dodgydriver service class
        DDQService ddqService = new DDQService() ;
        //Create mock of ClientInfo and set necessary fields
        ClientInfo clientInfo = mock(ClientInfo.class);
        clientInfo.points = 4;
        Quotation q = ddqService.generateQuotation(clientInfo);
        Assert.assertNotNull(q);
    }
}