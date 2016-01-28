package AdminModel;


import AdminModel.Report.Children.Model.ResponseCompareOverview;
import AdminModel.Report.Parent.Model.ResponseOverviewReport;
import RMI.AdminInterface;
import RMI.Constant;

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

    private String ipAddress = "localhost";
    private static AdminInterface server = null;
    private boolean isConnected = false;

    public AdminInterfaceImp() throws RemoteException {

        connectToServer();

    }

    public boolean connectToServer(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Registry reg = LocateRegistry.getRegistry(System.setProperty("java.rmi.server.hostname", ipAddress), Constant.Adminport);
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

        t1.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isConnected;
    }

    @Override
    public ArrayList getBarangayData() {
        ArrayList list = new ArrayList();
        try {
            list = server.getBarangayData();
            System.out.println("list size: " + list.size());
        } catch (RemoteException e) {
            System.out.println("remote exceptopn Admin interfaceimp");
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean checkConnectDB() throws RemoteException {
        return false;
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
    public ArrayList getCompareSpecificData(Params params, String type){
        ArrayList list = new ArrayList();

        try {
            list =  server.getCompareSpecificData(params, type);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return  list;
    }
    @Override
    public ArrayList getSpecificOverViewData(Params params, String type){
        ArrayList list = new ArrayList();

        try {
            list =  server.getSpecificOverViewData(params, type);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return  list;
    }
    @Override
    public ArrayList getSpecific(Params params, String type){
        ArrayList list = new ArrayList();

        try {
            list =  server.getSpecific(params, type);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return  list;
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

}
