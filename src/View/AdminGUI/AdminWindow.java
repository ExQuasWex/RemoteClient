package View.AdminGUI;

import AdminModel.BarangayData;
import AdminModel.RequestAccounts;
import Controller.Controller;
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


    public AdminWindow(){

        Controller ctr = Controller.getInstance();
        //Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        root = new BorderPane();

        SlidePane slidePane = new SlidePane(500);
        root.setLeft(slidePane);

        barangayDataList = ctr.getBarangayData();
        showInitialReports();


        // Components
        mp =  new ManagementPane(root);

            mp.addTableListener(new TableItemListener() {
                @Override
                public boolean Approve(RequestAccounts ra) {
                    System.out.println("from adminwindow " + ra.getName());

                    return ctr.Approve(ra);
                }

                @Override
                public boolean ApproveAdmin(RequestAccounts ra) {
                    return false;
                }

                @Override
                public boolean Reject(RequestAccounts ra) {
                    return false;
                }
            });

        // main settings
        Scene scene = new Scene(root, 800,600);
        setScene(scene);
        initStyle(StageStyle.TRANSPARENT);
        centerOnScreen();

    }
    private void showInitialReports(){
        int x = 0;
        BorderPane bp = new BorderPane();
        System.out.println("Initial size:" + barangayDataList.size());

        ObservableList<PieChart.Data>  pieData = FXCollections.observableArrayList();
                while (x <= barangayDataList.size() - 1){
                    BarangayData bd = (BarangayData) barangayDataList.get(x);
                    pieData.add(new PieChart.Data(bd.getBarangayName(),bd.getUnresolvePopulation()));
                    x++;
                }

        PieChart pc = new PieChart(pieData);
        pc.setTitle("General Reports");

        bp.setCenter(pc);
        root.setCenter(bp);

    }


    public static AdminWindow getInstance(){
        return adminWindow;}

    public  void ShowManagement(){

        root.setCenter(mp);
    }
}
