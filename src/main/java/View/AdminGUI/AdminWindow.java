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
import View.AdminGUI.Home.HomePane;
import View.AdminGUI.Home.Listeners.HomePaneListener;
import View.AdminGUI.Listeners.AdminSlidePaneListner;
import View.AdminGUI.Listeners.TableAccountListener;
import View.AdminGUI.Management.ManagementPane;
import View.AdminGUI.Report.Report.Layouts.Listener.MainReportPaneListener;
import View.AdminGUI.Report.Report.Layouts.MainReportPane;
import View.AdminGUI.Work.Listener.WorkPaneListener;
import View.AdminGUI.Work.WorkPane;
import View.ClientWindow.SearchTabWindow;
import View.ExportDatabasePane;
import View.Login.LoginWindow;
import ToolKit.LoadBar;
import ToolKit.Screen;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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

    public AdminWindow(){
        exportDatabasePane = new ExportDatabasePane(this);

        ctr = Controller.getInstance();

        root = new BorderPane();
        root.setLeft(adminSlidePane);

        ShowHomePane();

        // Components
        managementPane =  new ManagementPane(root);

        addComponentListeners();

        // main settings
        setWidth(Screen.screen.getWidth());
        setHeight(Screen.screen.getHeight());
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/CSS/ClientWindowCSS.css");
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

                    System.out.println(familyHistory.getAction());
                    System.out.println(familyHistory.getAdminName());
                    System.out.println(familyHistory.getDate());
                    System.out.println(familyHistory.getFamilyid());
                    System.out.println(familyHistory.isRevoke());
                    System.out.println(familyHistory.getRevokeDescription());

                    boolean isAdded = ctr.addHistoryToFamily(family);
                    if (isAdded) {
                        boolean isfinish = LoadBar.updateValue();
                        if (isfinish) {
                            Utility.showMessageBox("Successfully Save all Family History", Alert.AlertType.INFORMATION);
                        }
                    }

                    x++;

                }

            }
        });

        homePane.addHomePaneListeners(new HomePaneListener() {

            @Override
            public void viewPeople(String barangayName, String year) {
                Params params = new Params(year , barangayName);
                ArrayList<Family> list = ctr.getFamilyBarangay(params, null);
                workPane.showTable(list);
            }

            @Override
            public void refresh() {

            }
        });


        mainReportPane.addMainReportPaneListener(new MainReportPaneListener() {
            @Override
            public void getReportChartParameters(Params params, ReportCategoryMethod method) {

                ArrayList<Family> list = ctr.getFamilyBarangay(params, method);
                workPane.showTable(list);
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
            new LoginWindow();
            ctr.Logout();
            close();

    }
    private void ShowExportPane(){
        root.setCenter(null);
        root.setCenter(exportDatabasePane);
    }


}
