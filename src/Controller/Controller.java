package Controller;

import AdminModel.AdminInterfaceImp;
import ClientModel.Database;
import View.ClientWindow.ClientWindow;
import View.Login.LoginWindow;
import Family.Family;
import clientModel.StaffInfo;
import clientModel.StaffRegister;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

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
    private  String finalUsername = "";


    private static Controller controller = new Controller();

    private Controller() {

        try {
            clientDB = new Database();
            adminDB = new AdminInterfaceImp();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    // ==================== GLOBAL METHODS ==================//

    public boolean isServerConnected(){
       isServerConnected  = clientDB.connectToServer();
        return  isServerConnected;
    }

    public boolean  Login(String user, String Pass, String ipAddress) {
        StaffInfo staffInfo = clientDB.Login(user,Pass, ipAddress);
        this.staffInfo = staffInfo;
        finalUsername = staffInfo.getUsername();
        return staffInfo.isAccountExist();

    }


    // ====================== Encoder methods =================//
    public  boolean addFamilyInfo(Family family){
        return clientDB.addFamilyInfo(family);
    }

    public  StaffInfo getStaffInfo(){
    return staffInfo;
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
            clientDB.Logout(staffInfo.getAccountID(), staffInfo.getUsername()   );
    }
    public ArrayList getBarangayData(){
        return adminDB.getBarangayData();
    }

    public ArrayList searchedList (String name){
        return clientDB.searchedList(name);
    }
    public void setLoginToDisconnected(){
        LoginWindow.getInstantance().setLoginStageToDisconnected();
    }


    public int getPendingAccounts(){

        return clientDB.getPendingAccounts();
    }



    ////////////////////// BACKEND TO FRONT END ///////////////////
    public boolean updateStaffInfo(StaffInfo staffInfo) {
                this.staffInfo = staffInfo;
        return  clientDB.updateStaffInfo(staffInfo,finalUsername);
    }

    public static void showMessageBox(String message, Alert.AlertType alertType){
        ClientWindow.showMessageBox(message,alertType);

    }

    public  void showSearchedList(ArrayList data){
        ClientWindow.getInstance().showSearchedTable(data);
    }

}

