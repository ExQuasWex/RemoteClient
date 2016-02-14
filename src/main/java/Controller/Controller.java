package Controller;

import AdminModel.AdminInterfaceImp;
import AdminModel.Params;
import AdminModel.Report.Children.Model.ResponseCompareOverview;
import AdminModel.Report.Parent.Model.ResponseOverviewReport;
import AdminModel.RequestAccounts;
import ClientModel.Database;
import Remote.Method.FamilyModel.Family;
import View.AdminGUI.Listeners.TableItemListener;
import View.ClientWindow.ClientWindow;
import clientModel.StaffInfo;
import clientModel.StaffRegister;
import global.SecretDetails;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Didoy on 8/25/2015.
 */
public class Controller implements TableItemListener {

    Database clientDB ;
    private  StaffInfo staffInfo;
    private boolean isServerConnected;
    private AdminInterfaceImp adminDB;
    private  String finalUsername = "";


    private boolean isNotified;

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean isNotified) {
        this.isNotified = isNotified;
    }

    private static Controller controller = new Controller();

    private Controller()  {

        try {
            clientDB = new Database();
            adminDB = new AdminInterfaceImp();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    // ==================== GLOBAL METHODS ==================//

    public boolean isServerConnected(  )   {

                isServerConnected  = clientDB.connectToServer();
                boolean isAdminServerConnected = adminDB.connectToServer();

                if (isAdminServerConnected && isServerConnected){
                    isServerConnected =  true;
                }else {
                    isServerConnected =  false;
                }

        return  isServerConnected;
    }

    public boolean  Login(String user, String Pass, String ipAddress) {
        StaffInfo staffInfo = clientDB.Login(user,Pass, ipAddress, 0, "");
        this.staffInfo = staffInfo;
        finalUsername = staffInfo.getUsername();
        return staffInfo.isAccountExist();

    }

    public SecretDetails getSecurityQuestion(String hint1){
        return  clientDB.getSecurityQuestion(hint1);
    }


    // ====================== Encoder methods =================//
    /*
        if instant save is true the server automatically save the
        family information and would not check it
     */
    public  boolean addFamilyInfo(boolean instantSave, Family family){
        return clientDB.addFamilyInfo(instantSave ,family);
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
        ArrayList list = adminDB.getBarangayData();
            if (list.isEmpty()){
                System.out.println("Barangay list is empty detected fromm Contoller ");
            }
        return adminDB.getBarangayData();
    }

    public ArrayList searchedList (String name){
        return clientDB.searchedList(name);
    }


    public int getPendingAccounts(){

        return clientDB.getPendingAccounts();
    }
    public ArrayList getRequestAccounts(){

        return clientDB.getRequestAccounts();
    }


    // ======================BACK TO FRONT END=================//


    public boolean updateStaffInfo(StaffInfo staffInfo) {
                this.staffInfo = staffInfo;
        return  clientDB.updateStaffInfo(staffInfo,finalUsername);
    }


    public String getMethodIdenifier(){
        return clientDB.getMethodIdentifiers();
    }

    /*
        Critical method put the thread
        into TCP java thread
     */
    public void notifyClient(ArrayList familyList){
       ClientWindow.notifyClient(familyList);
    }

    @Override
    public boolean Approve(RequestAccounts ra) {
        return clientDB.Approve(ra);
    }

    @Override
    public boolean ApproveAdmin(RequestAccounts ra) {
        return clientDB.ApproveAdmin(ra);
    }

    @Override
    public boolean Reject(RequestAccounts ra) {
        return clientDB.Reject(ra);
    }



    // ==================== ADMIN REPORTS ===================//

    public ResponseOverviewReport getOverViewData(Params params, String type){

        return adminDB.getOverViewData( params,  type);
    }
    public ResponseCompareOverview getCompareOverViewData(Params params, String type){
        return adminDB.getCompareOverViewData( params,  type);
    }

    public ArrayList getCompareSpecificData(Params params, String type){

        return adminDB.getCompareSpecificData( params,  type);
    }

    public ArrayList getSpecificOverViewData(Params params, String type){

        return adminDB.getSpecificOverViewData( params,  type);
    }

    public ArrayList getSpecific(Params params, String type){

        return adminDB.getSpecific(params, type);
    }
    public ArrayList getYears(){
        return adminDB.getYears();
    }


    public ArrayList getFamilyBarangay(Params params){
        ArrayList list = new ArrayList();
        list = adminDB.getFamilyBarangay(params);
        // Utility.showMessageBox("Error Unable to get Data", Alert.AlertType.INFORMATION);

        return list;

    }

    public ArrayList getActiveAccounts( ){
        return adminDB.getActiveAccounts();
    }

}

