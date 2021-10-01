import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import service.core.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class LocalBrokerServiceTest {
    private static Registry registry;
    private static ClientInfo clientInfo;
    @BeforeClass
    public static void setup() {
        BrokerService brService = new LocalBrokerService();
        try {
            registry = LocateRegistry.createRegistry(1099);
            BrokerService brokerService = (BrokerService)
                    UnicastRemoteObject.exportObject(brService,0);
            registry.bind(Constants.BROKER_SERVICE, brokerService);
            //Create mock of ClientInfo
            clientInfo = mock(ClientInfo.class);
            clientInfo.gender = ClientInfo.MALE;
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    @Test
    public void connectionTest() throws Exception {
        BrokerService brokerService = (BrokerService)
                registry.lookup(Constants.BROKER_SERVICE);
        assertNotNull(brokerService);
    }

    /*
    Test case : When no quotation services are registered, empty quotation list is returned
     */
    @Test
    public void testGetQuotationsWhenNoQuotationServiceRegistered() throws RemoteException, NotBoundException {
        LocalBrokerService localBrokerService = new LocalBrokerService();
        List<Quotation> quotationList = new LinkedList<Quotation>();
        //Remove any service binding in case it is added if this test case runs after the testGetQuotationsWhenQuotationServiceRegistered test
        for(String name : registry.list()){
            if(name.startsWith("qs-")){
                registry.unbind(name);
            }
        }
        quotationList = localBrokerService.getQuotations(clientInfo);

        Assert.assertEquals(0,quotationList.size());
    }

    /*
    Test case : When a quotation services are registered , quotations are returned from that registered services
     */
    @Test
    public void testGetQuotationsWhenQuotationServiceRegistered() throws RemoteException, AlreadyBoundException {
        LocalBrokerService localBrokerService = new LocalBrokerService();
        List<Quotation> quotationList;
        QuotationService afqService = new AFQService();
        QuotationService quotationService = (QuotationService)
                UnicastRemoteObject.exportObject(afqService,0);
        registry.bind(Constants.AULD_FELLAS_SERVICE, quotationService);
        QuotationService gpService = new GPQService();
        QuotationService gpQuotationService = (QuotationService)
                UnicastRemoteObject.exportObject(gpService,0);
        registry.bind(Constants.GIRL_POWER_SERVICE, gpQuotationService);

        quotationList = localBrokerService.getQuotations(clientInfo);

        Assert.assertNotEquals(0,quotationList.size());
    }
}