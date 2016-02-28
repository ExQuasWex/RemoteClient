package View.AdminGUI.Report.SubHeader;

import AdminModel.Params;
import Controller.Controller;
import View.AdminGUI.Report.interfaces.ReportButtonListener;
import ListModels.UiModels;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import utility.Utility;

import java.util.ArrayList;

/**
 * Created by Didoy on 1/12/2016.
 */
public class ReportSpecificPane extends HBox {

    private ComboBox YearCB;
    private ComboBox BarangayCB;
    private ComboBox MonthCB;

    private Button View;
    private ReportButtonListener reportButtonListener;

    public ReportSpecificPane(){

        setAlignment(Pos.CENTER);
        setSpacing(5);

        View = new Button("Generate Report");

        YearCB = new ComboBox();
        MonthCB = new ComboBox();
        BarangayCB = new ComboBox();


        YearCB.setPromptText("Year");
        MonthCB.setPromptText("Month");
        BarangayCB.setPromptText("Barangay");

        YearCB.setItems(getYears());
        MonthCB.setItems(getMonths());
        BarangayCB.setItems(getBarangay());

        setMargin(View, new Insets(0,0,0,10));

        getChildren().addAll(YearCB, MonthCB, BarangayCB, View);

        View.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String month = (String) MonthCB.getSelectionModel().getSelectedItem();
                String year = ( (String)YearCB.getSelectionModel().getSelectedItem());
                String barangay  = (String) BarangayCB.getSelectionModel().getSelectedItem();

                String date = Utility.concatinateYearAndMonth(year, month);

                Params params = new Params(date , barangay);
                reportButtonListener.generateReport(params);
            }
        });

    }
    private ObservableList getBarangay(){
        ObservableList baranagayList = UiModels.getBarangayListModel();
        return  baranagayList;
    }

    private ObservableList getYears(){

        // fetch years here
        ArrayList yearList = Controller.getInstance().getYears();
        ObservableList<String> initialYearLis = FXCollections.observableArrayList(yearList);

        return initialYearLis;
    }

    public void addReportButtonListener(ReportButtonListener reportButtonListener){
        this.reportButtonListener = reportButtonListener;
    }

    private ObservableList getMonths(){
       ObservableList monthsList = UiModels.getMonthListModel();

        return monthsList;
    }
}
