package Controller;

import AdminModel.AdminInterfaceImp;
import AdminModel.Enum.AccountApproveStatus;
import AdminModel.Enum.AccountStatus;
import AdminModel.Enum.ReportCategoryMethod;
import AdminModel.Params;

import AdminModel.Report.Parent.ResponseCompareOverview;
import AdminModel.Report.Parent.ResponseOverviewReport;
import AdminModel.Report.Parent.ResponseSpecific;
import AdminModel.Report.Parent.ResponseSpecificOverView;
import AdminModel.RequestAccounts;
import ClientModel.Database;
import Remote.Method.FamilyModel.Family;
import View.AdminGUI.Listeners.TableAccountListener;
import ToolKit.LoadBar;
import clientModel.ClientEntries;
import clientModel.StaffInfo;
import clientModel.StaffRegister;
import global.SecretDetails;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import utility.Utility;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Didoy on 8/25/2015.
 */
public class Controller implements TableAccountListener {

    Database clientDB ;
    private  StaffInfo staffInfo;
    private boolean isServerConnected;
    private AdminInterfaceImp adminDB;
    private  String finalUsername = "";

    private ControllerListener controllerListener;
    public static  boolean isNotified = false;
    private static Controller controller = new Controller();

    public static ArrayList clientEntryList = new ArrayList();

    private Controller()  {

        try {
            clientDB = new Database();
            adminDB = new AdminInterfaceImp();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public  static  Controller getInstance(){
        return controller;
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

    public  StaffInfo getStaffInfo(){
        return staffInfo;
    }

    public  boolean register(StaffRegister staffRegister){
        return clientDB.register(staffRegister);
    }

    public boolean getUsername(String username){
        return clientDB.getUsername(username);
    }

    public  void Logout(){
        clientDB.Logout(staffInfo.getAccountID(), staffInfo.getUsername()   );
    }

    public ArrayList searchedList (String name){
        return clientDB.searchedList(name);
    }


    // ====================== CLIENT  METHODS =================//
    /*
        if instant save is true the server automatically save the
        family information and would not check it
     */
    public  boolean addFamilyInfo(boolean instantSave, Family family){
        return clientDB.addFamilyInfo(instantSave ,family);
    }

    public void UpdateFamilyInformation(Family family){

            boolean isEdited =  clientDB.UpdateFamilyInformation(family);

                if (isEdited){
                    Utility.showMessageBox("Successfully Edit Information", Alert.AlertType.INFORMATION);
                }else {
                    Utility.showMessageBox("Unable to Edit Information, please try again later", Alert.AlertType.INFORMATION);
                }
    }

    /*
       Critical method put the thread
       into TCP java thread
    */
    public void notifyClient(ArrayList familyList){
        isNotified = true;
        controllerListener.notifyClient(familyList);
    }

    public void getClientEntries(){
        clientDB.getClientEntries(staffInfo.getAccountID());
    }
    public void setEntrySize(int size){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                LoadBar.createProgressbar(size);
            }
        });
    }

    public void addClientEntry(ClientEntries clientEntries){
        clientEntryList.add(clientEntries);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                LoadBar.updateValue();
            }
        });
    }

    public ArrayList getClientEntryList(){
        return clientEntryList;
    }


    // ------------------------ ADMIN METHODS ----------------------//

    public ArrayList getBarangayData(String year){
        return adminDB.getBarangayData(year);
    }

    public int getPendingAccounts(){

        return clientDB.getPendingAccounts();
    }
    public ArrayList getRequestAccounts(){

        return clientDB.getRequestAccounts();
    }

    public boolean addHistoryToFamily(Family family){
        return  adminDB.addHistoryToFamily(family);
    }

    // ======================BACK TO FRONT END=================//

    public boolean updateStaffInfo(StaffInfo staffInfo) {
                this.staffInfo = staffInfo;
        return  clientDB.updateStaffInfo(staffInfo,finalUsername);
    }


    public String getMethodIdenifier(){
        return clientDB.getMethodIdentifiers();
    }


    // ==================== ADMIN REPORTS ===================//

    public ResponseOverviewReport getOverViewData(Params params, String type){

        return adminDB.getOverViewData( params,  type);
    }
    public ResponseCompareOverview getCompareOverViewData(Params params, String type){
        return adminDB.getCompareOverViewData( params,  type);
    }

    public ResponseCompareOverview getCompareSpecificData(Params params, String type){
        return adminDB.getCompareSpecificData( params,  type);
    }

    public ResponseSpecificOverView getSpecificOverViewData(Params params, String type){

        return adminDB.getSpecificOverViewData( params,  type);
    }

    public ResponseSpecific getSpecific(Params params, String type){

        return adminDB.getSpecific(params, type);
    }
    public ArrayList getYears(){
        return adminDB.getYears();
    }


    public ArrayList getFamilyBarangay(Params params, ReportCategoryMethod method){
        ArrayList list = new ArrayList();
        list = adminDB.getFamilyBarangay(params, method);
        return list;

    }
    @Override
    public boolean updateAccountStatus(int id, AccountStatus status) {
        return adminDB.updateAccountStatus(id, status);
    }

    @Override
    public boolean approveAccount(int id, AccountApproveStatus status) {
        return adminDB.approveAccount(id, status);
    }

    public ArrayList getActiveAccounts( ){
        return adminDB.getActiveAccounts();
    }

    public boolean isTheAccountOnline(String  username) {
        return  adminDB.isTheAccountOnline(username);
    }


    public File getBackUp(){
         return adminDB.getBackUp();
    }

    public void addControllerListener(ControllerListener controllerListener){
        this.controllerListener = controllerListener;
    }


}

