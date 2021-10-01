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
public class GPQServiceTest {
    private static Registry registry;
    @BeforeClass
    public static void setup() {
        QuotationService gpqService = new GPQService();
        try {
            registry = LocateRegistry.createRegistry(1099);
            System.out.println("TEST");
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(gpqService,0);
            registry.bind(Constants.GIRL_POWER_SERVICE, quotationService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }
    @Test
    public void connectionTest() throws Exception {
        QuotationService service = (QuotationService)
                registry.lookup(Constants.GIRL_POWER_SERVICE);
        assertNotNull(service);
    }

    @Test
    public void testGenerateQuotation(){
        //Initialise object of the girlpower service class
        GPQService gpqService = new GPQService() ;
        //Create mock of ClientInfo and set necessary fields
        ClientInfo clientInfo = mock(ClientInfo.class);
        clientInfo.gender = ClientInfo.FEMALE;
        Quotation q = gpqService.generateQuotation(clientInfo);
        Assert.assertNotNull(q);
    }
}