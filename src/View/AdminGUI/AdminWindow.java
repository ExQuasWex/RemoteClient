package View.AdminGUI;

import AdminModel.Report.Children.Model.ResponsePovertyRate;
import AdminModel.RequestAccounts;
import Controller.Controller;
import View.AdminGUI.Report.Report.Layouts.MainReportPane;
import View.Login.LoginWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Didoy on 10/9/2015.
 */
public class AdminWindow extends Stage{

    private BorderPane root;
    private ArrayList barangayDataList;
    private   ManagementPane mp ;
    private  Controller ctr;

    private WorkPane workPane = new WorkPane();

    public AdminWindow(){
        ctr = Controller.getInstance();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        root = new BorderPane();


        AdminSlidePane adminSlidePane = new AdminSlidePane(screen.getWidth()/4);
        root.setLeft(adminSlidePane);


        adminSlidePane.addAdminSlidePaneListener(new AdminSlidePaneListner() {
            @Override
            public void showManageMent() {
                showManagement();
            }

            @Override
            public void showInitialReport() {
                showInitialReports();

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


        showInitialReports();


        // Components
        mp =  new ManagementPane(root);

            mp.addTableListener(new TableItemListener() {
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
        setWidth(screen.getWidth());
        setHeight(screen.getHeight());
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/CSS/ClientWindowCSS.css");
        setScene(scene);
        initStyle(StageStyle.TRANSPARENT);
        centerOnScreen();

        show();

    }

    private void showWorkPane(){

        root.setCenter(null);
        root.setCenter(workPane);

    }
    public void showInitialReports(){
        int x = 0;

        barangayDataList = ctr.getBarangayData();

        ObservableList<PieChart.Data>  pieData = FXCollections.observableArrayList();
                while (x <= barangayDataList.size() - 1){
                    ResponsePovertyRate bd = (ResponsePovertyRate) barangayDataList.get(x);
                    pieData.add(new PieChart.Data(bd.getBarangayName(),bd.getUnresolvePopulation()));
                    x++;
                }

        PieChart pc = new PieChart(pieData);
        pc.setTitle("General Reports");

        root.setCenter(null);
        root.setCenter(pc);

    }


    public  void showManagement(){

        root.setCenter(mp);
    }

    public  void ShowReport(){
        root.setCenter(MainReportPane.getInstance());
    }

    public  void AdminLogout(){

         ctr.Logout();
         close();
         new LoginWindow();

        System.out.println("Admin logut called");
    }
}
