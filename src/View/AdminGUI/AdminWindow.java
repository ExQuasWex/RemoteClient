package View.AdminGUI;

import AdminModel.BarangayData;
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

    private static AdminWindow adminWindow = new AdminWindow();
    private BorderPane root;
    private ArrayList barangayDataList;
    private   ManagementPane mp ;


    private AdminWindow(){

        Controller ctr = Controller.getInstance();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        root = new BorderPane();
        AdminSlidePane adminSlidePane = new AdminSlidePane(screen.getWidth()/4);
        root.setLeft(adminSlidePane);

        barangayDataList = ctr.getBarangayData();
        showInitialReports(barangayDataList);


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
        Scene scene = new Scene(root, 1000,600);
        scene.getStylesheets().add("/CSS/ClientWindowCSS.css");
        setScene(scene);
        initStyle(StageStyle.TRANSPARENT);
        centerOnScreen();

    }
    private void showInitialReports(ArrayList barangayDataList){
        int x = 0;
        System.out.println("Initial size:" + barangayDataList.size());

        ObservableList<PieChart.Data>  pieData = FXCollections.observableArrayList();
                while (x <= barangayDataList.size() - 1){
                    BarangayData bd = (BarangayData) barangayDataList.get(x);
                    pieData.add(new PieChart.Data(bd.getBarangayName(),bd.getUnresolvePopulation()));
                    x++;
                }

        PieChart pc = new PieChart(pieData);
        pc.setTitle("General Reports");

        root.setCenter(pc);

    }

    public static AdminWindow getInstance(){
        return adminWindow;}

    public  void ShowManagement(){

        root.setCenter(mp);
    }

    public  void ShowReport(){
        root.setCenter(MainReportPane.getInstance());
    }

    public  void AdminLogout(){
         getInstance().close();
        LoginWindow.getInstantance().showLoginWindow(true);

        System.out.println("Admin logut called");
    }
}
