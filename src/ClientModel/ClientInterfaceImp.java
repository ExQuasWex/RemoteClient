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

        if (Controller.getInstance().showConfirmationMessage("We found Similar record of the person, Would You like to see it before proceeding?",
                Alert.AlertType.CONFIRMATION, familyList)){

        }else {
                // do nothing
        }

    }
}
