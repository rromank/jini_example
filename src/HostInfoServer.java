import net.jini.core.lease.Lease;
import net.jini.core.lookup.ServiceID;
import net.jini.core.lookup.ServiceItem;
import net.jini.core.lookup.ServiceRegistrar;
import net.jini.core.lookup.ServiceRegistration;
import net.jini.discovery.DiscoveryEvent;
import net.jini.discovery.DiscoveryListener;
import net.jini.discovery.LookupDiscovery;
import net.jini.lease.LeaseListener;
import net.jini.lease.LeaseRenewalEvent;
import net.jini.lease.LeaseRenewalManager;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HostInfoServer implements DiscoveryListener, LeaseListener {

    protected LeaseRenewalManager leaseManager = new LeaseRenewalManager();
    protected ServiceID serviceID = null;

    public HostInfoServer() throws Exception {
        System.setSecurityManager(new SecurityManager());
        LookupDiscovery discover = new LookupDiscovery(LookupDiscovery.ALL_GROUPS);
        discover.addDiscoveryListener(this);
    }

    @Override
    public void discovered(DiscoveryEvent discoveryEvent) {
        ServiceRegistrar[] registrars = discoveryEvent.getRegistrars();

        for (int n = 0; n < registrars.length; n++) {
            ServiceRegistrar registrar = registrars[n];
            System.err.println("Lookup server ip address: " + Utils.getIpAddress(registrar.toString()));

            ServiceItem item = new ServiceItem(serviceID, createProxy(), null);

            ServiceRegistration reg = null;
            try {
                reg = registrar.register(item, Lease.FOREVER);
            } catch (RemoteException e) {
                System.out.println("Register exception " + e.toString());
            }

            System.out.println("Service registered with id " + reg.getServiceID());

            // set lease renewal in place
            leaseManager.renewUntil(reg.getLease(), Lease.FOREVER, this);
        }
    }

    private Remote createProxy() {
        try {
            return UnicastRemoteObject.exportObject(new HostInfoProxy(), 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void discarded(DiscoveryEvent discoveryEvent) {}

    @Override
    public void notify(LeaseRenewalEvent leaseRenewalEvent) {
        System.out.println("Lease experied " + leaseRenewalEvent.toString());
    }

    public static void main(String[] args) throws Exception {
        HostInfoServer s = new HostInfoServer();
        Object keepAlive = new Object();
        synchronized (keepAlive) {
            keepAlive.wait();
        }
    }

}