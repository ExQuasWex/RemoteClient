package View.AdminGUI.Report.SubHeader;

import AdminModel.Params;
import View.AdminGUI.Report.interfaces.ReportButtonListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import utility.Utility;

/**
 * Created by Didoy on 1/13/2016.
 */
public class ReportOverViewPane extends HBox {

    private  Button generateReportBtn;
    private ReportButtonListener reportButtonListener;

    public ReportOverViewPane(){
        setAlignment(Pos.CENTER);

        generateReportBtn = new Button("Generate Report");

        getChildren().add(generateReportBtn);

        generateReportBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int year = Utility.getCurrentIntYear();
                Params params = new Params(year,1,0,"Dau", null);
                reportButtonListener.generateReport(params);
            }
        });
    }

    public void addReportButtonListener(ReportButtonListener reportButtonListener){
        this.reportButtonListener = reportButtonListener;
    }

}
