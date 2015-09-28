package ClientModel;

import RMI.Constant;
import RMI.RemoteMethods;
import View.LoginWindow;
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

    RemoteMethods rm;
    Alert alertBox;
    public Database() throws RemoteException {

    }

    @Override
    public boolean checkDatabase() throws RemoteException, SQLException {
        connectToServer();
        return rm.checkDatabase();
    }




    private void connectionError(){
        alertBox = new Alert(Alert.AlertType.ERROR);
        alertBox.setHeaderText(null);
        alertBox.setTitle("Error information");
        alertBox.setContentText("We cant connect to server as of the moment");
        alertBox.show();
        LoginWindow.getInstantance().setLoginStageToDisconnected();
    }


    public boolean connectToServer(){
        boolean bol;
        try {

            Registry reg = LocateRegistry.getRegistry("Localhost",Constant.Remote_port);
            rm = (RemoteMethods) reg.lookup(Constant.Remote_ID);
            bol = rm.checkDatabase();

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
            if (!connectToServer()){
                connectionError();
            }else{
                bool = rm.getAdminKeyCode(keycode);
            }

        }catch (RemoteException e){
            e.printStackTrace();
            connectionError();
            System.out.println("Client:Database:getAdminkeyCode:RemoteException");
        }

        return bool;
    }

    @Override
    public boolean register(StaffRegister staffRegister){
        boolean isRegistered;
        try {
            if (!connectToServer()){
                isRegistered = false;
                connectionError();
            }else{
                rm.register(staffRegister);
                isRegistered = true;
            }

        }catch (RemoteException e){
            e.printStackTrace();
            connectionError();
            isRegistered = false;
            System.out.println("Client:Database:register:RemoteException");
        }

        return isRegistered;
    }

    @Override
    public boolean Login(String user, String pass) throws RemoteException {
        connectToServer();
        System.out.println(rm.Login(user,pass));
        return   rm.Login(user,pass);
    }



}
