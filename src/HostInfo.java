import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HostInfo extends Remote {

    String getHostInfo() throws RemoteException;

}