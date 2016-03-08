package View.AdminGUI;

import AdminModel.Enum.AccountApproveStatus;
import AdminModel.Enum.AccountStatus;
import AdminModel.Enum.ReportCategoryMethod;
import AdminModel.Params;
import AdminModel.RequestAccounts;
import Controller.Controller;
import Remote.Method.FamilyModel.Family;
import Remote.Method.FamilyModel.FamilyHistory;
import Remote.Method.FamilyModel.FamilyInfo;
import View.AdminGUI.Home.HistoryTable;
import View.AdminGUI.Home.HomePane;
import View.AdminGUI.Home.Listeners.HomePaneListener;
import View.AdminGUI.Home.PeopleTable;
import View.AdminGUI.Home.ResolveTable;
import View.AdminGUI.Listeners.AdminSlidePaneListner;
import View.AdminGUI.Listeners.TableAccountListener;
import View.AdminGUI.Management.ManagementPane;
import View.AdminGUI.Report.Report.Layouts.Listener.MainReportPaneListener;
import View.AdminGUI.Report.Report.Layouts.MainReportPane;
import View.AdminGUI.Work.Listener.WorkPaneListener;
import View.AdminGUI.Work.WorkPane;
import View.ClientWindow.Listeners.SearchPaneListener;
import View.ClientWindow.Listeners.SearchTableListener;
import View.ClientWindow.SearchPane;
import View.ClientWindow.SearchTabWindow;
import View.ClientWindow.SearchTable;
import View.ExportDatabasePane;
import View.Login.LoginWindow;
import ToolKit.LoadBar;
import ToolKit.Screen;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import utility.Utility;

import java.util.ArrayList;

/**
 * Created by Didoy on 10/9/2015.
 */
public class AdminWindow extends Stage{

    private BorderPane root;
    private ArrayList barangayDataList;
    private ManagementPane managementPane;
    private  Controller ctr;
    private WorkPane workPane = new WorkPane();
    private HomePane homePane = new HomePane();
    private AdminSlidePane adminSlidePane = new AdminSlidePane();
    private MainReportPane mainReportPane = new MainReportPane();
    private ExportDatabasePane exportDatabasePane;

    private SearchPane searchPane = new SearchPane();
    private SearchTable searchtable;

    public AdminWindow(){
        exportDatabasePane = new ExportDatabasePane(this);
        ctr = Controller.getInstance();

        root = new BorderPane();
        root.setLeft(adminSlidePane);
        root.setTop(searchPane);

        searchtable = new SearchTable(root);

        ShowHomePane();
        managementPane =  new ManagementPane(root);

        addComponentListeners();

        // main settings
        setWidth(Screen.screen.getWidth());
        setHeight(Screen.screen.getHeight());
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/CSS/AdminWindowCSS.css");
        setScene(scene);
        centerOnScreen();

    }
    private void addComponentListeners(){

        adminSlidePane.addAdminSlidePaneListener(new AdminSlidePaneListner() {
            @Override
            public void showManageMent() {
                showManagement();
            }

            @Override
            public void showHomePane() {
                ShowHomePane();
            }

            @Override
            public void Logout() {
                AdminLogout();
            }

            @Override
            public void showReport() {
                ShowReport();
            }

            @Override
            public void showExportDatabase() {
                ShowExportPane();
            }

            @Override
            public void ShowWorkPane() {
                showWorkPane();
            }
        });

        managementPane.addTableListener(new TableAccountListener() {
            @Override
            public boolean updateAccountStatus(int id, AccountStatus status) {
                return ctr.updateAccountStatus(id, status);
            }

            @Override
            public boolean approveAccount(int id, AccountApproveStatus status) {
                return ctr.approveAccount(id, status);
            }
        });


        workPane.addWorkPaneListener(new WorkPaneListener() {
            @Override
            public void showWorkPane() {
                root.setCenter(null);
                root.setCenter(workPane);
            }

            @Override
            public void saveChanges(ObservableList famiyList) {

                String date = Utility.getCurrentDate();
                String AdminName = ctr.getStaffInfo().getName();
                int x = 0;

                LoadBar.createProgressbar(famiyList.size());

                while (x <= famiyList.size() - 1) {
                    Family family = (Family) famiyList.get(x);

                    FamilyInfo familyInfo = family.getFamilyinfo();
                    int familyID = familyInfo.familyId();

                    FamilyHistory familyHistory = family.getFamilyHistory();

                    familyHistory.setDate(date);
                    familyHistory.setFamilyid(familyID);
                    familyHistory.setAdminName(AdminName);

                    boolean isAdded = ctr.addHistoryToFamily(family);
                    if (isAdded) {
                        boolean isfinish = LoadBar.updateValue();
                        if (isfinish) {
                            Utility.showMessageBox("Successfully Save all Family History", Alert.AlertType.INFORMATION);
                        }
                    }

                    x++;
                }

                showWorkPane();

            }
        });

        homePane.addHomePaneListeners(new HomePaneListener() {

            @Override
            public void viewPeople(String barangayName, String year, String type) {
                Params params = new Params(year , barangayName, type);

                ArrayList<Family> list = ctr.getFamilyBarangay(params, null);
                workPane.showTable(list);
            }

            @Override
            public void viewAllPeople(String barangayName, String year) {
                ArrayList<Family> list = ctr.viewAllPeople(barangayName, year);
                showPeopleTable(list);
            }

            @Override
            public void refresh() {
            }

            @Override
            public void viewDataByStatus(String barangayName, String date, String status) {

               ArrayList list= ctr.getFamilyDataByStatus(barangayName, date, status);
                if (status.equals("Resolve")){
                    showResolveTable(list);
                }
            }

            @Override
            public void showFamilyHistories(String barangayName, String date) {
                ArrayList list = ctr.showFamilyHistories(barangayName, date);
                showHistoryTable(list);
            }
        });


        mainReportPane.addMainReportPaneListener(new MainReportPaneListener() {
            @Override
            public void getReportChartParameters(Params params, ReportCategoryMethod method) {

                ArrayList<Family> list = ctr.getFamilyBarangay(params, method);
                workPane.showTable(list);
            }
        });

        searchPane.addSearchPaneListener(new SearchPaneListener() {
            @Override
            public void searchFamily(String text) {
                search(text);
            }
        });

        searchtable.addSearchTableListener(new SearchTableListener() {
            @Override
            public void rollUp() {
                root.setRight(null);
            }
        });

    }

    private void showWorkPane(){

        root.setCenter(null);
        root.setCenter(workPane);

    }
    public void ShowHomePane(){
        // add year options
        String year = "2016";
        barangayDataList = ctr.getBarangayData("2016");

        homePane.setData(barangayDataList, year);

        root.setCenter(null);
        root.setCenter(homePane);

    }


    public  void showManagement(){

        root.setCenter(managementPane);
    }

    public  void ShowReport(){
        root.setCenter(null);
        root.setCenter(mainReportPane);
    }

    public  void AdminLogout(){

        boolean x = Utility.showConfirmationMessage("Are you sure you want to Logout?", Alert.AlertType.CONFIRMATION);
        if (x){
            Exit();
        }
    }

    private void Exit(){
        close();
        ctr.Logout();
        new LoginWindow();

    }

    private void ShowExportPane(){
        root.setCenter(null);
        root.setCenter(exportDatabasePane);
    }

    private void showResolveTable(ArrayList data){
        ResolveTable resolveTable = new ResolveTable();
        root.setCenter(null);

        resolveTable.setData(data);
        root.setCenter(resolveTable);
    }

    private void showHistoryTable(ArrayList data){
        HistoryTable table = new HistoryTable();
        root.setCenter(null);

        table.setData(data);
        root.setCenter(table);
    }

    private void showPeopleTable(ArrayList data){
        PeopleTable table = new PeopleTable();
        root.setCenter(null);

        table.setData(data);
        root.setCenter(table);
    }

    private void search(String Searchedname){
        if (ctr.isServerConnected()){

            ArrayList list = ctr.searchedList(Searchedname);

            if (Searchedname.equals("")){
                Utility.showMessageBox("Search Box is empty", Alert.AlertType.ERROR);
            }else if (list.isEmpty()){
                Utility.showMessageBox("No records found", Alert.AlertType.ERROR);
            }else {
                System.out.println("show records");
                // Controller.getInstance().showSearchedList(list);
                showSearchedTable(list);
            }
        }else{
            ShowConnectingWindow(root);
            connect();
        }

    }
    private  void showSearchedTable   (ArrayList<Family> data){
        // get back to FX application if ever we are in RMI TCP Connection(2) thread
        searchtable.setData(data);
        root.setRight(searchtable);
    }
    public void ShowConnectingWindow(BorderPane root){

        GridPane grid = new GridPane();

        ProgressBar pb = new ProgressBar();
        pb.setPrefWidth(190);

        Label connectingL = new Label("Connecting to Server. . .");

        Timeline t = new Timeline();
        t.setCycleCount(Timeline.INDEFINITE);
        t.setAutoReverse(true);
        KeyValue kv = new KeyValue(connectingL.opacityProperty(), 0.0);
        KeyFrame kf = new KeyFrame(Duration.millis(2000), kv);

        KeyValue kv2 = new KeyValue(connectingL.opacityProperty(), 1.0);
        KeyFrame kf2 = new KeyFrame(Duration.millis(2000), kv2);
        t.getKeyFrames().addAll(kf, kf2);
        t.play();

        grid.setConstraints(connectingL, 0, 0, 1,1, HPos.CENTER, VPos.CENTER);
        grid.setConstraints(pb, 0, 1, 1,1, HPos.CENTER, VPos.CENTER);

        grid.setAlignment(Pos.CENTER);

        grid.getChildren().addAll(connectingL,pb);

        root.setDisable(true);
        root.setCenter(grid);

    }


    private void connect(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(!ctr.isServerConnected( )){
                }
                if (ctr.isServerConnected( )){

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            root.setDisable(false);
                            root.setCenter(null);
                            root.setCenter(homePane);
                            System.out.println("CONNECTED");
                        }
                    });

                }
            }
        });

        thread.start();

    }




}
