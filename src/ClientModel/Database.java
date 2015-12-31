package ClientModel;

import Controller.Controller;
import RMI.ClientInterface;
import RMI.Constant;
import RMI.RemoteMethods;
import Family.Family;
import clientModel.StaffInfo;
import clientModel.StaffRegister;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Didoy on 8/24/2015.
 */
public class Database extends UnicastRemoteObject implements RemoteMethods {

   private RemoteMethods server;
   private Alert alertBox;
   private boolean bol;
   private String ipAddress = ""; //Local IP address
   private  Registry reg;


    public Database() throws RemoteException {

       ClientCallBackInit();


        try {
            //used only for localhost
            //reg = LocateRegistry.getRegistry("localhost");
            //Registry reg = LocateRegistry.getRegistry("localhost",Constant.Remote_port);

             reg = LocateRegistry.getRegistry(System.setProperty("java.rmi.server.hostname",ipAddress),Constant.Remote_port);
             server = (RemoteMethods) reg.lookup(Constant.Remote_ID);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (ConnectException ce){
            ce.printStackTrace();

        }

    }

    @Override
    public boolean updateStaffInfo(StaffInfo staffInfo, String oldUsername)  {
        boolean isUpdated = false;
        try {
            isUpdated = server.updateStaffInfo(staffInfo, oldUsername);
        } catch (RemoteException e) {
            e.printStackTrace();
            isUpdated = false;
        }
        return  isUpdated;
    }

    @Override
    public ArrayList searchedList(String name)  {
        ArrayList list = new ArrayList();
        try {

            list =  server.searchedList(name);
        } catch (RemoteException e) {
            e.printStackTrace();
            list.clear();
        }
        return list;
    }

    @Override
    public int getPendingAccounts(){
        int pendingAccounts = 0;
        try {
            pendingAccounts = server.getPendingAccounts();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return pendingAccounts;
    }

    @Override
    public ArrayList getRequestAccounts()  {
        ArrayList requestList = null;

        try {
            requestList = server.getRequestAccounts();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return requestList;
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
    public void Logout(int accountID, String username)  {
        try {
            server.Logout(accountID, username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addFamilyInfo(Family family)  {
        boolean bool;

        try {
            bool =  server.addFamilyInfo(family);
        } catch (RemoteException e) {
            e.printStackTrace();
            bool = false;
        }
        return bool;
    }

    @Override
    public StaffInfo Login(String user, String pass,String ipAddress)  {
    StaffInfo staffInfo = null;
        try {
            InetAddress rawIp  = InetAddress.getLocalHost();
            String strIp = rawIp.toString();
            int beginningIndex = strIp.indexOf("/");
            String ip = strIp.substring(beginningIndex + 1, strIp.length());


            staffInfo = server.Login(user,pass,ip);

        } catch (RemoteException e) {
            Controller.getInstance().setLoginToDisconnected();
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return staffInfo;
    }


    private void ClientCallBackInit(){
        try {
            ClientInterfaceImp clientInterfaceImp  = new ClientInterfaceImp();
            Registry reg = LocateRegistry.createRegistry(Constant.ClientPort);
            reg.bind(Constant.Remote_ID,clientInterfaceImp);



        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }



}
