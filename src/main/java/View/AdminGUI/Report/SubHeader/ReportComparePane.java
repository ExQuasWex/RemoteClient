package View.AdminGUI.Report.SubHeader;

import AdminModel.Params;
import Controller.Controller;
import View.AdminGUI.Report.interfaces.ReportButtonListener;
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
 * Created by Didoy on 1/12/2016.
 */

public class ReportComparePane extends HBox {

     private ComboBox initialYear ;
     private ComboBox maxYear ;

    private Button geneateReportBtn;
    private ReportButtonListener reportButtonListener;
    private Controller controller;

    public ReportComparePane(){

        controller = Controller.getInstance();

        setAlignment(Pos.CENTER);
        setSpacing(5);

        geneateReportBtn = new Button("Generate Report");

        initialYear = new ComboBox();
        maxYear = new ComboBox();

        setYears();
        initialYear.setPromptText("Initial Year");
        maxYear.setPromptText("Max Year");

        setMargin(geneateReportBtn, new Insets(0, 0, 0, 10));


        getChildren().addAll(initialYear, maxYear, geneateReportBtn);


        geneateReportBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int initialyear = Integer.parseInt( (String)initialYear.getSelectionModel().getSelectedItem());
                int maxyear     = Integer.parseInt( (String)maxYear.getSelectionModel().getSelectedItem());

                Params params = new Params(initialyear, maxyear, 1, null, null);

                reportButtonListener.generateReport(params);
            }
        });

    }

    private boolean validateReportParameters(){


        return false;
    }

    private void  setYears(){
        ArrayList year = controller.getYears();

        ObservableList yearObservableList = FXCollections.observableArrayList(year);
        initialYear.setItems(yearObservableList);
        maxYear.setItems(yearObservableList);

    }



    private ObservableList getmaxYears(){
        // fetch years here
        ObservableList<Integer> maxYears = FXCollections.observableArrayList();
        maxYears.add(2011);
        maxYears.add(2013);

        return maxYears;
    }
    public void addReportButtonListener(ReportButtonListener reportButtonListener){
        this.reportButtonListener = reportButtonListener;
    }


}
