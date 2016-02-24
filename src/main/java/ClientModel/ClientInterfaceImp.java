package ClientModel;

import Controller.Controller;
import RMI.ClientInterface;
import clientModel.ClientEntries;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

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

    @Override
    public void notifyClient(ArrayList familyList) throws RemoteException {
        Controller.getInstance().notifyClient(familyList);
    }

    @Override
    public void setClientEntriesMaxSize(int size) throws RemoteException {
        Controller.getInstance().setEntrySize(size);
    }

    @Override
    public void addClientEntry(ClientEntries clientEntries) throws RemoteException {
        Controller.getInstance().addClientEntry(clientEntries);
    }
}
