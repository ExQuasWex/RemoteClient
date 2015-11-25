package Controller;

import AdminModel.AdminInterfaceImp;
import ClientModel.Database;
import View.Login.LoginWindow;
import Family.Family;
import clientModel.StaffInfo;
import clientModel.StaffRegister;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Didoy on 8/25/2015.
 */
public class Controller {

    Database clientDB ;
    private  StaffInfo staffInfo;
    private boolean isServerConnected;
    private AdminInterfaceImp adminDB;


    private static Controller controller = new Controller();
    private Controller() {

        try {
            clientDB = new Database();
            adminDB = new AdminInterfaceImp();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean isServerConnected(){           // check the server if is UP
       isServerConnected  = clientDB.connectToServer();
        return  isServerConnected;
    }

    public boolean  Login(String user, String Pass, String ipAddress) {
        StaffInfo staffInfo = clientDB.Login(user,Pass, ipAddress);
        this.staffInfo = staffInfo;
        return staffInfo.isAccountExist();

    }
    public  boolean addFamilyInfo(Family family){
        return clientDB.addFamilyInfo(family);
    }

    public  StaffInfo getStaffInfo(){
    return staffInfo;
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
    public  static  Controller getInstance(){
        return controller;
    }

    public  void Logout(){
            clientDB.Logout(staffInfo.getAccountID(), staffInfo.getUsername());
    }

    // clientdb to Login
    public void setLoginToDisconnected(){
        LoginWindow.getInstantance().setLoginStageToDisconnected();
    }

    public ArrayList getBarangayData(){
        return adminDB.getBarangayData();
    }
}
