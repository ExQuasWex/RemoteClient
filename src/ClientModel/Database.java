package ClientModel;

import RMI.Constant;
import RMI.RemoteMethods;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Didoy on 8/24/2015.
 */
public class Database extends UnicastRemoteObject implements RemoteMethods {

    RemoteMethods rm;

    public Database() throws RemoteException {


    }

    @Override
    public boolean checkDatabase() throws RemoteException {
        connectToServer();
        return rm.checkDatabase();
    }

    @Override
    public boolean Login(String user, String pass) throws RemoteException {
        connectToServer();
        System.out.println(rm.Login(user,pass));
        return   rm.Login(user,pass);
    }

    public boolean connectToServer(){
        boolean bol;
        try {

            Registry reg = LocateRegistry.getRegistry("Localhost",Constant.Remote_port);
            rm = (RemoteMethods) reg.lookup(Constant.Remote_ID);
            bol = rm.checkDatabase();
                        if (bol){
                            return bol;
                        }else if (!bol){
                            System.out.println("down");
                            return bol;
                        }

        } catch (RemoteException e) {
            System.out.println("cant connect remotely");
            e.printStackTrace();
            return false;
        } catch (NotBoundException z) {
            z.printStackTrace();
            return false;
        }
        return bol;
    }


}
