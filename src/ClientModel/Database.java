package ClientModel;

import RMI.Constant;
import RMI.RemoteMethods;
import clientModel.StaffInfo;
import clientModel.StaffRegister;
import javafx.scene.control.Alert;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

/**
 * Created by Didoy on 8/24/2015.
 */
public class Database extends UnicastRemoteObject implements RemoteMethods {

    RemoteMethods server;
    Alert alertBox;
    public Database() throws RemoteException {

    }

    @Override
    public boolean checkDatabase() throws RemoteException, SQLException {
        connectToServer();
        return server.checkDatabase();
    }

    public boolean connectToServer(){
        boolean bol;
        try {

            Registry reg = LocateRegistry.getRegistry("Localhost",Constant.Remote_port);
            server = (RemoteMethods) reg.lookup(Constant.Remote_ID);
            bol = server.checkDatabase();

        } catch (RemoteException e) {
            System.out.println("Client:Database:connectToServer:RemoteException");
            bol = false;

        } catch (NotBoundException z) {
            System.out.println("Client:Database:connectToServer:NotBoundException");
            bol = false;
        } catch (SQLException s){
            System.out.println("Client:Database:connectToServer:SQLException");
            bol = false;
        }
        return bol;
    }


    // CLIENT BASIC METHODS

    @Override
    public boolean getAdminKeyCode(String keycode) {
        boolean bool = false;
        try {
                bool = server.getAdminKeyCode(keycode);
        }catch (RemoteException e){
            e.printStackTrace();
            System.out.println("Client:Database:getAdminkeyCode:RemoteException");
        }

        return bool;
    }

    @Override
    public boolean register(StaffRegister staffRegister){
        boolean isRegistered;
        try {
                isRegistered = server.register(staffRegister);

        }catch (RemoteException e){
            isRegistered = false;
            System.out.println("Client:Database:register:RemoteException");
        }
        return isRegistered;
    }

    @Override
    public boolean getUsername(String username)  {
        boolean bol = false;

        try {
            bol = server.getUsername(username);
        } catch (RemoteException e) {
            e.printStackTrace();
            bol = false;
        }
        return bol;
    }

    @Override
    public StaffInfo Login(String user, String pass)  {
    StaffInfo staffInfo = null;
        try {
               staffInfo = server.Login(user,pass);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    return staffInfo;
    }



}
