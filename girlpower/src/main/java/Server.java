import service.core.Constants;
import service.core.QuotationService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        QuotationService gpqService = new GPQService();
        try {
            // Connect to the RMI Registry - creating the registry will be the
            // responsibility of the broker.
            Registry registry = null;
            registry = LocateRegistry.getRegistry(1099);
            // Create the Remote Object
            QuotationService gpqQuotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(gpqService,0);
            // Register the object with the RMI Registry
            registry.bind(Constants.GIRL_POWER_SERVICE, gpqQuotationService);

            System.out.println("GIRLPOWER Service");
            while (true) {Thread.sleep(1000); }
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }
}