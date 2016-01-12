package AdminModel;

import RMI.AdminInterface;
import RMI.Constant;
import RMI.RemoteMethods;

import java.rmi.AccessException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by Didoy on 11/25/2015.
 */
public class AdminInterfaceImp extends UnicastRemoteObject implements AdminInterface {

    private String ipAddress = "localhost";
    private AdminInterface server;

    public AdminInterfaceImp() throws RemoteException {

        RegisterServer();

    }

    @Override
    public ArrayList getBarangayData() {
        ArrayList list = new ArrayList();
        try {
            list = server.getBarangayData();
            System.out.println("list size: " + list.size());
        } catch (RemoteException e) {
            System.out.println("remote exceptopn Admin interfaceimp");
            e.printStackTrace();
        }
        return list;
    }


    private void RegisterServer() {
        try {
            //used only for localhost
            //reg = LocateRegistry.getRegistry("localhost");
            //Registry reg = LocateRegistry.getRegistry("localhost",Constant.Remote_port);

            Registry reg = LocateRegistry.getRegistry(System.setProperty("java.rmi.server.hostname", ipAddress), Constant.Adminport);
            server = (AdminInterface) reg.lookup(Constant.RMIAdminID);

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (ConnectException ce) {
            ce.printStackTrace();

        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
