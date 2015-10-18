package ClientModel;

import Controller.Controller;
import RMI.Constant;
import RMI.RemoteMethods;
import clientModel.StaffInfo;
import clientModel.StaffRegister;
import javafx.scene.control.Alert;

import java.rmi.ConnectException;
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

   private RemoteMethods server;
   private Alert alertBox;
   private boolean bol;
   private String ipAddress = "localhost"; //Local IP address
   private  Registry reg;


    public Database() throws RemoteException {

        try {
             reg = LocateRegistry.getRegistry(System.setProperty("java.rmi.server.hostname",ipAddress),Constant.Remote_port);
            //Registry reg = LocateRegistry.getRegistry("localhost",Constant.Remote_port);
             server = (RemoteMethods) reg.lookup(Constant.Remote_ID);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (ConnectException ce){
            ce.printStackTrace();

        }

    }

    @Override
    public boolean checkDatabase() throws RemoteException, SQLException {
        connectToServer();
        return server.checkDatabase();
    }

    public boolean connectToServer(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    reg = LocateRegistry.getRegistry(System.setProperty("java.rmi.server.hostname",ipAddress),Constant.Remote_port);
                    //Registry reg = LocateRegistry.getRegistry("localhost",Constant.Remote_port);
                    server = (RemoteMethods) reg.lookup(Constant.Remote_ID);

                    bol = server.checkDatabase();
                    System.out.println(bol);

                } catch (RemoteException e) {
                    System.out.println("Client:Database:connectToServer:RemoteException");
                    bol = false;

                } catch (SQLException s){
                    System.out.println("Client:Database:connectToServer:SQLException");
                    bol = false;
                } catch (NotBoundException e) {
                    e.printStackTrace();
                }

            }
        });


        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.start();



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
    public void Logout(int accountID)  {
        try {
            server.Logout(accountID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public StaffInfo Login(String user, String pass)  {
    StaffInfo staffInfo = null;
        try {
            staffInfo = server.Login(user,pass);
        } catch (RemoteException e) {
            Controller.getInstance().setLoginToDisconnected();
            e.printStackTrace();
        }
    return staffInfo;
    }




}
