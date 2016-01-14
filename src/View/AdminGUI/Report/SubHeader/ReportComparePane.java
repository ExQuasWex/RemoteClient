package View.AdminGUI.Report.SubHeader;

import AdminModel.Params;
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

/**
 * Created by Didoy on 1/12/2016.
 */

public class ReportComparePane extends HBox {

     private ComboBox initialYear ;
     private ComboBox maxYear ;

    private Button geneateReportBtn;
    private ReportButtonListener reportButtonListener;

    public ReportComparePane(){

        setAlignment(Pos.CENTER);
        setSpacing(5);

        geneateReportBtn = new Button("Generate Report");

        initialYear = new ComboBox();
        maxYear = new ComboBox();

        initialYear.setPromptText("Initial Year");
        maxYear.setPromptText("Max Year");

        initialYear.setItems(getInitialYears());
        maxYear.setItems(getmaxYears());

        setMargin(geneateReportBtn, new Insets(0, 0, 0, 10));


        getChildren().addAll(initialYear, maxYear, geneateReportBtn);


        geneateReportBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int initialyear = (Integer) initialYear.getSelectionModel().getSelectedItem();
                int maxyear = (Integer) maxYear.getSelectionModel().getSelectedItem();

                System.out.println(initialyear +" :) "+ maxyear);
                Params params = new Params(initialyear, maxyear, 1, null, null);
                reportButtonListener.generateReport(params);
            }
        });


    }

    private ObservableList getInitialYears(){

        // fetch years here
        ObservableList<Integer> initialYearLis = FXCollections.observableArrayList();
        initialYearLis.add(2011);
        initialYearLis.add(2013);

        return initialYearLis;
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
