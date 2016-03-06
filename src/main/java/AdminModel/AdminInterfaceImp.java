package AdminModel;

import AdminModel.Enum.AccountApproveStatus;
import AdminModel.Enum.AccountStatus;
import AdminModel.Enum.ReportCategoryMethod;
import AdminModel.Report.Parent.ResponseCompareOverview;
import AdminModel.Report.Parent.ResponseOverviewReport;
import AdminModel.Report.Parent.ResponseSpecific;
import AdminModel.Report.Parent.ResponseSpecificOverView;
import RMI.AdminInterface;
import RMI.Constant;
import Remote.Method.FamilyModel.Family;
import utility.TimedRMIclientSocketFactory;
import utility.Utility;

import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by Didoy on 11/25/2015.
 */
public class AdminInterfaceImp extends UnicastRemoteObject implements AdminInterface {

    private String ipAddress = "";
    private static AdminInterface server = null;
    private boolean isConnected = false;

    private TimedRMIclientSocketFactory csf;

    public AdminInterfaceImp() throws RemoteException {
        ipAddress = Utility.getPreference();
        csf = new TimedRMIclientSocketFactory(2000);
        connectToServer();

    }

    public boolean connectToServer(){
        ipAddress = Utility.getPreference();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Registry reg = LocateRegistry.getRegistry(System.setProperty("java.rmi.server.hostname", ipAddress), Constant.Adminport, csf);
                    server = (AdminInterface) reg.lookup(Constant.RMIAdminID);
                    isConnected = server.checkConnectDB();

                } catch (RemoteException e) {
                    System.out.println("Client:Database:connectToServer:RemoteException");
                    isConnected = false;

                } catch (NotBoundException e) {
                    e.printStackTrace();
                }

            }
        });

        thread.start();



        return isConnected;
    }

    @Override
    public ArrayList getBarangayData(String year) {
        ArrayList list = new ArrayList();
        try {
            list = server.getBarangayData(year);
            System.out.println("list size: " + list.size());
        } catch (RemoteException e) {
            System.out.println("remote exceptopn Admin interfaceimp");
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean checkConnectDB() throws RemoteException {
        return server.checkConnectDB();
    }

    // ==================== ADMIN REPORTS ===================//
    @Override
    public ResponseOverviewReport getOverViewData(Params params, String type){
        ResponseOverviewReport overViewReportObject = null;
        try {
            overViewReportObject =  server.getOverViewData(params, type);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return  overViewReportObject;
    }
    @Override
    public ResponseCompareOverview getCompareOverViewData(Params params, String type){
        ResponseCompareOverview compareOverview = null;

        try {
            compareOverview =  server.getCompareOverViewData(params, type);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return  compareOverview;
    }
    @Override
    public ResponseCompareOverview getCompareSpecificData(Params params, String type){
        ResponseCompareOverview  responseCompareOverview = null;

        try {
            responseCompareOverview =  server.getCompareSpecificData(params, type);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return  responseCompareOverview;
    }
    @Override
    public ResponseSpecificOverView getSpecificOverViewData(Params params, String type){
        ResponseSpecificOverView responseSpecificOverView = null;

        try {
            responseSpecificOverView =  server.getSpecificOverViewData(params, type);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return  responseSpecificOverView;
    }

    @Override
    public ResponseSpecific getSpecific(Params params, String type){
        ResponseSpecific responseSpecific = null;
        try {
            responseSpecific =  server.getSpecific(params, type);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return  responseSpecific;
    }

    @Override
    public ArrayList getYears()  {
        ArrayList yearlist = new ArrayList();
        try {
            yearlist = server.getYears();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return  yearlist;
    }

    @Override
    public ArrayList getFamilyBarangay(Params params, ReportCategoryMethod method)   {
        ArrayList list = new ArrayList();

        try {
             list = server.getFamilyBarangay(params, method);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList getActiveAccounts( )   {
        ArrayList list = new ArrayList();

        try {
            list = server.getActiveAccounts();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean addHistoryToFamily(Family family) {
        boolean isAdded = false ;

        try {
            isAdded = server.addHistoryToFamily(family);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return isAdded;
    }

    @Override
    public File getBackUp()  {
        File file = null;
        try {
            file =  server.getBackUp();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public boolean updateAccountStatus(int id, AccountStatus status)  {
        boolean isUpdated = false;
        try {
            isUpdated =  server.updateAccountStatus(id, status);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    @Override
    public boolean approveAccount(int id, AccountApproveStatus status)   {
        boolean isUpdated = false;
        try {
            isUpdated =  server.approveAccount(id, status);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }


}
