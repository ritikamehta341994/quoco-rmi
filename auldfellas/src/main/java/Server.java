import service.core.Constants;
import service.core.QuotationService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        QuotationService afqService = new AFQService();
        try {
            // Connect to the RMI Registry - creating the registry will be the
            // responsibility of the broker.
            Registry registry = null;
            registry = LocateRegistry.getRegistry(1099);

            // Create the Remote Object
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(afqService,0);
            // Register the object with the RMI Registry
            registry.bind(Constants.AULD_FELLAS_SERVICE, quotationService);

            System.out.println("AULDFELLAS Service");
            while (true) {Thread.sleep(1000); }
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }
}