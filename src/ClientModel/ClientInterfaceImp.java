package ClientModel;

import RMI.ClientInterface;
import RMI.Constant;
import RMI.RemoteMethods;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by reiner on 11/23/2015.
 */
public class ClientInterfaceImp extends UnicastRemoteObject implements ClientInterface {

    private RemoteMethods server;

    protected ClientInterfaceImp() throws RemoteException {
        super();

    }


    @Override
    public boolean imAlive() {
        return true;
    }
}
