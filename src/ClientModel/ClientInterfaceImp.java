package ClientModel;

import RMI.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by reiner on 11/23/2015.
 */
public class ClientInterfaceImp extends UnicastRemoteObject implements ClientInterface {


    protected ClientInterfaceImp() throws RemoteException {
        super();

    }


    @Override
    public boolean imAlive() {
        return true;
    }
}
