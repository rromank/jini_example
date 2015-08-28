import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

public class HostInfoProxy implements HostInfo, Serializable {

    public String getHostInfo() throws RemoteException {
        System.out.println("Host info reclaimed");
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (java.net.UnknownHostException uhe) {
            hostName = "<unknown>";
        }
        Date time = Calendar.getInstance().getTime();

        return " - Name & Time: " + hostName + " on " + time;
    }

}