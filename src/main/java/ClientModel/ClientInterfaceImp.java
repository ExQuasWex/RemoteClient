package ClientModel;

import ClientPreference.ClientPreference;
import Controller.Controller;
import RMI.ClientInterface;
import clientModel.ClientEntries;
import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import javafx.scene.control.Alert;
import org.apache.commons.io.IOUtils;
import utility.Utility;

import java.io.*;
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

    @Override
    public void sendData(String filename, RemoteInputStream remoteFileData) throws RemoteException {

        try {
            InputStream fileData = RemoteInputStreamClient.wrap(remoteFileData);

            String path = ClientPreference.getDbPath();
            File file = new File(path + "\\" + filename);
            OutputStream outputStream = new FileOutputStream(file);
            IOUtils.copy(fileData, outputStream);
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
