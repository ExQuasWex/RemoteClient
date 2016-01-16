package ClientModel;

import Controller.Controller;
import RMI.ClientInterface;
import javafx.scene.control.Alert;

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

        Controller.getInstance().notifyClient(familyList);;
        System.out.println("passed to contoller");
    }
}
