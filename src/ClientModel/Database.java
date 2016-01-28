package ClientModel;

import AdminModel.RequestAccounts;
import Controller.Controller;
import RMI.Constant;
import RMI.RemoteMethods;
import Remote.Method.FamilyModel.Family;
import View.AdminGUI.TableItemListener;
import clientModel.StaffInfo;
import clientModel.StaffRegister;
import global.Credentials;
import javafx.scene.control.Alert;
import utility.Utility;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Didoy on 8/24/2015.
 */
public class Database extends UnicastRemoteObject implements RemoteMethods, TableItemListener {

   private RemoteMethods server;
   private boolean bol;
   private String ipAddress = "localhost"; //Local IP address
   private  Registry reg;
   private Credentials credentials;

    // call back objects
   private  Registry myRegistry;
   private ClientInterfaceImp myClientInterface;

    public Database() throws RemoteException {

        RegisterServer();
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

                  bol = RegisterServer();

            }
        });

        t1.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

            if (connectToServer()){
                server.Logout(accountID, username);
                DemolishCallBack();
            }else{
                DemolishCallBack();
            }


        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addFamilyInfo(boolean instantSave, Family family)  {
        boolean bool;

        try {
            if (Controller.getInstance().isNotified()){
                bool =  server.addFamilyInfo(true, family);
                    if (bool){
                        Controller.getInstance().setNotified(false);
                    }
            }else{
                bool =  server.addFamilyInfo(instantSave, family);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
            bool = false;
        }
        return bool;
    }

    @Override
    public StaffInfo Login(String user, String pass, String ipAddress, int port, String remoteID)  {
    StaffInfo staffInfo = null;
        try {

            InetAddress rawIp  = InetAddress.getLocalHost();
            String strIp = rawIp.toString();
            int beginningIndex = strIp.indexOf("/");
            String ip = strIp.substring(beginningIndex + 1, strIp.length());

            // establish callback rmi after  login
             credentials = getCredentials(user, ip);

                if (credentials == null){
                    Utility.showMessageBox("Invalid username or password", Alert.AlertType.INFORMATION);
                }else {
                    staffInfo = server.Login(user,pass,ip, credentials.getRemotePort(), credentials.getRemoteID());
                    EstablishCallBack(credentials);
                }


        } catch (RemoteException e) {
            Controller.getInstance().setLoginToDisconnected();
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return staffInfo;
    }

    private boolean RegisterServer(){
        try {
            reg = LocateRegistry.getRegistry(System.setProperty("java.rmi.server.hostname",ipAddress), Constant.Remote_port);
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

        return bol;
    }


    private void EstablishCallBack(Credentials credentials){
        try {

            myClientInterface  = new ClientInterfaceImp();
            myRegistry = LocateRegistry.createRegistry(credentials.getRemotePort());
            myRegistry.bind(credentials.getRemoteID(), myClientInterface);

            System.out.println("EstablishCallBack: " + credentials.getRemoteID());

            System.out.println("Successfully created callback port: " + credentials.getRemotePort());
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private void DemolishCallBack(){
        try {
            System.out.println("DemolishCallBack: " + credentials.getRemoteID());
            UnicastRemoteObject.unexportObject(myRegistry, true);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



// methods from TableItemListener interface
    @Override
    public boolean Approve(RequestAccounts ra) {
        boolean x = false;

        try {
            x = server.Approve(ra);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return x;
    }

    @Override
    public boolean ApproveAdmin(RequestAccounts ra) {
        boolean isActivated = false;

            try {
                isActivated = server.ApproveAdmin(ra);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        return isActivated;
    }

    @Override
    public boolean Reject(RequestAccounts ra) {
        boolean isRejected = false;


        try {
            isRejected =  server.Reject(ra);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return isRejected;
    }

    @Override
    public String getMethodIdentifiers() {
        String methodName =  "";

        try {
            methodName =  server.getMethodIdentifiers();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return methodName;
    }

    @Override
    public Credentials getCredentials(String username, String ipAddress) throws RemoteException {

        Credentials credentials = server.getCredentials(username, ipAddress);

        return credentials;
    }



}
