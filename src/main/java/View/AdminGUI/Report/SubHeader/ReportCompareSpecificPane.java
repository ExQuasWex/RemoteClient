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
public class ReportCompareSpecificPane extends HBox {

    private ComboBox barangay1 ;
    private ComboBox barangay2 ;
    private ComboBox Year ;
    private ComboBox MaxYear ;

    private Button generateReportBtn;
    private ReportButtonListener reportButtonListener;
    private Controller controller = Controller.getInstance();

    public ReportCompareSpecificPane() {
        setAlignment(Pos.CENTER);
        setSpacing(5);
        generateReportBtn = new Button("Generate Report");

        barangay1 = new ComboBox();
        barangay2 = new ComboBox();
        Year = new ComboBox();
        MaxYear = new ComboBox();

        barangay1.setPromptText("Barangay 1");
        barangay2.setPromptText("Barangay 2");
        Year.setPromptText("Year");
        MaxYear.setPromptText("Year");

        barangay1.setItems(getBarangay());
        barangay2.setItems(getBarangay());

        setMargin(generateReportBtn, new Insets(0,0,0,10));

        getChildren().addAll(barangay1, Year,  barangay2, MaxYear, generateReportBtn);

        generateReportBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int initialyear = Integer.parseInt( (String)Year.getSelectionModel().getSelectedItem());
                int maxyear     = Integer.parseInt( (String)MaxYear.getSelectionModel().getSelectedItem());

                String barangayOne  = (String) barangay1.getSelectionModel().getSelectedItem();
                String barangayTwo  = (String) barangay2.getSelectionModel().getSelectedItem();

                Params params = new Params(initialyear, maxyear, 0, barangayOne, barangayTwo);
                reportButtonListener.generateReport(params);
            }
        });

        setYears();
    }

    private ObservableList getBarangay(){

        ObservableList baranagayList = UiModels.getBarangayListModel();
        return  baranagayList;
    }

    public void addReportButtonListener(ReportButtonListener reportButtonListener){
        this.reportButtonListener = reportButtonListener;
    }

    private void  setYears(){
        ArrayList year = controller.getYears();

        ObservableList yearObservableList = FXCollections.observableArrayList(year);
        Year.setItems(yearObservableList);
        MaxYear.setItems(yearObservableList);

    }


}
