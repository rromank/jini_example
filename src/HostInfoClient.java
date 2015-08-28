import net.jini.core.discovery.LookupLocator;
import net.jini.core.lookup.ServiceRegistrar;
import net.jini.core.lookup.ServiceTemplate;

public class HostInfoClient {

    public HostInfoClient() throws Exception {
        System.setSecurityManager(new SecurityManager());
        LookupLocator lookup = new LookupLocator("jini://localhost");
        ServiceRegistrar registrar = lookup.getRegistrar();

        HostInfo hostInfo = (HostInfo) registrar.lookup(new ServiceTemplate(null, new Class[] {HostInfo.class}, null));
        System.out.println(hostInfo.getHostInfo());

        System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        new HostInfoClient();
    }

}