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

import java.util.ArrayList;

/**
 * Created by Didoy on 1/13/2016.
 */
public class ReportSpecificOverviewPane extends  HBox{

    /**
     * Created by Didoy on 1/12/2016.
     */
        private ComboBox YearCB;
        private ComboBox BarangayCB;

        private Button View;
          private ReportButtonListener reportButtonListener;


    public ReportSpecificOverviewPane(){

            setAlignment(Pos.CENTER);
            setSpacing(5);

            View = new Button("Generate Report");

            YearCB = new ComboBox();
            BarangayCB = new ComboBox();

            YearCB.setPromptText("Year");
            BarangayCB.setPromptText("Barangay");

            YearCB.setItems(getYears());
            BarangayCB.setItems(getBarangay());

            setMargin(View, new Insets(0,0,0,10));

            getChildren().addAll(YearCB, BarangayCB, View);

        View.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                int initialyear = Integer.parseInt( (String)YearCB.getSelectionModel().getSelectedItem());
                String barangayOne  = (String) BarangayCB.getSelectionModel().getSelectedItem();

                Params params = new Params(initialyear, 0, 0, barangayOne, "");
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
            ArrayList list =  Controller.getInstance().getYears();
            ObservableList<String> initialYearLis = FXCollections.observableArrayList(list);

            return initialYearLis;
        }

        private ObservableList getMonths(){
            // fetch years here
            ObservableList<String> months = FXCollections.observableArrayList();
            months.add("January");
            months.add("February");
            months.add("March");
            months.add("April");
            months.add("May");
            months.add("June");
            months.add("July");
            months.add("August");
            months.add("September");
            months.add("October");
            months.add("November");
            months.add("December");

            return months;
        }
    public void addReportButtonListener(ReportButtonListener reportButtonListener){
        this.reportButtonListener = reportButtonListener;
    }


}
