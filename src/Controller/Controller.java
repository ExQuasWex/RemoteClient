package Controller;

import ClientModel.Database;
import clientModel.StaffRegister;

import java.rmi.RemoteException;

/**
 * Created by Didoy on 8/25/2015.
 */
public class Controller {

    Database clientDB ;

    public Controller() {

        try {
            clientDB = new Database();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean isServerConnected(){           // check the server if is UP
    return  clientDB.connectToServer();
    }

    public boolean  Login(String user, String Pass) {
        return  clientDB.Login(user,Pass);
    }
    public boolean  getAdminKeyCode(String keycode) {
        return  clientDB.getAdminKeyCode(keycode);
    }

    public  boolean register(StaffRegister staffRegister){
           return clientDB.register(staffRegister);
    }

    public boolean getUsername(String username){
        return clientDB.getUsername(username);
    }
}
