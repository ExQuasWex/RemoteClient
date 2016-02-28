package View.AdminGUI;

import AdminModel.Report.Children.Model.ResponsePovertyRate;
import AdminModel.RequestAccounts;
import Controller.Controller;
import View.AdminGUI.Home.HomePane;
import View.AdminGUI.Listeners.AdminSlidePaneListner;
import View.AdminGUI.Listeners.TableItemListener;
import View.AdminGUI.Management.ManagementPane;
import View.AdminGUI.Report.Report.Layouts.MainReportPane;
import View.AdminGUI.Work.WorkPane;
import View.Login.LoginWindow;
import View.ToolKit.Screen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
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

    private Dimension windowScreen = Screen.screen;

    public AdminWindow(){
        ctr = Controller.getInstance();

        root = new BorderPane();

        AdminSlidePane adminSlidePane = new AdminSlidePane();
        root.setLeft(adminSlidePane);

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
            public void ShowWorkPane() {
                showWorkPane();
            }
        });

        ShowHomePane();

        // Components
        managementPane =  new ManagementPane(root);

            managementPane.addTableListener(new TableItemListener() {
                @Override
                public boolean Approve(RequestAccounts ra) {

                    return ctr.Approve(ra);
                }

                @Override
                public boolean ApproveAdmin(RequestAccounts ra) {
                    return ctr.ApproveAdmin(ra);
                }

                @Override
                public boolean Reject(RequestAccounts ra) {
                    return ctr.Reject(ra);
                }
            });

        // main settings
        setWidth(windowScreen.getWidth());
        setHeight(windowScreen.getHeight());
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/CSS/ClientWindowCSS.css");
        setScene(scene);
        centerOnScreen();

        show();

    }

    private void showWorkPane(){

        root.setCenter(null);
        root.setCenter(workPane);

    }
    public void ShowHomePane(){
        barangayDataList = ctr.getBarangayData("2016");
        homePane.setData(barangayDataList);

        root.setCenter(null);
        root.setCenter(homePane);

    }


    public  void showManagement(){

        root.setCenter(managementPane);
    }

    public  void ShowReport(){
        root.setCenter(MainReportPane.getInstance());
    }

    public  void AdminLogout(){
         ctr.Logout();
         close();
         new LoginWindow();

    }
}
