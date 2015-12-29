package View.AdminGUI;

import AdminModel.BarangayData;
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

    public AdminWindow(){

        // css settingss

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        root = new BorderPane();

        SlidePane slidePane = new SlidePane(500);
        root.setLeft(slidePane);

        barangayDataList = Controller.getInstance().getBarangayData();
        showInitialReports();

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

    public  void ShowManagement(String totalRequest){
        ManagementPane mp =  new ManagementPane(totalRequest);
        root.setCenter(mp);
    }
}
