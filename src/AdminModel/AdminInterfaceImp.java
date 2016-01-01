package AdminModel;

import RMI.AdminInterface;
import RMI.Constant;
import RMI.RemoteMethods;

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

    private String ipAddress;
    private AdminInterface server;

    public AdminInterfaceImp() throws RemoteException {
        ipAddress = "localhost";
        try {


            Registry reg = LocateRegistry.getRegistry( System.setProperty("java.rmi.server.hostname", ipAddress), Constant.Adminport);
            server = (AdminInterface) reg.lookup(Constant.RMIAdminID);

        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList getBarangayData() {
        ArrayList list = null;
        try {
            list = server.getBarangayData();
            System.out.println("list size: " + list.size());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return list;
    }

}
