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
        System.out.println("sendData called from client interface");

        try {
            InputStream fileData = RemoteInputStreamClient.wrap(remoteFileData);

            writeToFile(fileData,  filename);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeToFile(InputStream stream, String filename) throws IOException {
        FileOutputStream output = null;

        try {
            String path = ClientPreference.getDbPath();

            File file = new File(path+"\\"+filename);
            output = new FileOutputStream(file);
            int chunk = 4096;
            byte [] result = new byte[chunk];

            int readBytes = 0;
            do {
                readBytes = stream.read(result);
                if (readBytes > 0)
                    output.write(result, 0, readBytes);
                System.out.println("write");
            } while(readBytes != -1);

            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(output != null)
                output.close();
        }
    }
}
